public class Room {

    //private final Item[] myItems;
    private final DungeonCharacter[] myDungeonCharacters;
    
    private boolean myNorth;
    private boolean myEast;
    private boolean mySouth;
    private boolean myWest;
    private int myEvent;
    private boolean myExplored;

    Room(){
        //myItems = new Item[1];
        myDungeonCharacters = new DungeonCharacter[1];

        myNorth = true;
        myEast = true;
        mySouth = true;
        myWest = true;
    }

    public boolean getNorth(){
        return myNorth;
    }

    public boolean getWest(){
        return myWest;
    }

    public boolean getEast(){
        return myEast;
    }

    public boolean getSouth(){
        return mySouth;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Print First Row
        sb.append('*');
        if(myNorth){
            sb.append("-");
        }else{
            sb.append("*");
        }
        sb.append("*\n");

        if(myWest){
            sb.append("|");
        }else{
            sb.append("*");
        }

        // Print Second Row
        if(myDungeonCharacters.length > 1){
            sb.append('m');
        }else{
            sb.append(' ');
        }
        if(myEast){
            sb.append('|');
        }else{
            sb.append('*');
        }
        sb.append('\n');

        // Print 3rd Row
        sb.append('*');
        if(mySouth){
            sb.append('-');
        }else{
            sb.append('*');
        }
        sb.append("*\n");

        return sb.toString();
    }

}