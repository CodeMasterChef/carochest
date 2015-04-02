

package caro;

import java.io.FileInputStream;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;

/**
 *
 * @author VanNinh
 */
public class SoundPlayer {
    private FileInputStream FIS  ; 
    private Player myPlayer ; 
  
  
    public void playSound(String path ) { 
      
        try {            
                  
              FIS = new FileInputStream(path) ; 
                  
              myPlayer = new Player(FIS) ; 
           
               new Thread (
                    new Runnable() {

                @Override
                public void run() {
                        try { 
                         myPlayer.play(); 
                                              
                    }catch(Exception e) { 
                    }                                                                              
                }
            }
            ).start();  ; 
             
        }
        catch(Exception e ) { 
            JOptionPane.showInputDialog(e.getMessage()) ; 
    }
        
    }
 
   
}
