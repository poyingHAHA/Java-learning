import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {

    public static class RecursiveDemo extends RecursiveTask<Long>{
        private static final int MAX = 70;
        private long[] arr;
        private int start;
        private int end;

        public RecursiveDemo(long[] arr, int start, int end){
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            if((end - start) < MAX){
                for (int i = start; i < end; i++) {
                    sum += arr[i];
                }

                return sum;
            }else{ // 超過70在拆
                int middle = (start + end) / 2;
                RecursiveDemo left = new RecursiveDemo(arr, start, middle);
                RecursiveDemo right = new RecursiveDemo(arr, middle, end);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {
        long[] arr = new long[10000000];
        Random random = new Random();

        long startTIme = System.currentTimeMillis();
        long total = 0;
        for (int i = 0; i < arr.length; i++) {
            long temp = random.nextLong();
            arr[i] = temp;
            total += arr[i];
        }
        long endTime = System.currentTimeMillis();

        System.out.println("總合為 " + total);
        System.out.println("線性加法所花時間為 "+ (endTime - startTIme));

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        startTIme = System.currentTimeMillis();
        long result = forkJoinPool.invoke(new RecursiveDemo(arr,0, arr.length));
        endTime = System.currentTimeMillis();

        System.out.println("總合為 " + result);
        System.out.println("並行加法所花時間為 "+ (endTime - startTIme));
        forkJoinPool.shutdown();

    }

}
