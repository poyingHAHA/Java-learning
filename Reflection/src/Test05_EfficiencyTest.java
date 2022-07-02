import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test05_EfficiencyTest {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 普通方式創建實例
        test01();
        // 反射方式創建實例，不關閉安全檢測機制
        test02();
        // 反射方式創建實例，關閉安全檢測機制
        test03();
    }
    // 普通方式創建實例
    public static void test01(){
        User user = new User();
        long startTime = System.currentTimeMillis();

        for(int i=0; i< 1000000000; i++){
            user.getName();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("普通方式執行十億次: " + (endTime - startTime) + "ms");
    }

    // 反射方式創建實例，不關閉安全檢測機制
    public static void test02() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();

        Class c1 = user.getClass();

        Method getName = c1.getDeclaredMethod("getName", null);

        long startTime = System.currentTimeMillis();

        for(int i=0; i< 1000000000; i++){
            getName.invoke(user, null);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("不關閉安全檢測機制: " + (endTime - startTime) + "ms");
    }

    // 反射方式創建實例，關閉安全檢測機制
    public static void test03() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();

        Class c1 = user.getClass();

        Method getName = c1.getDeclaredMethod("getName", null);
        getName.setAccessible(true);
        long startTime = System.currentTimeMillis();

        for(int i=0; i< 1000000000; i++){
            getName.invoke(user, null);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("關閉安全檢測機制: " + (endTime - startTime) + "ms");
    }
}
