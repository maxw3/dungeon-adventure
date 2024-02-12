import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Dungeon Adventure!");

        Floor f = new Floor(6);

        f.getRooms()[0][0].addDungeonCharacter(new Hero("Hero"));

        System.out.println(f.toString());

        Scanner scanner = new Scanner(System.in);

        char input;

        while((input = (char)scanner.nextByte()) > 0) {
            System.out.println("Inputing");
        }
    }
}
