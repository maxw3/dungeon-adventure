public class Floor {

    private short mySize;
    private char[][] myFloorChars;

    Floor(){
        mySize = 3;

        myFloorChars = new char[2 * mySize + 1][2 * mySize + 1];

        for(int row = 0; row < myFloorChars.length; row++){
            for(int col = 0; col < myFloorChars.length; col++){
                if(row == 0 || col == 0 ||
                 row == myFloorChars.length - 1 || col == myFloorChars.length - 1){
                    myFloorChars[row][col] = '*';
                }else{
                    myFloorChars[row][col] = ' ';
                }
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int row = 0; row < myFloorChars.length; row++){
            for(int col = 0; col < myFloorChars.length; col++){
                sb.append(myFloorChars[row][col]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
