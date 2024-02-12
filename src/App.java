

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Dungeon Adventure!");

        Floor f = new Floor(6);

        Hero aHero = new Hero("Hero");

        Room[][] rooms = f.getRooms();

        int currX, currY;
        currX = 0;
        currY = 0;

        rooms[currY][currX].getCharacters().add(aHero);


        System.out.println(f.toString());

        new Window();

    }

}