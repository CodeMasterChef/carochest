package caro;

import java.io.FileInputStream;
import javax.swing.JPanel;
import javazoom.jl.player.Player;

/**
 *
 * @author VanNinh
 */
public class BackgrondMusic extends JPanel {

    private int total;  // tổng số byte của file nhạc  
    private int stop;    // số byte chưa đọc

    private FileInputStream FIS;
    private Player myPlayer;

    private boolean firstPress = true;
    public boolean isStopping = false;
    public boolean isPlay = true;

    public void stopMusic() {
        try {
            System.out.println("stop: " + stop);
            stop = FIS.available();
            myPlayer.close();
        } catch (Exception e) {
        }
    }

    public void repeatMusic() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                while (isStopping == false) {
                    try {
                        Thread.sleep(100);
                        int now = FIS.available();
                        if (now == 0) {
                            playMusic(-1);
                        }
                    } catch (Exception e) {

                    }

                }
            }
        }).start();;
    }

    public void playMusic(final int pos) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    FIS = new FileInputStream("sound/bkmusic.mp3");
                    System.out.println("total " + total);
                    total = FIS.available();
                    if (pos > -1) {
                        FIS.skip(pos);
                    }
                    myPlayer = new Player(FIS);
                    myPlayer.play();
                } catch (Exception e) {

                }

            }
        }
        ).start();;

    }

    public void resumeMusic() {
        try {
            System.out.println("resume" + (total - stop));
            if (stop == 0) {
                playMusic(-1);
            } else {
                playMusic(total - stop);
            }
        } catch (Exception e) {

        }

    }

    public BackgrondMusic() {
        playMusic(-1);
        isStopping = false;
        repeatMusic();

    }

}
