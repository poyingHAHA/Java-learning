public class Test02 {
    public static void main(String[] args) throws ClassNotFoundException {
        Person person = new Student();
        System.out.println("這個人是 "+person.name);

        Class c1 = person.getClass();
        System.out.println(c1.hashCode());

        Class c2 = Class.forName("MyStudent");
        System.out.println(c2.hashCode());

        Class c3 = MyStudent.class;
        System.out.println(c3.hashCode());

        Class c4 = Integer.TYPE;
        System.out.println(c4);

        Class c5 = c1.getSuperclass();
        System.out.println(c5);

        Class c6 = void.class;
        System.out.println(c6);
    }
}

class Person{
    public String name;

    public Person(){
    }

    public Person(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Teacher extends Person{
    public Teacher(){
        this.name = "老師";
    }
}

class Student extends Person{
    public Student(){
        this.name = "學生";
    }
}