public class Floor {

    private short mySize;
    private Room[][] myRooms;

    Floor(){
        mySize = 3;
        myRooms = new Room[mySize][mySize];

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room();
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Room[] ra: myRooms){
            for(int row = 0; row < 3; row++){
                for(Room room: ra){
                    sb.append(room.getRow(row));
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
