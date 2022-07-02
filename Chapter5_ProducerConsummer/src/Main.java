import java.lang.constant.ConstantDesc;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static Buffer buffer = new Buffer();

    private static class Buffer{
        private static final int CAPACITY = 1;
        private LinkedList<Integer> queue = new LinkedList<>();
        private static Lock lock = new ReentrantLock();
        private static Condition notEmpty = lock.newCondition();
        private static Condition notFull = lock.newCondition();

        public void write(int value){
            lock.unlock();
            try{
                while(queue.size() == CAPACITY){
                    notFull.await();
                }
                queue.offer(value); // add to the tail
                notEmpty.signalAll(); // 如果寫一個值，就去告訴所有notEmpty.await()，讓他們醒來
            }catch(InterruptedException e){
                e.printStackTrace();
            }finally{
                lock.unlock();
            }
        }

        public int read(){
            int value = 0;
            lock.lock();
            try{
                while(queue.isEmpty()){
                    notEmpty.await();
                }
                value = queue.remove(); // remove head
                notFull.signalAll(); // 告知所有notFull.await()醒來，讓他們知道有空位
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
                return value;
            }
        }
    }

    private static class Consumer implements Runnable{
        @Override
        public void run() {
            try{
                while (true) {
                    System.out.println("Consumer reads " + buffer.read());
                    Thread.sleep((int) (Math.random() * 1000));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private static class Producer implements Runnable{
        @Override
        public void run() {
            try{
                int i = 0;
                while(true){
                    System.out.println("Producer writes "+ i);
                    buffer.write(i++);
                    Thread.sleep((int) (Math.random() * 1000));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Producer());
        executorService.execute((new Consumer()));
        executorService.shutdown();
    }
}
