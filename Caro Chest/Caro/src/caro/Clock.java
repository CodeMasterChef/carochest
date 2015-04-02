package caro;

/**
 *
 * @author VanNinh
 */
public class Clock {

    public boolean isRunning;
    public static boolean isLose;
    public char mark;
    public static int count = 20; 
    public void acttion() {
        isRunning = true;
        count = 20 ; 
        isLose = false;
        while (isRunning) {
            if (count == 0) {
                isLose = true;
                isRunning = false;
                        
            } else {
                System.out.println("time " + count);
               
                count--;
                try {
                    Thread.sleep(1000);
                    
                } catch (Exception e) {
                    System.err.println(""+ e.getMessage());
                }

            }
        }
    }

    public Clock(  ) {
       
        new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        acttion();
                    }
                }
        ).start();

    }

 
}
