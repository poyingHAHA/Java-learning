import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static Account account = new Account();

    private static  class Account{
        // field
        private int balance = 0;
        private static Lock lock = new ReentrantLock();
        private static Condition newDeposit = lock.newCondition(); // 跟lock有掛鉤的condition

        public int getBalance(){
            return this.balance;
        }

        public void deposit(int amount){
            lock.lock(); // 對critical region 上鎖
            balance += amount;
            System.out.println("Deposit " + amount + "\t\tCurrent Balance: " + getBalance());
            newDeposit.signalAll(); // 存完錢後叫醒所有目前正在等待的thread
            lock.unlock();
        }

        public void withdraw(int amount) {
            lock.lock(); // 對critical region 上鎖
            try{
                while (balance < amount) {
                    System.out.println("Try to withdraw "+amount+". Wait for a deposit");
                    newDeposit.await();
                }
                balance -= amount; // await 結束表示可以提款，就從balance扣掉
                System.out.println("\t\t\t\t\t\t\tWithdraw "+ amount + ", the remaining balance is "+getBalance());
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        }
    }

    public static class DepositTask implements Runnable{ // 每0.5s就存一次錢
        @Override
        public void run(){
            try{
                while(true){ // 不斷地存錢，每存完一次錢就會休息0.5s
                    account.deposit((int)(Math.random() * 20) + 5);
                    Thread.sleep(1000);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static class WithdrawTask implements Runnable{ // 不斷地領錢

        @Override
        public void run(){
            while(true){
                account.withdraw((int) (Math.random() * 20) + 5);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new DepositTask());
        executorService.execute(new WithdrawTask());
        executorService.shutdown();

    }
}
