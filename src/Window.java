import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Window extends JFrame implements KeyListener{

    JLabel l;    
    JTextArea area;

    Window(){

        l = new JLabel();    
// setting the location of  the label in frame  
        l.setBounds (20, 50, 100, 20);    
// creating the text area  
        area = new JTextArea();    
// setting the location of text area   
        area.setBounds (20, 80, 300, 300);    
// adding the KeyListener to the text area  
        area.addKeyListener(this);  
// adding the label and text area to the frame  
        add(l);  
        add(area);    

        setSize (400, 400);    
        setLayout (null);    
        setVisible (true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        l.setText ("Key Typed");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        l.setText ("Key Pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        l.setText ("Key Released");
    }
    
}
