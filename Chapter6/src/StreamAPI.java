import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
    public static void main(String[] args) {
        Stream.of("Ron", "Edwin", "Harry")
                .sorted()
                .forEach(name -> System.out.println(name));

        System.out.println("================================");
        String[] names = {"Ron", "Edwin", "Harry", "Grace", "Mike"};
        Stream.of(names)
                .sorted()
                .forEach(name -> System.out.println(name));

        System.out.println("================================");
        Arrays.stream(names)
                .sorted()
                .forEach(name -> System.out.println(name));

        System.out.println("================================");
        Stream.of(names)
                .filter(name -> !name.startsWith("R")) // return true的會被留下來
                .forEach(name -> System.out.println(name));

        System.out.println("================================");
        Arrays.stream(new int[] {2, 4, 6, 8, 10})
                .map(num -> num*num)
                .average()
                .ifPresent(System.out::println);

        System.out.println("================================");
        ArrayList<String> people = new ArrayList<>();
        people.add("Edwin");
        people.add("Edward");
        people.add("John");
        people.add("Ron");
        people.add("Mike");
        people.add("Ray");
        people.add("ron");

        people.stream()
                .map(name -> name.toLowerCase())
                .filter(name -> name.startsWith("r"))
                .forEach(System.out::println);

        System.out.println("================================");
        try {
           Stream<String> bands = Files.lines(Paths.get("bands.txt"), StandardCharsets.UTF_8);
//           bands
//                   .sorted()
//                   .filter(name -> name.length() > 13)
//                   .forEach(System.out::println);
//           bands.close();

           List<String> mylist = bands
                   .filter(name -> name.contains("jit"))
                   .collect(Collectors.toList());

           mylist.forEach(name -> System.out.println(name));
           bands.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
