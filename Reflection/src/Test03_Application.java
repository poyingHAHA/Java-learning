import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test03_Application {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class c1 = Class.forName("User");
        System.out.println(c1.getName());
        System.out.println(c1.getSimpleName());

        System.out.println("==================================");
        Field[] fields = c1.getFields(); // 無法獲取private屬性
        for(Field field:fields){
            System.out.println(field);
        }

        System.out.println("==================================");
        Field[] fields2 = c1.getDeclaredFields(); // 可以獲取private屬性
        for(Field field:fields2){
            System.out.println(field);
        }

        System.out.println("=================獲取指定的屬性值=================");
        Field name = c1.getDeclaredField("name");
        System.out.println(name);

        System.out.println("=================獲取類與其父類的方法=================");
        Method[] methods = c1.getMethods();
        for(Method m : methods){
            System.out.println(m);
        }

        System.out.println("=================獲取該類所有方法=================");
        Method[] methods1 = c1.getDeclaredMethods();
        for(Method m : methods1){
            System.out.println(m);
        }

        System.out.println("=================獲取指定方法=================");
        Method setName = c1.getMethod("setName", String.class);
        System.out.println(setName);

        System.out.println("=================獲取建構子=================");
        Constructor[] constructors = c1.getConstructors();
        for(Constructor c : constructors){
            System.out.println(c);
        }

    }
}
