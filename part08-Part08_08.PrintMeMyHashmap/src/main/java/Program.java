
import java.util.HashMap;

public class Program {

    public static void main(String[] args) {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("f.e", "for example");
        hashmap.put("etc.", "and so on");
        hashmap.put("i.e", "more precisely");

        printKeys(hashmap);
        System.out.println("---");
        printKeysWhere(hashmap, "i");
        System.out.println("---");
        printValuesOfKeysWhere(hashmap, ".e");
        // Test your program here!

    }

    public static void printKeys(HashMap<String, String> hashMap) {

        for (String key : hashMap.keySet()) {

            System.out.println(key);

        }

    }
    ///Use contains
    public static void printKeysWhere(HashMap<String, String> hashMap, String text) {

        for (String key : hashMap.keySet()) {

            if (key.contains(cleanString(text))) {
                System.out.println(key);
            }

        }

    }

    public static void printValuesOfKeysWhere(HashMap<String, String> hashMap, String text) {

        for (String key : hashMap.keySet()) {

            if (key.contains(cleanString(text))) {
                
                
                System.out.println(hashMap.get(key));
            }

        }

    }

    public static String cleanString(String strToClean) {

        if (strToClean == null) {
            return "";
        }

        return strToClean.toLowerCase().trim();

    }

}
