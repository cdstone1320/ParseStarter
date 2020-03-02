import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, Integer> symbolTable;
    private int value;


    //constructor for symbol table
    public SymbolTable(){
        this.symbolTable = new HashMap<String, Integer>();
        this.value = 0;
    }

    // adds an id to hashmap and assigns it a address value
    public void add(String id){
        if(!symbolTable.containsKey(id)) {
            symbolTable.put(id, value);
        }
        this.value++;
    }

    // gets the address of an id and returns -1 if id not inside
    public int getAddress(String id){
        if (symbolTable.get(id)!=null){
            return symbolTable.get(id);
        }
        else{
            return -1;
        }
    }

    // returns all key-value pairs in hashmap in a String
    public String toString(){
        return symbolTable.toString();
    }
}
