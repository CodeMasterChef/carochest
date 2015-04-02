package caro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author VanNinh
 */
public class Server {

    /*
     *Chạy chương trình . Cho Client kết nối đến Server
     */
    public ServerSocket listener;
  
    public Thread serverThread;
  
    
    public Runnable serverAccept = new Runnable() {

        @Override
        public void run() {
            try {

                System.out.println("Tic Tac Toe Server is Running");

                while (true) {

                    Game game = new Game();

                    Game.Player playerX = game.new Player(listener.accept(), 'X');

                    Game.Player playerO = game.new Player(listener.accept(), 'O');
                                                        
                    playerX.setOpponent(playerO);
                    playerO.setOpponent(playerX);
                    game.currentPlayer = playerX;
                    playerX.start();
                    playerO.start();
                    

                }
            } catch (Exception ex) {
                System.out.println("Bỏ qua lỗi : tồn tại server ");
               
               
            } finally {
                try {
                    listener.close();

                } catch (Exception e) {

                }
            }
        }

    };

    public Server() {
    
        try {
            listener = new ServerSocket(8901);
         
        } catch (Exception ex) {

        }

        serverThread = new Thread(serverAccept);
        serverThread.start();
    }

   

    /**
     * A two-player game.
     */
    public class Game {

        /*
         * Một bảng gồm 16 x 16 ô . Mỗi ô là một player. 
         * 
         */
        private Player[][] board = new Player[16][16];
        private int[][] status = new int[16][16];

        public Game() {
            for (int row = 0; row < 16; row++) {
                for (int col = 0; col < 16; col++) {
                    board[row][col] = null;
                    status[row][col] = 0;
                }
            }
        }

      
        Player currentPlayer; // người chơi hiện tại đang có quyền đi 

        /*
         * 
         * Kiểm tra người chơi  hiện tại có chiến thắng hay không? 
         */
        public boolean hasWinner(int row, int col) {

            Check myCheck = new Check(16, 16);
            int prePlayer = (currentPlayer.mark == 'X' ? 2 : 1);
            System.out.println("Player " + prePlayer + "Win?" + myCheck.checkIt(row, col, status, prePlayer));
            if (myCheck.checkIt(row, col, status, prePlayer)) {
                
                return true;
               
            } else {
                return false;
            }

        }

        /*
        * Kiểm tra bảng có còn ô trống nào không ? 
         */
        public boolean boardFilledUp() {
            for (int row = 0; row < 16; row++) {
                for (int col = 0; col < 16; col++) {
                    if (board[row][col] == null) {
                        return false;
                    }
                }
            }
            return true;

        }

        /*
        Phương thức được gọi  bởi phayer Thread khi một người chơi cố gắng thực 
        hiện một nước đi. 
        Phương thức này kiểm tra xem nước đi có hợp lệ không. Nước đi hợp lệ 
        phải là ô còn trống và người chơi đến lượt đi của mình. Nếu hợp lệ, ô cờ 
        sẽ cặp nhật lại và chuyển lượt chơi ( currentPlayer = opponent)
           */
        public synchronized boolean legalMove(int location, Player player) {
            System.out.println("location " + location);
            int row = location / 16;
            int col = location % 16;
            if (player == currentPlayer && board[row][col] == null  ) {
                board[row][col] = currentPlayer;
                status[row][col] = (currentPlayer.mark == 'X' ? 1 : 2);
                currentPlayer = currentPlayer.opponent;
                currentPlayer.otherPlayerMoved(location);
                return true;
            }
            return false;

        }

       
        /* 
        Mỗi người chơi sẽ là một Thread. Mỗi người chơi sẽ có kí tự đánh dấu 
        là 'X' hoặc 'O'. Để giao tiếp được, mỗi Player có socket với input 
        và output stream. 
         */
        class Player extends Thread {

            char mark;
            Player opponent;
            Socket socket;
            BufferedReader input;
            PrintWriter output;

            /*
               Construct sẽ khởi tạo socket và kí tự đánh dấu. 
               Khởi tạo các luồng input và output qua mạng LAN.
               Gửi tin nhắn "Welcome" đế người chơi.        
             */
            public Player(Socket socket, char mark) {
                this.socket = socket;
                this.mark = mark;
                try {
                    input = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    output = new PrintWriter(socket.getOutputStream(),true);
                    System.out.println("sent: " + "WELCOME " + mark);
                    output.println("WELCOME " + mark);
                    output.println("MESSAGE Wait another connects");
                   
                    
                } catch (IOException e) {
                    System.out.println("Player died: " + e);
                }
            }

            /*
             * Phương thức thiết lập đối thủ cho một Player
             */
            public void setOpponent(Player opponent) {
                this.opponent = opponent;
            }

            /*
             * Xử lí tin nhắn : otherPlayerMoved .
             */
            public void otherPlayerMoved(int location) {
                System.out.println("sent: OPPONENT_MOVED " + location);
                output.println("OPPONENT_MOVED " + location);

                System.out.println(
                        hasWinner(location / 16, location % 16) ? "DEFEAT" : boardFilledUp() ? "TIE" : "");
                output.println(
                        hasWinner(location / 16, location % 16) ? "DEFEAT" : boardFilledUp() ? "TIE" : "");

            }

            /*
             phương thức run của thread.
             */
            public void run() {
                try {
                    // The thread bắt đầu khi cả hai người chơi đã kết nối
                    output.println("MESSAGE All is connected");
                
                    Client.isStartGame = true ; 
                
                    // Bảo người chơi thứ nhất Đi lượt đầu tiên
                    if (mark == 'X') {
                        output.println("MESSAGE First turn");
                       
                       
                    }
                    
                    // Serve: Nhận các yêu cầu(commands) từ client và xử lí chúng
                    while (true) {
                        String command = input.readLine();
                        if (command.startsWith("MOVE")  ) {
                            int location = Integer.parseInt(command.substring(5));
                            if (legalMove(location, this)) {
                                output.println("VALID_MOVE");
                                output.println(hasWinner(location / 16, location % 16) ? "VICTORY"
                                        : boardFilledUp() ? "TIE"
                                        : "");
                            } else {
                                output.println("MESSAGE This is not your turn");
                            }
                        } else if (command.startsWith("QUIT")) {
           
                            return;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Player died: " + e);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

    }

}
