public class Floor {

    private Room[][] myRooms;
    private short mySize;

    Floor(){
        mySize = 5;
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

        for(int ra = 0; ra < mySize; ra++){
            for(int room = 0; room < mySize; room++){
                sb.append('*');
                if(ra == 0){
                    sb.append('*');
                }else{
                    if(myRooms[ra][room].getNorth()){
                        sb.append('-');
                    }else{
                        sb.append('*');
                    }
                }
                if(room == mySize - 1){
                    sb.append('*');
                }
            }
            sb.append('\n');
            for(int room = 0; room < mySize; room++){
                if(room == 0){
                    sb.append('*');
                }else{
                    if(myRooms[ra][room].getWest()){
                        sb.append('|');
                    }else{
                        sb.append('*');
                    }
                }
                sb.append(' ');
                if(room == mySize - 1){
                    sb.append('*');
                }
                // if(ra[room].getEast()){
                //     sb.append("|");
                // }else{
                //     sb.append("*");
                // }
            }
            sb.append('\n');
            // for(int room = 0; room < mySize; room++){
            //     sb.append('*');
            //     if(ra[room].getSouth()){
            //         sb.append('-');
            //     }else{
            //         sb.append('*');
            //     }
            //     sb.append("*");
            // }
            //sb.append('\n');
        }

        for(int c = 0; c < mySize * 2; c++){
            sb.append('*');
        }
        sb.append('*');

        return sb.toString();
    }
}
