package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel){
        jframe=new JFrame();
        
        
        
        //Command for Jframe exiting on close
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jframe.add(gamePanel);
        
        //not resizable
        jframe.setResizable(false);
        //resolution
        jframe.pack();
        
        //pulling the screen  to center
        jframe.setLocationRelativeTo(null);
        
        //Command for jframe to be set as visible
        jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener(){

            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                
            }

            
          });
        
    }
    

}
