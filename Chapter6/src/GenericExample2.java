public class GenericExample2 {
    // 泛型的methods
    // public <T> T func(T obj){}
    public static  <T> T printHello(T obj){
        return (T) (obj + ", hello");
    }

    public static <T> void printClass(T obj){ // obj參數會影響T的值
        System.out.println(obj.getClass().getName());
    }

    public static void main(String[] args) {
        printClass("Hello World");
        System.out.println(printHello("Edwin"));
    }
}
