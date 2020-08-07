
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Divisible {

    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(2);
        numbers.add(-17);
        numbers.add(-5);
        numbers.add(7);

        ArrayList<Integer> divisible = divisible(numbers);

        divisible.stream()
                .forEach(luku -> System.out.println(luku));
    }

    public static ArrayList<Integer> divisible(ArrayList<Integer> numbers) {

        ArrayList<Integer> isDivisbleNumbers = numbers.stream()
                .filter(n -> n % 2 == 0|| n % 3 ==0 || n % 5 ==0)
//                
                .collect(Collectors.toCollection(ArrayList::new));

        // .filter(i -> i % 2 == 0)
        return isDivisbleNumbers;
    }

}
