package model;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Window extends JFrame implements KeyListener{

    private final Hero myHero;
    private final Floor myFloor;

    private final JTextArea myDisplayedFloor;

    Window(){
        super();

        myHero = new Hero("model.Hero");
        myFloor = new Floor(7);

        int[] heroPos = myHero.getPosition();
        myFloor.addCharacter(heroPos[0], heroPos[1], myHero);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myDisplayedFloor = new JTextArea();
        myDisplayedFloor.setBounds(0,0,800,800);
        myDisplayedFloor.setText(myFloor.toString());
        
        if(System.getProperty("os.name").startsWith("Mac")){
            myDisplayedFloor.setFont(new Font("PT Mono", 1, 44));
        }else if(System.getProperty("os.name").startsWith("Windows")){
            myDisplayedFloor.setFont(new Font("Consolas", 1, 44));
        }

        myDisplayedFloor.setEditable(false);

        myDisplayedFloor.addKeyListener(this);
        add(myDisplayedFloor);

        setSize (800, 800);    
        setLayout (null);    
        setVisible (true);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT){

            int[] heroPos = myHero.getPosition();

            myFloor.removeCharacter(heroPos[1], heroPos[0], myHero);
            heroPos[1] = Math.min(heroPos[1] + 1, myFloor.getSize() - 1);
            myFloor.addCharacter(heroPos[1], heroPos[0], myHero);

            myDisplayedFloor.setText(myFloor.toString());

        }else if(e.getKeyChar() == 'a'){
            int[] heroPos = myHero.getPosition();

            myFloor.removeCharacter(heroPos[1], heroPos[0], myHero);
            heroPos[1] = Math.max(heroPos[1] - 1, 0);
            myFloor.addCharacter(heroPos[1], heroPos[0], myHero);

            myDisplayedFloor.setText(myFloor.toString());

        }else if(e.getKeyChar() == 's'){

            int[] heroPos = myHero.getPosition();

            myFloor.removeCharacter(heroPos[1], heroPos[0], myHero);
            heroPos[0] = Math.min(heroPos[0] + 1, myFloor.getSize() - 1);
            myFloor.addCharacter(heroPos[1], heroPos[0], myHero);

            myDisplayedFloor.setText(myFloor.toString());

        }else if(e.getKeyChar() == 'w'){
            int[] heroPos = myHero.getPosition();

            myFloor.removeCharacter(heroPos[1], heroPos[0], myHero);
            heroPos[0] = Math.max(heroPos[0] - 1, 0);
            myFloor.addCharacter(heroPos[1], heroPos[0], myHero);

            myDisplayedFloor.setText(myFloor.toString());
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
