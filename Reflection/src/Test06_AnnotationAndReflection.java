import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Test06_AnnotationAndReflection {
    public static void main(String[] args) throws NoSuchFieldException {
        Class c1 = MyStudent.class;

        // 通過反射操作註解
        Annotation[] annotations = c1.getAnnotations();

        for(Annotation item : annotations){
            System.out.println(item);
        }

        System.out.println("================獲取TableStudent Annotation的值================");
        TableStudent tableStudent = (TableStudent) c1.getAnnotation(TableStudent.class);
        String value = tableStudent.value(); // 得到TableStudent這個Annotation的值
        System.out.println(value);

        System.out.println("================獲取FieldStudent Annotation的值================");
        Field idField = (Field) c1.getDeclaredField("id");
        FieldStudent idFieldStudent = (FieldStudent) idField.getAnnotation(FieldStudent.class);
        System.out.println(idFieldStudent.columnName());
        System.out.println(idFieldStudent.type());
        System.out.println(idFieldStudent.length());
    }
}
