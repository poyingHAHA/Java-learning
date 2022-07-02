import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static Account account = new Account();

    private static  class Account{
        private int balance = 0;
        private static Lock lock = new ReentrantLock();

        public int getBalance(){
            return this.balance;
        }

        public void deposit(){ // deposit需要上鎖
            lock.lock();
            int newBalance = this.balance + 1;
            try{
                Thread.sleep(3);
                this.balance = newBalance;
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    private static class AddMoneyTask implements Runnable{
        @Override
        public void run(){
            account.deposit();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //create 100 task
        for(int i = 0; i < 5000; i++){
            executorService.execute(new AddMoneyTask());
        }

        executorService.shutdown(); // 不是結束任務，是避免接受更多任務
        while(!executorService.isTerminated()){
        } // 只要pool裡面還有任務沒完成，就會一直在while loop裡面

        System.out.println("The balance is "+ account.getBalance());

    }
}
