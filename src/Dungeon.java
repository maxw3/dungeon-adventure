public class Dungeon {

    private static class Room {
        private int r;
        private int c;
        private boolean northWall;
        private boolean southWall;
        private boolean eastWall;
        private boolean westWall;

        private char contains;

        private Room(int r, int c) {
            this.r = r;
            this.c = c;
            northWall = true;
            southWall = true;
            eastWall = true;
            westWall = true;
            contains = ' ';
        }

        private void setNorth(boolean n) {
            northWall = n;
        }

        private void setSouth(boolean s) {
            southWall = s;
        }

        private void setEast(boolean e) {
            eastWall = e;
        }

        private void setWest(boolean w) {
            westWall = w;
        }

        private void setContains(char c) {
            contains = c;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (northWall == true) {
                sb.append("***\n");
            } else {
                sb.append("*-*\n");
            }

            if (westWall == true) {
                sb.append("*");
            } else {
                sb.append("|");
            }

            sb.append(contains);

            if (eastWall == true) {
                sb.append("*\n");
            } else {
                sb.append("|\n");
            }

            if (southWall == true) {
                sb.append("***");
            } else {
                sb.append("*-*");
            }

            return sb.toString();
        }



    }

    public static void main(String[] args) {
        Room aRoom = new Room(0,0);
        System.out.print(aRoom.toString());
        System.out.println("room ^^^");
        int rowNums = 4;
        int rowCols = 4;
        Room[][] mRooms = new Room[rowNums][rowCols];
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rowNums; r++) { //Generates a 2d array of only walls
            for (int c = 0; c < rowCols; c++) {
                Room tempRoom = new Room(r,c);
                mRooms[r][c] = tempRoom;
                System.out.println(tempRoom.toString());
            }
        }

        for (int r = 0; r < rowNums; r++) { //Creates a String of the double array of Rooms
            for (int d = 0; d < 3; d++) {
                for (int c = 0; c < rowCols; c++) {
                    Room tempRoom = mRooms[r][c];
                    if (d == 0 && tempRoom.northWall == true) {
                        sb.append("*** ");
                    } else if (d == 0 && tempRoom.northWall == false) {
                        sb.append("*-* ");
                    }

                    if (d == 1) {
                        if (tempRoom.eastWall == true) {
                            sb.append("*" + tempRoom.contains);
                        }
                        if (tempRoom.eastWall == false) {
                            sb.append("|" + tempRoom.contains);
                        }
                        if (tempRoom.westWall == true) {
                            sb.append("* ");
                        }
                        if (tempRoom.westWall == false) {
                            sb.append("| ");
                        }
                    }

                    if (d == 2 && tempRoom.southWall == true) {
                        sb.append("*** ");
                    } else if (d == 2 && tempRoom.southWall == false) {
                        sb.append("*-* ");
                    }
                }
                sb.append("\n");
            }
        }


        System.out.println(sb);

    }


}
