import model.Floor;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Dungeon Adventure!");

        Floor f = new Floor(6);

        System.out.println(f.toString());
    }
}
