package caro;

/**
 *
 * @author VanNinh
 */
public class EvalBoard {

    public int height, width;
    public int[][] EBoard;
    public int max ; 

    public EvalBoard(int height, int width) {
        this.height = height;
        this.width = width;
        EBoard = new int[height][width];

    }

    public void ResetBoard() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                EBoard[r][c] = 0;
            }
        }
    }
    
    public Point MaxPos() {
        max = 0;
        Point p = new Point();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (EBoard[r][c] > max) {
                    p.x = r;
                    p.y = c;
                    max = EBoard[r][c];
                }

            }
        }
        return p;
    }

}
