import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class MergeSort{
    public static void mergeSort(int[] list){
        if (list.length > 1){ // 終止條件
            // merge sort the first half
            int[] firstHalf = new int[list.length / 2];
            System.arraycopy(list,0, firstHalf, 0, list.length / 2);
            mergeSort(firstHalf);

            // merge sort the second half
            int secondHalfLength = list.length - list.length/ 2;
            int[] secondHalf = new int[secondHalfLength];
            System.arraycopy(list, list.length / 2, secondHalf,0, secondHalfLength);
            mergeSort(secondHalf);

            // merge
            merge(firstHalf, secondHalf, list);
        }
    }

    public static void merge(int[] list1, int[] list2, int[] temp){
        int i = 0;
        int j = 0;
        int k = 0;

        while(i < list1.length && j < list2.length){ // list1 跟 list2的比較
            if(list1[i] < list2[j]){
                temp[k] = list1[i];
                i++;
            }else{
                temp[k] = list2[j];
                j++;
            }
            k++;
        }

        while(i < list1.length){ // list1剩下沒比的
            temp[k] = list1[i];
            k++;
            i++;
        }
        while(j < list2.length){ // list2剩下沒比的
            temp[k] = list2[j];
            k++;
            j++;
        }
    }
}

public class Main {

    private static class SortTask extends RecursiveAction{

        private final int THRESHOLD = 500;
        private int[] list;

        public SortTask(int[] list){
            this.list = list;
        }

        @Override
        protected void compute() {
            if(list.length < THRESHOLD){ // 小於500個就直接用java內建的sort就好
                Arrays.sort(list);
            }else{
                // merge sort the first half
                int[] firstHalf = new int[list.length / 2];
                System.arraycopy(list,0, firstHalf, 0, list.length / 2);

                // merge sort the second half
                int secondHalfLength = list.length - list.length/ 2;
                int[] secondHalf = new int[secondHalfLength];
                System.arraycopy(list, list.length / 2, secondHalf,0, secondHalfLength);

                invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));
                MergeSort.merge(firstHalf, secondHalf, list);
            }
        }
    }

    public static void paralleMergeSort(int[] list){
        RecursiveAction recursiveAction = new SortTask(list);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(recursiveAction);
    }

    public static void main(String[] args) {
        final int SIZE = 7000000;
        int[] list1 = new int[SIZE];
        int[] list2 = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            list1[i] = (int) (Math.random() * 1000000);
            list2[i] = (int) (Math.random() * 1000000);
        }

        long startTime = System.currentTimeMillis();
        paralleMergeSort(list1);
        long endTime = System.currentTimeMillis();
        System.out.println("Parallel time with "+ Runtime.getRuntime().availableProcessors() + " processors are "+(endTime - startTime)+" millisecond");

        startTime = System.currentTimeMillis();
        MergeSort.mergeSort(list2);
        endTime = System.currentTimeMillis();
        System.out.println("Sequential time is "+ (endTime - startTime)+ " milliseconds");
    }
}
