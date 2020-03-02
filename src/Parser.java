import org.w3c.dom.ls.LSOutput;

import java.lang.reflect.Array;
import java.nio.channels.NotYetBoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    SymbolTable symbolTable = new SymbolTable();
    Token token;
    ArrayList<Token> tokenList = new ArrayList<Token>();
    int index = 0;
    int failure;

    //constructor for Parser
    public Parser(){
        Lexer lexer = new Lexer("textExpectingId2.txt");
        tokenList = lexer.getAllTokens();
    }
    // takes in index and returns the next token in token list
    public Token getToken(int index) {
        return tokenList.get(index);
    }
    //takes in index and returns previous token in token list
    public Token putToken(int index){
        return tokenList.get(index-1);
    }

    // checks if next token is an identifier
    public boolean parseID(Token token) {
        String type = token.getType();
        String value = token.getValue();
        if(type.equals("ID")){
            return true;
        }
        else{
            return false;
        }
    }
    //inserts ID into table
    public void insertToTable(Token t) {
        symbolTable.add(t.getValue());

    }

    //parses and returns true if assign op
    public boolean parseAssignOp(Token token){
        String type = token.getType();
        if (type == "ASSMT"){
            return true;
        }
        else{
            return false;
        }
    }

    // parses through an expression and returns -1 if invalid or i to start looping through new expression at that index
    public int parseExpression(int index) {
        int i = index;
        while( i < this.tokenList.size()) {
            if((parseID(getToken(i)) && parseID(putToken(i))) || (parseID(getToken(i)) && (putToken(i).getType().equals("INT"))))
                return i;

            if(parseID(getToken(i))) {
                    if (this.symbolTable.getAddress(getToken(i).getValue().trim()) == -1) {
                        System.out.println("identifier not defined");
                        failure = i;
                        return -1;

                    }
                }

            //tests whether there are two ints right next to each other
            if((getToken(i).getType().equals("INT") && putToken(i).getType().equals("INT"))) {
                failure = i;
                System.out.println("Expecting identifier or integer");
                return -1;
            }

            if(parseAssignOp(getToken(i)) && getToken(i).getType().equals("INT")) {
                System.out.println("Expecting integer or plus operator");
                failure = i;
                return -1;
            }

            if(((getToken(i).getType().equals("PLUS") && (parseID(getToken(i)) || getToken(i).getType().equals("INT"))) && (putToken(i).getType().equals("PLUS") && (parseID(putToken(i)) || putToken(i).getType().equals("INT"))))) {
                failure = i;
                System.out.println("Expecting identifier or integer");
                return -1;
            }
            i++;
        }
        return i;
    }

    //takes previously defined methods and returns true or false
    public boolean parseAssignment() {

        boolean x = false;

        System.out.println(tokenList.toString());
        int j = 0;
        for(int i = 0; i <  tokenList.size(); i++){

            if(i-j ==0) {
                insertToTable(getToken((i)));
            }
            if((i - j) ==0 && !parseID(getToken(i))) {
                System.out.println("Expecting identifier");
                failure = i;
                return false;

            }

            else if((i - j) ==1 && !parseAssignOp(getToken(i))) {
                failure = i;
                System.out.println("Expecting assignment operator");
                return false;

            }

            else if(i - j == 1)
                x = true;

            else if(x){
                j = parseExpression(i);
                if(j == -1)
                    return false;
            }
        }

        return true;
    }

    // returns valid or invalid based on return value for parseAssignment
    public String parseProgram(){
        boolean valid = parseAssignment();
        if (valid){
            return "Valid Program";
        }
        else{
            return "Invalid program";
        }
    }


    public static void main(String[] args) {
        Parser parser = new Parser();
        System.out.println(parser.parseProgram());
        System.out.println(parser.failure);



    }

}
