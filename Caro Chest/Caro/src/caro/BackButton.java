
package caro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author VanNinh
 */
public class BackButton extends JButton {

    String presentPanel;

    public BackButton(String presnetPanel) {
        this.presentPanel = presnetPanel;
        Icon myIcon = new ImageIcon(getClass().getResource("picture/BackButton.png"));
        setIcon(myIcon);
        setBorderPainted(false);
        setBounds(650, 80, 60, 60);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if ("GamePanel".equals(presentPanel)) {
                    Main.myFrame.remove(Main.myGamePanel);
                }

                if ("NetworkPanel".equals(presentPanel) ) {
                    Main.myFrame.remove(Main.myNetworkPanel);

//                    /* Khi trở về startMenu, nếu là host thì tất cả kết nối của server bị ngắt*/
                   
                } // end of else -if clause 
                if("towLanPlayerPanel".equals(presentPanel)) { 
                    Main.myFrame.remove(Main.twoLanPlayerPanel);
                    try {
                    
                   
                      
                    } catch (Exception ex) {
                       
                    }
                   
            
                }
                
                Main.myFrame.add(Main.myStartPanel);
                Main.startGame = true;
                Main.myFrame.repaint();

            }
        });

    }
}
