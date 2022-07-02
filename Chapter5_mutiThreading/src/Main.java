import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class PrintNumber implements Runnable{
    private int max;

    public PrintNumber(int max){
        this.max = max;
    }

    @Override
    public void run() {
        for (int i = 0; i <= max; i++){
            System.out.println(i);
        }
    }
}

class PrintChar implements Runnable{
    private int times;
    private char aChar;

    public PrintChar(char aChar, int times){
        this.times = times;
        this.aChar = aChar;
    }

    @Override
    public void run() {
        for (int i = 0; i <= times; i++){
            System.out.println(aChar);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(new PrintChar('a', 20));
        executorService.execute(new PrintChar('b', 20));
        executorService.execute(new PrintChar('c', 20));
        executorService.execute(new PrintNumber(20));

        executorService.shutdown();
    }
}
