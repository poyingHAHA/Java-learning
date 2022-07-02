import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test04_ApplicationAdvance {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        System.out.println("=================透過無參數建構子實例化=================");
        Class c1 = Class.forName("User");
        User user = (User) c1.newInstance();

        System.out.println(user);

        System.out.println("==================透過有參數建構子實例化===================");
        Constructor constructor = c1.getDeclaredConstructor(String.class, String.class, int.class);
        User user2 = (User) constructor.newInstance("1", "Edwin", 24);
        System.out.println(user2);

        System.out.println("==================透過反射調用方法========================");
        User user3 = (User) c1.newInstance();
        Method setName = c1.getDeclaredMethod("setName", String.class);
        setName.invoke(user3, "Potter");
        System.out.println(user3.getName());

        System.out.println("==================透過反射操作屬性========================");
        User user4 = (User) c1.newInstance();
        Field privateAgeField = c1.getDeclaredField("age");

        // 關閉程序的安全檢測
        privateAgeField.setAccessible(true); // 這樣就可以操作private Field
        privateAgeField.set(user4, 100);

        System.out.println(privateAgeField.get(user4));

    }
}
