
import java.util.HashMap;

public class Program {

    public static void main(String[] args) {

        HashMap<String, Book> hashmap = new HashMap<>();
        hashmap.put("sense", new Book("Sense and Sensibility", 1811, "..."));
        hashmap.put("prejudice", new Book("Pride and prejudice", 1813, "...."));

        printValues(hashmap);
        System.out.println("---");
        printValueIfNameContains(hashmap, "prejud");
        // Test your program here!
    }

    public static void printValues(HashMap<String, Book> hashMap) {

        for (Book e : hashMap.values()) {
            System.out.println(e);
        }

    }

    public static void  printValueIfNameContains(HashMap<String, Book> hashMap, String text) {

        for (Book e : hashMap.values()) {
            if (e.getName().contains(text.toLowerCase().trim())) {

                System.out.println(e);
            }
        }

    }

}
