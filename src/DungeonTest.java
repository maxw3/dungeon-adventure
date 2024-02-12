// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class DungeonTest {
    public static void main(String[] args) {
        Dungeon d1 = new Dungeon();
        d1.setRowNum(5);
        d1.setColNum(5);
        System.out.println(d1.getRowNum());
        System.out.println(d1.getColNum());
        d1.buildFloor();
        System.out.println(d1.toString());
        d1.openNorth(2,2);
        d1.openSouth(2,2);
        d1.openEast(2,2);
        d1.openWest(2,2);
        d1.openNorth(0,0);
        d1.openSouth(0,0);
        d1.openEast(0,0);
        d1.openWest(0,0);
        d1.openNorth(3,3);
        d1.openSouth(3,3);
        d1.openEast(3,3);
        d1.openWest(3,3);
        d1.openNorth(0,3);
        d1.openSouth(0,3);
        d1.openEast(0,3);
        d1.openWest(0,3);
        System.out.print(d1.toString());
        System.out.println();
        d1.reset();
        //System.out.println(d1.toString());
        d1.walk(2,2);
        System.out.println(d1.toString());
        d1.hunt();
        System.out.println(d1.toString());
    }
}