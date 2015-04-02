package caro;

public class Check {

    private int height;
    private int width;

    public Check(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isDraw(int[][] status) {
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
               
                if (status[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean rowCheck(int row, int col, int[][] status, int player) {

        int count = 0;

        for (int i = 0; i < width; i++) {
            if (status[row][i] == player) {
                count++;
                if (count >= 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean columnCheck(int row, int col, int[][] status, int player) {

        int count = 0;

        for (int i = 0; i < height; i++) {
            if (status[i][col] == player) {
                count++;
                if (count >= 5) {
                    return true;

                }

            } else {
                count = 0;
            }

        }
        return false;

    }

    public boolean leftCheck(int row, int col, int[][] status, int player) {

        int count = 0;
        try {
            while (col > 0 && row > 0) {
                col--;
                row--;
            }
            while (col < width && row < height) {
                if (status[row][col] == player) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                col++;
                row++;
            }

        } catch (Exception e) {
        }
        return false;
    }

    public boolean rightCheck(int row, int col, int[][] status, int player) {

        int count = 0;
        try {
            while (col > 0 && row < 15) {
                col--;
                row++;

            }

            while (col <= 31 && row >= 0) {
                if (status[row][col] == player) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                col++;
                row--;
            }

        } catch (Exception e) {
        }
        return false;
    }

    public boolean checkIt(int row, int col, int[][] status, int player) {
        if (rowCheck(row, col, status, player)) {
            return true;
        }

        if (columnCheck(row, col, status, player)) {
            return true;
        }
        if (leftCheck(row, col, status, player)) {
            return true;
        }
        if (rightCheck(row, col, status, player)) {
            return true;
        }
        return false;
    }

}
