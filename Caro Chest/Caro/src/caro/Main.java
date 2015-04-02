package caro;

/*
 @author VanNinh Main.java : là đối tượng chạy chính , quyết định "cảnh game"
  hiện hành
 
 */
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {

    public static JFrame myFrame;
    public static StartPanel myStartPanel = new StartPanel();
    public static GamePanel myGamePanel = new GamePanel();
    public static NetworkPanel myNetworkPanel = new NetworkPanel();
    public static ImagePanel twoLanPlayerPanel = new ImagePanel("picture/main.png", 0, 0, 800, 400);
    public static boolean startGame;

    public Main() {

        startGame = true; // đánh dấu game đã bắt đầu 

      
        
        //  Khởi tạo cửa sổ Game
         
        myFrame = new JFrame("Game");
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        myFrame.setLayout(null);
        myFrame.setBounds(100, 100, 800, 600);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // nhạc nền 
        MusicPanel myMusicPanel = new MusicPanel();
        myFrame.add(myMusicPanel);

        // âm thanh 
        SoundPanel mySoundPanel = new SoundPanel();

        // thêm button  nhạc nền và âm thanh vào Frame chính 
        myFrame.add(mySoundPanel);
        myFrame.repaint();

        // khởi chạy menu start game 
        myFrame.add(myStartPanel);

    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {

        try { // sử Jato libary có chức năng thay đổi giao diện game đẹp hơn 
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (Exception e) {
        };
        Main myMain = new Main();
    }

}
