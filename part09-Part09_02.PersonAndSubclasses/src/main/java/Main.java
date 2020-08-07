
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your test code here

        Teacher ada = new Teacher("Ada Lovelace", "24 Maddox St. London W1S 2QN", 1200);
        Teacher esko = new Teacher("Esko Ukkonen", "Mannerheimintie 15 00100 Helsinki", 5400);
        System.out.println(ada);
        System.out.println(esko);

        Student ollie = new Student("Ollie", "6381 Hollywood Blvd. Los Angeles 90028");

        int i = 0;
        while (i < 25) {
            ollie.study();
            i = i + 1;
        }
        System.out.println(ollie);

    }

    public static void printPersons(ArrayList<Person> persons) {

        for (Person e : persons) {
            System.out.println(e);
        }

    }

}
