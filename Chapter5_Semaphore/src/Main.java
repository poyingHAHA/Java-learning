import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    static int[] buffer = new int[3];
    static int producerIndex = 0; // Producer正在處裡的index
    static int consumerIndex = 0;
    static Semaphore s_lock, n_lock, e_lock;

    private static void append(int i){
        buffer[producerIndex] = i;
        if(producerIndex != buffer.length - 1){
            producerIndex++; // 下次拿的位置
        }else{ // 已經在buffer底端
            producerIndex = 0;
        }
    }

    private static int take(){
        int tmp = buffer[consumerIndex];

        if(consumerIndex != buffer.length - 1){
            consumerIndex++; // 下次拿的位置
        }else{ // 已經在buffer底端
            consumerIndex = 0;
        }
        return tmp;
    }

    private static class ProducerTask implements Runnable{
        int thread_id;

        public ProducerTask(int thread_id){
            this.thread_id = thread_id;
            System.out.println("Producer #"+thread_id+" launched.");
        }

        @Override
        public void run() {
            try{
                int value_took;
                for(int i=0; i<20; i++){
                    e_lock.acquire(); // 確認buffer裡面還有東西
                    s_lock.acquire(); // 上鎖
                    int randomInt = (int)(Math.random() * 10);
                    System.out.println("Producer #" + thread_id + " produced "+randomInt);
                    append(randomInt);
                    s_lock.release(); // 解鎖
                    n_lock.release();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private static class ConsumerTask implements Runnable{
        int thread_id;

        public ConsumerTask(int thread_id){
            this.thread_id = thread_id;
            System.out.println("Consumer #"+thread_id+" launched.");
        }

        @Override
        public void run() {
            try{
                int value_took;
                for(int i=0; i<20; i++){
                    n_lock.acquire(); // 確認buffer裡面還有東西
                    s_lock.acquire(); // 上鎖
                    value_took = take();
                    System.out.println("Consumer #" + thread_id + " consumed "+value_took);
                    s_lock.release(); // 解鎖
                    e_lock.release();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Using "+4+" threads.");
        s_lock = new Semaphore(1);  // 只有0跟1
        n_lock = new Semaphore(0);  // buffer內有多少東西
        e_lock = new Semaphore(buffer.length);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            if(i % 2 == 0){
                executorService.execute(new ProducerTask(i));
            }else{
                executorService.execute(new ConsumerTask(i));
            }
        }

        executorService.shutdown();
    }
}
