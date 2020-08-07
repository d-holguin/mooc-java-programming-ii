
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // You can test your method here

        Set<String> set = new HashSet<>();
        set.add("one");
        set.add("one");
        set.add("two");

        for (String element : set) {
            System.out.println(element);
        }

    }

    // implement the method returnSize here, which returns
    // the number of elements in the set that it receives as a parameter.
    public static int returnSize(Set<String> setList) {

        return setList.size();
    }

}
