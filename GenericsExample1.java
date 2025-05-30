import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericsExample1 {

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>();
        stringBox.set("String 1");
        System.out.println(stringBox.get());



        Box<Integer> integerBox = new Box<>();
        integerBox.set(100);
        System.out.println(integerBox.get());

        Person person1 = new Person("person1", 12);
        Person person2 = new Person("person2", 22);
        Person person3 = new Person("person3", 32);

        List<Person> personList = Arrays.asList(person1, person2, person3);


        Box<Person> personBox = new Box<>();
        personBox.set(person1);
        System.out.println(personBox.get());


        Box<List<Person>> personsBox = new Box<>();
        personsBox.set(personList);
        System.out.println(personsBox.get());

    }
}
