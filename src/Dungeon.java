import java.util.Random;

public class Dungeon {

    private static int rowNum;
    private static int colNum;
    private static Room[][] myRooms;

    public Dungeon() {
        rowNum = 0;
        colNum = 0;
        myRooms = new Room[colNum][rowNum];

    }

    private static class Room {
        private boolean visited;
        private boolean northWall;
        private boolean southWall;
        private boolean eastWall;
        private boolean westWall;
        private boolean outOfBounds;

        private char contains;

        private Room() {
            visited = false;
            northWall = true;
            southWall = true;
            eastWall = true;
            westWall = true;
            contains = ' ';
            outOfBounds = false;
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

        private void setVisited(boolean v) { visited = v;}

        private void setOutOfBounds(boolean o) { outOfBounds = o;}

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

    static void setRowNum(int n) {
        rowNum = n;
    }

    static void setColNum(int n) {
        colNum  = n;
    }

    static void setRooms (Room[][] m) {
        myRooms = m;

    }

    static int getRowNum() {
        return rowNum;
    }

    static int getColNum() {
        return colNum;
    }



    public static void main(String[] args) {
//        Room aRoom = new Room(0,0);
//        System.out.print(aRoom.toString());
//        System.out.println("room ^^^");
//        rowNums = 4;
//        colNums = 4;
//        Room[][] myDungeon = new Room[rowNums][colNums];
//        StringBuilder sb = new StringBuilder();
//        myRooms = buildFloor(myRooms, rowNums,colNums); //Generates a Dungeon with only walls.

//        for (int r = 0; r < rowNums; r++) { //Creates a String of the double array of Rooms
//            for (int d = 0; d < 3; d++) {
//                for (int c = 0; c < colNums; c++) {
//                    Room tempRoom = myDungeon[r][c];
//                    if (d == 0 && tempRoom.northWall == true) {
//                        sb.append("*** ");
//                    } else if (d == 0 && tempRoom.northWall == false) {
//                        sb.append("*-* ");
//                    }
//
//                    if (d == 1) {
//                        if (tempRoom.eastWall == true) {
//                            sb.append("*" + tempRoom.contains);
//                        }
//                        if (tempRoom.eastWall == false) {
//                            sb.append("|" + tempRoom.contains);
//                        }
//                        if (tempRoom.westWall == true) {
//                            sb.append("* ");
//                        }
//                        if (tempRoom.westWall == false) {
//                            sb.append("| ");
//                        }
//                    }
//
//                    if (d == 2 && tempRoom.southWall == true) {
//                        sb.append("*** ");
//                    } else if (d == 2 && tempRoom.southWall == false) {
//                        sb.append("*-* ");
//                    }
//                }
//                sb.append("\n");
//            }
//        }
//
//
//        System.out.println(sb);
//        System.out.print(myRooms.toString());

    }

    static void buildFloor() {
        myRooms = new Room[rowNum+2][colNum+2];
        for (int r = 0; r < rowNum+2; r++) { //Generates a 2d array of only walls
            for (int c = 0; c < colNum+2; c++) {
                Room tempRoom = new Room();
                myRooms[r][c] = tempRoom;
                if (r == 0 || c == 0 || r == rowNum + 1 || c == colNum + 1){
                    tempRoom.setOutOfBounds(true);
                    tempRoom.setContains('z');

                }
//                System.out.println(tempRoom.toString());
            }
        }


    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rowNum+2; r++) { //Creates a String of the double array of Rooms
            for (int d = 0; d < 3; d++) {
                for (int c = 0; c < colNum+2; c++) {
                    Room tempRoom = myRooms[r][c];
                    if (d == 0 && tempRoom.northWall == true) {
                        sb.append("*** ");
                    } else if (d == 0 && tempRoom.northWall == false) {
                        sb.append("*-* ");
                    }

                    if (d == 1) {
                        if (tempRoom.westWall == true) {
                            sb.append("*" + tempRoom.contains);
                        }
                        if (tempRoom.westWall == false) {
                            sb.append("|" + tempRoom.contains);
                        }
                        if (tempRoom.eastWall == true) {
                            sb.append("* ");
                        }
                        if (tempRoom.eastWall == false) {
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

        return sb.toString();
    }

    static void generateMaze() {

    }

    static void openNorth(int r, int c) {
        Room currentRoom = myRooms[r][c];
        if (currentRoom.outOfBounds == false) {
            Room nextRoom = myRooms[r-1][c];
            if (nextRoom.outOfBounds == false) {
                currentRoom.setNorth(false);
                nextRoom.setSouth(false);
            }
        }
    }

    static void openSouth(int r, int c) {
        Room currentRoom = myRooms[r][c];
        if (currentRoom.outOfBounds == false) {
            Room nextRoom = myRooms[r+1][c];
            if (nextRoom.outOfBounds == false) {
                currentRoom.setSouth(false);
                nextRoom.setNorth(false);
            }
        }
//        if (r == rowNum-1) {
//            return;
//        } else {
//            Room neighbor = myRooms[r+1][c];
//            currentRoom.setSouth(false);
//            neighbor.setNorth(false);
//        }
    }

    static void openEast(int r, int c) {
        Room currentRoom = myRooms[r][c];
        if (currentRoom.outOfBounds == false) {
            Room nextRoom = myRooms[r][c+1];
            if (nextRoom.outOfBounds == false) {
                currentRoom.setEast(false);
                nextRoom.setWest(false);
            }
        }
    }

    static void openWest(int r, int c) {
        Room currentRoom = myRooms[r][c];
        if (currentRoom.outOfBounds == false) {
            Room nextRoom = myRooms[r][c-1];
            if (nextRoom.outOfBounds == false) {
                currentRoom.setWest(false);
                nextRoom.setEast(false);
            }
        }
    }

    static void reset() {
        buildFloor();
    }

    void walk(int r, int c) {
        int nextRow = r;
        int nextCol = c;
        boolean hasNext = false;
        Random random = new Random();
        int x = random.nextInt(5)+1;
        Room nextRoom = new Room();
        nextRoom.setOutOfBounds(true);
        System.out.println(x);
        if (myRooms[r][c].outOfBounds == false) {
            if (x == 1) {
                nextRoom = myRooms[r-1][c];
                if (nextRoom.outOfBounds == false && nextRoom.visited == false) {
                    openNorth(r,c);
                    myRooms[r][c].setVisited(true);
                    hasNext = true;
                    nextRow = r-1;
                }
            }
            if (x == 2) {
                nextRoom = myRooms[r][c+1];
                if (nextRoom.outOfBounds == false && nextRoom.visited == false) {
                    openEast(r,c);
                    myRooms[r][c].setVisited(true);
                    hasNext = true;
                    nextCol = c+1;
                }
            }
            if (x == 3) {
                nextRoom = myRooms[r+1][c];
                if (nextRoom.outOfBounds == false && nextRoom.visited == false) {
                    openSouth(r,c);
                    myRooms[r][c].setVisited(true);
                    hasNext = true;
                    nextRow = r+1;
                }
            }
            if (x == 4) {
                nextRoom = myRooms[r][c-1];
                if (nextRoom.outOfBounds == false && nextRoom.visited == false) {
                    openWest(r,c);
                    myRooms[r][c].setVisited(true);
                    hasNext = true;
                    nextCol = c-1;
                }
            }
            if (x == 5) {
                myRooms[r][c].setVisited(true);
                hasNext = false;
            }
        }
        if (hasNext == true) {
            walk(nextRow, nextCol);
        }


    }

    void hunt() {
        for (int r = 0; r < rowNum+2; r++) {
            for (int c = 0; c < colNum+2; c++) {
                if (myRooms[r][c].outOfBounds == false && myRooms[r][c].visited == false) {
                    System.out.println("room unvisited " + r + " " + c);
                    Room northRoom = myRooms[r-1][c];
                    Room eastRoom = myRooms[r][c+1];
                    Room southRoom = myRooms[r+1][c];
                    Room westRoom = myRooms[r-1][c-1];
                    if (northRoom.outOfBounds == false && northRoom.visited == true) {
                        openNorth(r,c);
                        walk(r,c);
                    } if (eastRoom.outOfBounds == false && eastRoom.visited == true) {
                        openEast(r,c);
                        walk(r,c);
                    } if (southRoom.outOfBounds == false && southRoom.visited == true) {
                        openSouth(r,c);
                        walk(r,c);
                    } if (westRoom.outOfBounds == false && southRoom.visited == true) {
                        openWest(r,c);
                        walk(r,c);
                    } else {
                        walk(r,c);
                    }


                }






//                if (myRooms[r][c].outOfBounds == false || myRooms[r][c].visited == false) {
//                    if (myRooms[r-1][c].outOfBounds == false && myRooms[r-1][c].visited == true) {
//                        openNorth(r,c);
//                        walk(r,c);  //checkNorth if north isn't outOfBounds and has been visited then do a walk at the current position
//                    } else if (myRooms[r][c+1].outOfBounds == false && myRooms[r][c+1].visited == true) {
//                        openEast(r, c);
//                        walk(r, c);  //checkEast if East isn't outOfBounds and has been visited then do a walk at the current position
//                    } else if (myRooms[r+1][c].outOfBounds == false && myRooms[r-1][c].visited == true) {
//                        openSouth(r, c);
//                        walk(r, c); //checkSouth if South isn't outOfBounds and has been visited then do a walk at the current position
//                    } else if (myRooms[r][c-1].outOfBounds == false && myRooms[r-1][c].visited == true) {
//                        openWest(r, c);
//                        walk(r, c);  //checkWest if West isn't outOfBounds and has been visited then do a walk at the current position
//                    } else {
//                        walk(r,c);
//                            // if none of them have been visited or are out of bounds do a walk.
//                    }
//                }
            }
        }

    }











}
