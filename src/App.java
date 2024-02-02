
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Dungeon Adventure!");

        Room r = new Room();

        System.out.println(r.toString());

        Floor f = new Floor();

        System.out.println(f.toString());
    }
}
