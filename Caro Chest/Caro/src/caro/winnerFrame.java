
package caro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author VanNinh
 */
public class winnerFrame extends JFrame {
   
    public winnerFrame(int winner ) { 
        setLayout(null);
        setVisible(true);
        setBounds(200, 200, 405, 230);
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
          ImagePanel  winnerPicture ; 
         if( winner ==0) { // hòa nhau 
               winnerPicture = new ImagePanel("picture/draw.png", 0, 0, 400, 200) ; 
              
          }
         else if(winner==3) { // người chơi thứ 1 thua máy tính
               winnerPicture = new ImagePanel("picture/loser1.gif", 0, 0, 400, 200) ; 
        } 
        else {
              winnerPicture = new ImagePanel("picture/winner"+winner+".gif", 0, 0, 400, 200) ; 
        }
        
      
    
        JButton acceptButton = new JButton("Xác nhận") ; 
        acceptButton.setBounds(150, 170, 90, 25);
        
        SoundPlayer mySound = new SoundPlayer() ; // âm thanh 
        if(GamePanel.winner==0 && GamePanel.canPlaySound) { 
             mySound.playSound("sound/draw.mp3" );
        }
        else if( GamePanel.winner==3 && GamePanel.canPlaySound )  { // computer win 
              mySound.playSound("sound/GameOver.mp3" );
        }
        else if(  (GamePanel.winner==1 || GamePanel.winner==2) && GamePanel.canPlaySound )
             mySound.playSound("sound/win.mp3" );
       
        acceptButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Main.myFrame.remove(Main.myGamePanel);
                Main.myFrame.add(Main.myStartPanel) ; 
                Main.myFrame.repaint();
                 Main.startGame = true ; 
                dispose(); //close window
            
                 
            }
        });
        
        winnerPicture.add(acceptButton) ; 
        repaint() ;
          
        add(winnerPicture) ; 
     
   
        
       // repaint(); 
    }
    
    
}
