
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;

@Points("10-03")
public class PositiveNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    private Reflex.MethodRef1 method;

    @Before
    public void onSetup() {
        method = Reflex.reflect(PositiveNumbers.class).staticMethod("positive").returning(List.class).taking(List.class);
    }

    @Test
    public void methodPositiveExists() {
        method.requirePublic();
    }

    @Test
    public void theMethodDoesNotAlterTheListItReceivesAsAParameter() throws Throwable {
        List<Integer> numbers = createNumbersList(-3, -7, 0, 7, 3);
        List<Integer> copy = new ArrayList<>(numbers);

        method.invoke(numbers);

        assertEquals("The method positive shouldn't modify the original list. Try:\nList<Integer> numbers = new ArrayList<>();\n"
                + "numbers.add(-3);\n"
                + "numbers.add(-7);\n"
                + "numbers.add(0);\n"
                + "numbers.add(7);\n"
                + "numbers.add(3);\n"
                + "System.out.println(numbers);\n"
                + "positive(numbers);\n"
                + "System.out.println(numbers);", copy, numbers);
    }

    @Test
    public void theMethodReturnsThePositiveNumbers() throws Throwable {
        List<Integer> numbers = createNumbersList(-8, -11, -3, 1, 8, 1);
        List<Integer> positiveNumbers = numbers.stream().filter(l -> l > 0).collect(Collectors.toList());

        List<Integer> returned = (List<Integer>) method.invoke(numbers);

        assertEquals("The method positive must return a list that contains the positive numbers from the list it received as a parameter.\n"
                + "Try:\nList<Integer> numbers = new ArrayList<>();\n"
                + "numbers.add(-8);\n"
                + "numbers.add(-11);\n"
                + "numbers.add(-3);\n"
                + "numbers.add(1);\n"
                + "numbers.add(8);\n"
                + "numbers.add(1);\n"
                + "System.out.println(numbers);\n"
                + "positive(numbers);\n"
                + "System.out.println(numbers);", positiveNumbers, returned);
    }

    @Test
    public void theMethodReturnsThePositiveNumbers2() throws Throwable {
        List<Integer> numbers = createNumbersList(2, -8, -11, -3, 1, 8, 1);
        List<Integer> positiveNumbers = numbers.stream().filter(l -> l > 0).collect(Collectors.toList());

        List<Integer> returned = (List<Integer>) method.invoke(numbers);

        assertEquals("The method positive must return a list that contains the positive numbers from the list it received as a parameter.\n"
                + "Try:\nList<Integer> numbers = new ArrayList<>();\n"
                + "numbers.add(2);\n"
                + "numbers.add(-8);\n"
                + "numbers.add(-11);\n"
                + "numbers.add(-3);\n"
                + "numbers.add(1);\n"
                + "numbers.add(8);\n"
                + "numbers.add(1);\n"
                + "System.out.println(numbers);\n"
                + "positive(numbers);\n"
                + "System.out.println(numbers);", positiveNumbers, returned);
    }

    public List<Integer> createNumbersList(int... l) {
        List<Integer> numbers = new ArrayList<>();

        for (int i : l) {
            numbers.add(i);
        }

        return numbers;
    }

}
