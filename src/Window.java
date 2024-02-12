import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Window extends JFrame implements KeyListener{

    private final Hero myHero;
    private final Floor myFloor;

    JTextArea floor;

    Window(){
        super();

        myHero = new Hero("Hero");
        myFloor = new Floor(6);

        Room[][] rooms = myFloor.getRooms();

        int[] heroPos = myHero.getPosition();
        rooms[heroPos[0]][heroPos[1]].getCharacters().add(myHero);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        floor = new JTextArea();
        floor.setBounds(20,60,700,700);
        floor.setText(myFloor.toString());
        floor.setFont(new Font("Consolas", 1, 44));
        floor.setEditable(false);

        floor.addKeyListener(this);
        add(floor);

        setSize (800, 800);    
        setLayout (null);    
        setVisible (true);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'd'){

            int[] heroPos = myHero.getPosition();

            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().remove(myHero);
            heroPos[1] = Math.min(heroPos[1] + 1, myFloor.getSize() - 1);
            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().add(myHero);

            floor.setText(myFloor.toString());

        }else if(e.getKeyChar() == 'a'){
            int[] heroPos = myHero.getPosition();

            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().remove(myHero);
            heroPos[1] = Math.max(heroPos[1] - 1, 0);
            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().add(myHero);

            floor.setText(myFloor.toString());

        }else if(e.getKeyChar() == 's'){

            int[] heroPos = myHero.getPosition();

            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().remove(myHero);
            heroPos[0] = Math.min(heroPos[0] + 1, myFloor.getSize() - 1);
            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().add(myHero);

            floor.setText(myFloor.toString());

        }else if(e.getKeyChar() == 'w'){
            int[] heroPos = myHero.getPosition();

            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().remove(myHero);
            heroPos[0] = Math.max(heroPos[0] - 1, 0);
            myFloor.getRooms()[heroPos[0]][heroPos[1]].getCharacters().add(myHero);

            floor.setText(myFloor.toString());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}
