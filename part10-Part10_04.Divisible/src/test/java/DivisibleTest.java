
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("10-04")
public class DivisibleTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(2);
        numbers.add(-17);
        numbers.add(-5);
        numbers.add(7);

        ArrayList<Integer> divisible = Divisible.divisible(numbers);

        assertTrue("When applied to the list " + numbers + " the method divisible should return a list with 3 values.", divisible.size() == 3);
        assertTrue("When applied to the list " + numbers + " the method divisible should return a list that contains the number 3.", divisible.contains(3));
        assertTrue("When applied to the list " + numbers + " the method divisible should return a list that contains the number 2.", divisible.contains(2));
        assertTrue("When applied to the list " + numbers + " the method divisible should return a list that contains the number -5.", divisible.contains(-5));

        assertTrue("When the divisible method is called on the list " + numbers + ", the list must not be altered", numbers.size() == 5);
        assertTrue("When the divisible method is called on the list " + numbers + ", the list must not be altered", numbers.get(0) == 3);
        assertTrue("When the divisible method is called on the list " + numbers + ", the list must not be altered", numbers.get(1) == 2);
        assertTrue("When the divisible method is called on the list " + numbers + ", the list must not be altered", numbers.get(2) == -17);
        assertTrue("When the divisible method is called on the list " + numbers + ", the list must not be altered", numbers.get(3) == -5);
        assertTrue("When the divisible method is called on the list " + numbers + ", the list must not be altered", numbers.get(4) == 7);
    }

}
