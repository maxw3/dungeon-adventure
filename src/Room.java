public class Room {

    private Item[] myItems;
    private DungeonCharacter[] myDungeonCharacters;
    
    private boolean myNorth;
    private boolean myEast;
    private boolean mySouth;
    private boolean myWest;
    private int myEvent;
    private boolean myExplored;

    Room(){
        
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Print First Row
        sb.append('*');
        if(myNorth){
            sb.append("-\n");
        }else{
            sb.append("*\n");
        }

        if(myEast){
            sb.append("|");
        }else{
            sb.append("*");
        }

        if(myItems.length > 1){
            sb.append('m');
        }

        return "4";
    }

}