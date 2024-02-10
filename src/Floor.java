import model.HealthPotion;
import model.Pillar;

import java.util.Random;

public class Floor {

    private Room[][] myRooms;
    private int mySize;

    Floor(){
        mySize = 3;
        myRooms = new Room[mySize][mySize];

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room();
            }
        }
        fillFloor();
    }

    Floor(int theSize){
        mySize = theSize;
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

    public int getSize(){
        return mySize;
    }

    private void fillFloor() {
        Random rand = new Random();
        for(int row = 0; row < mySize; row++) {
            for(int col = 0; col < mySize; col++){
                int choice = rand.nextInt(12);
                if (choice >= 3 && choice <= 5) {
                    myRooms[row][col].addCharacter(MonsterFactory.createSkeleton(0));
                } else if (choice > 5 && choice <= 10) {
                    myRooms[row][col].addItem(new HealthPotion("Health Potion", 1));
                } else if (choice > 10) {
                    // Add in Pits
                }
            }
        }
        int x = rand.nextInt(mySize);
        int y = rand.nextInt(mySize);
        myRooms[x][y].addItem(new Pillar("Encapsulation"));
    }
}
