package caro;

/**
 *
 * @author VanNinh
 */
public class Computer {

    private int height;
    private int width;
    public int optimalX;
    public int optimalY;


    private EvalBoard myEvalBoard;

    public Computer(int height, int width) {
        this.height = height;
        this.width = width;

       
        myEvalBoard = new EvalBoard(height, width);
    }

    public void calculateEvalBoard(int player, int[][] status) {
        int[] DScore = new int[]{0, 1, 9, 81, 729};
        int[] AScore = new int[]{0, 2, 18, 162, 1458};

        int row, col, ePC, eHuman;

        myEvalBoard.ResetBoard();
        //Đánh giá theo hàng ngang
        for (row = 0; row < height; row++) {
            for (col = 0; col < width - 4; col++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row][col + i] == 1) {
                        eHuman++;
                    }
                    if (status[row][ col + i] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) // một trong 2 bằng 0 và không đồng thời bằng 0
                {
                    for (int i = 0; i < 5; i++) {
                        if (status[row][col + i] == 0) // Nếu ô chưa đánh
                        {
                            if (eHuman == 0) {
                                if (player == 1) {
                                    myEvalBoard.EBoard[row][ col + i] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row][ col + i] += AScore[ePC];
                                }
                            }
                            else if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row][col + i] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row][col + i] += AScore[eHuman];
                                }
                            }
                            if (eHuman == 4 || ePC == 4) {
                                myEvalBoard.EBoard[row][col + i] *= 2;
                            }
                        }
                    }

                }
            }
        }
        //Đánh giá theo cột
        for (col = 0; col < width; col++) {
            for (row = 0; row < height - 4; row++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row + i][col] == 1) {
                        eHuman++;
                    }
                    if (status[row + i][col] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (status[row + i][col] == 0) // cộng điểm cho các ô chưa đánh
                        {
                            if (eHuman == 0) {
                                if (player == 1) {
                                    myEvalBoard.EBoard[row + i][col] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row + i][col] += AScore[ePC];
                                }
                            }
                            if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row + i][ col] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row + i][col] += AScore[eHuman];
                                }
                            }
                            if (eHuman == 4 || ePC == 4) {
                                myEvalBoard.EBoard[row + i][col] *= 2;
                            }
                        }
                    }

                }
            }
        }

        //Đánh giá theo đường chéo chính
        for (col = 0; col < width - 4; col++) {
            for (row = 0; row < height - 4; row++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row + i][col + i] == 1) {
                        eHuman++;
                    }
                    if (status[row + i][col + i] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (status[row + i][col + i] == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0) {
                                if (player == 1) {
                                    myEvalBoard.EBoard[row + i][col + i] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row + i][col + i] += AScore[ePC];
                                }
                            }
                            if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row + i][col + i] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row + i][col + i] += AScore[eHuman];
                                }
                            }
                            if (eHuman == 4 || ePC == 4) {
                                myEvalBoard.EBoard[row + i][col + i] *= 2;
                            }
                        }
                    }

                }
            }
        }

            //Đánh giá theo đường chéo phụ
        for (row = 4; row < width; row++) {
            for (col = 0; col < height - 4; col++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row - i][col + i] == 1) {
                        eHuman++;
                    }
                    if (status[row - i][col + i] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (status[row - i][col + i] == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0) {
                                if (player == 1) {
                                    myEvalBoard.EBoard[row - i][col + i] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row - i][col + i] += AScore[ePC];
                                }
                            }
                            if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row - i][col + i] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row - i][ col + i] += AScore[eHuman];
                                }
                            }
                            if (eHuman == 4 || ePC == 4) {
                                myEvalBoard.EBoard[row - i][col + i] *= 2;
                            }
                        }
                    }

                }
            }
        }

    }

//    public void printEvalBoard() {
//        for (int row = 0; row < height; row++) {
//            for (int col = 0; col < width; col++) {
//                System.out.print(myEvalBoard.EBoard[row][col] + " ");
//            }
//            System.out.println(" ");
//        }
//        System.out.println("--------------------------------- ");
//    }

    //Ham tim nuoc di cho may
    public void FindMove(int[][] status) {


        calculateEvalBoard(2, status);

        Point temp =  myEvalBoard.MaxPos();
       
        optimalX = temp.x;
        optimalY = temp.y;
        System.out.println("op: " + temp.x + " " + temp.y);

    }

}
