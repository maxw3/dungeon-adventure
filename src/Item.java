
public abstract class Item {

    private String myName;

    Item(){
        myName = "default";
    }

    Item(String theName){
        myName = theName;
    }

    public void setName(String theName){
        myName = theName;
    }

    @Override
    public String toString(){
        return myName;
    }
}
