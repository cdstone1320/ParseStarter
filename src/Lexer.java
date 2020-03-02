import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to build an array of Tokens from an input file
 * @author wolberd
 * @see Token
 * @see Parser
 * @see ByteCodeInterpreter
 */
public class Lexer {

    String buffer;
    int index = 0;
    public static final String INTTOKEN = "INT";
    public static final String IDTOKEN = "ID";
    public static final String ASSMTTOKEN = "ASSMT";
    public static final String PLUSTOKEN = "PLUS";
    public static final String EOFTOKEN = "EOF";

    /**
     * call getInput to get the file data into our buffer
     *
     * @param fileName the file we'll open
     */
    public Lexer(String fileName) {
        getInput(fileName);

    }

    /**
     * Reads given file into the data member buffer
     *
     * @param fileName name of file to parse
     */
    private void getInput(String fileName) {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String(allBytes);
        } catch (IOException e) {
            System.out.println("You did not enter a valid file name in the run arguments.");
            System.out.println("Please enter a string to be parsed:");
            Scanner scanner = new Scanner(System.in);
            buffer = scanner.nextLine();
        }
    }

    private void getIdentifier(Token token) {
        String value = "";
        if (Character.isLetter(buffer.charAt(index))) {
            token.setType(IDTOKEN);
        }
        // whatever follows a letter that is either a letter or a number is added to the identifier value
        while (index < buffer.length() && buffer.charAt(index) != ' ' && buffer.charAt(index) != '=' && buffer.charAt(index) != '+') {

            value += ("" + buffer.charAt(index));
            index ++;
        }
        token.setValue(value);
    }

    private void getInteger(Token token) {
        boolean notSpace = !Character.isWhitespace(buffer.charAt(index));
        boolean isInt = Character.isDigit(buffer.charAt(index));
        String value = "";

        if (isInt)  {
            token.setType(INTTOKEN);
        }
        // sets integer value so that it only includes numbers
        while (index < buffer.length() && buffer.charAt(index)!=' ' && buffer.charAt(index)!= '+' && isInt  && buffer.charAt(index) != '='){
            value += "" + buffer.charAt(index);
            index++;
        }
        token.setValue(value);
    }

    public Token getNextToken() {
        Token token = new Token();



        // if the first character is a letter followed by any number of letters and numbers its an identifier
        if (Character.isLetter(buffer.charAt(index))) {
            getIdentifier(token);
            return token;
        }
        // if the first character is a number followed by any number of numbers its an integer
        else if (Character.isDigit(buffer.charAt(index))) {
            getInteger(token);
            return token;
        }
        // if the character is = it's an assignment operator
        else if (buffer.charAt(index) == '=') {
            token.setType(ASSMTTOKEN);
            token.setValue("=");
            index++;
            return token;
        }
        // if the character is + it's a plus operator
        else if (buffer.charAt(index) == '+') {
            token.setType(PLUSTOKEN);
            token.setValue("+");
            index++;
            return token;
        }
        // if we reached the end of the file, its an EOF token
        else {
            token.setType(EOFTOKEN);
            token.setValue("-");
            index++;
            return token;
        }
    }


    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> tokenList = new ArrayList<Token>();
        Token token;
        while(index!=buffer.length()){

                 token = getNextToken();
                 String type = token.getType();
                 if(type!=EOFTOKEN)
                 tokenList.add(token);
        }
        Token temp = new Token();
        temp.setType(EOFTOKEN);
        temp.setValue("-");
        tokenList.add(temp);
        return tokenList;
    }

        public static void main(String[] args) {
        String fileName = "test.txt";

//        if (args.length==0) {
//            System.out.println("You must specify a file name");
//            return;
//        } else {
//            fileName= args[0];
//        }
        Lexer lexer = new Lexer("test.txt");
        System.out.println(lexer.buffer);
        System.out.println(lexer.getAllTokens());
    }
}

	