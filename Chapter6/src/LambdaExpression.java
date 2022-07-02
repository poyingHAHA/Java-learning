import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
class PrintWithThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}

public class LambdaExpression {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(30);
        arr.add(40);
        arr.add(50);

        for (int i : arr){
            System.out.println(i);
        }

        arr.forEach(i -> System.out.println(i));

        //===============================================
        Map<String, Integer> map = new HashMap<>();
        map.put("Edwin", 30);
        map.put("Mike", 40);
        map.put("Jack", 50);

        map.forEach((k, v) -> {
            System.out.println(k + " is "+v+" years old");
        });

        //=============================================

        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            System.out.println(i);
                        }
                    }
                }
        );

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        });

        Thread t3 = new Thread(new PrintWithThread());

        t1.start();
        t2.start();
        t3.start();
    }
}
