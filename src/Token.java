public class Token {
    private String type = "";
    private String value = "";

    //gets type data member
    public String getType() {

        return type;
    }
    //sets type data member
    public void setType(String type) {

        this.type = type;
    }
//gets value data member
    public String getValue() {

        return value;
    }
//sets value data member
    public void setValue(String value) {

        this.value = value;
    }
//returns data members as string
    public String toString(){

        return type + " " + value;
    }
}
