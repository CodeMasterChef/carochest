package caro;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author VanNinh
 */
public class GamePanel extends JPanel {

    public static int winner; // player 1 win -> 1 ;  player 2 win -> 2  ; computer win->3 ;draw ->0   
    public int height = 16 ; // chiều cao bàn cờ , mặc định la 16 ô 
    public int width  = 16 ;  // chiều rộng bàn cờ , mặc định la 16 ô 
    public int numberPlayer;
    public int player; // lượt người chơi hiện hành : 1-> player 1 ; 2 -> computer|player 2 
    public StatusBoard myStatus; // bảng trạng thái gồm các giá trị : 0 chưa đánh , 1|2 đã đánh
    public int address; // địa chỉ ô được click chuột
  
    public SoundPlayer mySound = new SoundPlayer(); // đối tượng chơi âm thanh 
    public static boolean canPlaySound = true; // biến điều kiện xem có thể chơi âm thanh
    
     
    public static ImagePanel backgroundPanel; // JPanel chính 
    public ImagePanel tablePanel; // JPanel chứa các ô vuông 
    public BackButton myBackButton; // JButton quay về màn hình chính 
   
    public Computer myComputer; // đối tượng computer có khả năng tính toán nước đi
    public Check myCheck; // đối tượng kiểm tra xem có ai thắng hay hòa? 
  
 
    public MouseAdapter myAction; // đối tượng tạo hành động cho các ô 
 
 


    public GamePanel() {
        /* khởi tạo các giá trị ban đầu cần thiết  */
        winner = -1 ; 
        setBounds(0, 0, 800, 600);
        setLayout(null);

        height = 16;
        width = 16;

        myCheck = new Check(height, width);
        myStatus = new StatusBoard(height, width);
        myComputer = new Computer(height, width);

        // add button back to menu 
        myBackButton = new BackButton("GamePanel");
        add(myBackButton);

        // mainPanel 
        player = 1;  // nguoi thu 1 đi dau tien 

        backgroundPanel = new ImagePanel("picture/main.png", 0, 0, 800, 600);

        // panel chứa bàn cờ 
        tablePanel = new ImagePanel("picture/table.png", 20, 20, 480, 480);

        // tạo các ô cờ 
        ImagePanel[][] mySquare = new ImagePanel[16][16];
       normalGame();
       
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                myStatus.statusBoard[i][j] = 0;
                mySquare[i][j] = new ImagePanel("picture/khung.png", i * 30, j * 30, 30, 30);
                tablePanel.add(mySquare[i][j]);

                // action of normal game
             

                mySquare[i][j].addMouseListener(myAction);
            }
        }

        repaint();

        //
        add(tablePanel);

        add(backgroundPanel);

    }

    public void normalGame() {
        player = 1;
        myAction = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (canPlaySound) {
                    mySound.playSound("sound/kick.mp3");
                }

                if (Main.startGame == true) {
                    Main.myFrame.repaint();
                    ImagePanel a = (ImagePanel) e.getComponent();
                    address = tablePanel.getComponentZOrder(a);  // lấy địa chỉ ô ấn vào từ 0 đến (max -1) theo cột dọc 
                    //System.out.println("adress " + address );
                    int row = address % 16;
                    int col = address / 16;
                    // System.out.println("index " + row +" " +col +" " +status[row][col] );

                    if (myStatus.statusBoard[row][col] == 0) {

                        if (player == 1) {

                            a.setPicture("picture/khung1.png");

                            myStatus.setStatus(row, col, player);
                            repaint();

                            System.out.println("index " + row + " " + col + " " + myStatus.statusBoard[row][col]);
                            // kiem tra 
                      
                            if (myCheck.checkIt(row, col, myStatus.statusBoard, player) == true) {
                                Main.startGame = false;
                                winner = 1;
                                winnerFrame myWinnerFrame = new winnerFrame(1);

                                System.out.println(" Player 1  win !");
                            } else if (myCheck.isDraw(myStatus.statusBoard)) {
                                Main.startGame = false;
                                winner = 0;
                                winnerFrame myWinnerFrame = new winnerFrame(0);
                                System.out.println("Draw");
                            }
                            player = 2;

                            if (numberPlayer == 1) { //chế độ mội người chơi 

                                // Computer tính toán và đi
                                myComputer.calculateEvalBoard(player, myStatus.statusBoard);
                                // myComputer.printEvalBoard(); 
                                do {
                                    Computer myComputer = new Computer(height, width);
                                    myComputer.calculateEvalBoard(player, myStatus.statusBoard);
                                    myComputer.FindMove(myStatus.statusBoard);
                                    row = myComputer.optimalX;
                                    col = myComputer.optimalY;

                                } while (myStatus.statusBoard[row][col] != 0);

                                System.out.println("COm index " + row + " " + col + " " + myStatus.statusBoard[row][col]);
                                ImagePanel b = new ImagePanel("picture/khung2.png", col * 30, row * 30, 30, 30);
                                tablePanel.add(b);
                                repaint();
                                myStatus.statusBoard[row][col] = 2;
                                if (myCheck.checkIt(row, col, myStatus.statusBoard, player) == true) {
                                    Main.startGame = false;
                                    winner = 3;
                                    winnerFrame myWinnerFrame = new winnerFrame(3);

                                    System.out.println(" Computer win !");
                                } else if (myCheck.isDraw(myStatus.statusBoard)) {
                                    Main.startGame = false;
                                    winner = 0;
                                    winnerFrame myWinnerFrame = new winnerFrame(0);
                                    System.out.println("Draw");
                                }
                                player = 1;

                            }

                        } else if (player == 2) {
                            a.setPicture("picture/khung2.png");
                            myStatus.setStatus(row, col, player);
                            repaint();
                            System.out.println("index " + row + " " + col + " " + myStatus.statusBoard[row][col]);
                            // kiem tra nước đi chiến thắng?
                           
                            if (myCheck.checkIt(row, col, myStatus.statusBoard, player) == true) {
                                Main.startGame = false;
                                winner = 2;
                                winnerFrame myWinnerFrame = new winnerFrame(2);
                                System.out.println(" Player 2 win !");

                            } else if (myCheck.isDraw(myStatus.statusBoard)) {
                                Main.startGame = false;
                                winner = 0;
                                winnerFrame myWinnerFrame = new winnerFrame(0);
                                System.out.println("Draw");
                            }
                            player = 1;

                        }

                    }

                }

            }

        };
    }
}
