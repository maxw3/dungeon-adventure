public class Room {

    private Item[] myItems;
    private DungeonCharacter[] myDungeonCharacters;
    
    private int myEvent;
    private boolean myExplored;

    private char[][] myRoomChars; 

    Room(){
        myItems = new Item[0];
        myDungeonCharacters = new DungeonCharacter[0];

        myRoomChars = new char[3][3];

        myRoomChars[0][0] = '*';
        myRoomChars[0][1] = '-';
        myRoomChars[0][2] = '*';

        myRoomChars[1][0] = '|';
        myRoomChars[1][1] = ' ';
        myRoomChars[1][2] = '|';

        myRoomChars[2][0] = '*';
        myRoomChars[2][1] = '-';
        myRoomChars[2][2] = '*';
    }

    public char[] getRow(int theRow){
        return myRoomChars[theRow];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(char[] a: myRoomChars){
            for(char c: a){
                sb.append(c);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}