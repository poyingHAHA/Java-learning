public class Test01 {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 類實例只有一個
        Class c1 = Class.forName("User");
        Class c2 = Class.forName("User");
        Class c3 = Class.forName("User");
        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());

        // 使用newInstance創建實例
        User user = new User();
        User user2 = (User) c1.newInstance();

        System.out.println(user2 instanceof User);

    }
}
