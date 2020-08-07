
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Points("12-08")
public class LotteryRowTest {

    public ArrayList<Integer> test() {
        LotteryRow row;
        ArrayList<Integer> numbers;

        try {
            row = new LotteryRow();
            numbers = row.numbers();
        } catch (Throwable t) {
            fail("Something went wrong when creating a new LotteryRow object! More information: " + t);
            return new ArrayList<>();
        }

        assertEquals("The total number of returned lottery numbers should be 7!", 7, numbers.size());

        Set<Integer> remainingNumbers = new HashSet<>();
        for (int i = 1; i <= 40; i++) {
            remainingNumbers.add(i);
        }

        Set<Integer> drawnNumbers = new HashSet<>();
        for (int i : numbers) {
            assertTrue("Each lottery number should be in the range 1-40. However, you returned: " + i,
                    (i >= 1 && i <= 40));
            assertTrue("The method 'containsNumber' returns the value false even though the number was in the list of drawn numbers: " + i,
                    row.containsNumber(i));
            assertTrue("LotteryRow contains the same number multiple times: " + i,
                    drawnNumbers.add(i));
            remainingNumbers.remove(i);
        }

        for (int i : remainingNumbers) {
            assertFalse("The method 'containsNumber' returns the value true even though the number is not included in the list of drawn numbers: " + i,
                    row.containsNumber(i));
        }

        return numbers;
    }

    @Test
    public void testOneRandomization() {
        test();
    }

    @Test
    public void testRandomizeNumbersRandomizesNewNumbers() {
        String error = "When the following code was executed:\n LotteryRow lotteryRow = new LotteryRow();\nSystem.out.println(lotteryRow.numbers());\nlotteryRow.randomizeNumbers();\nSystem.out.println(lotteryRow.numbers());\n";
        LotteryRow row;
        ArrayList<Integer> numbers;
        try {
            row = new LotteryRow();
            numbers = row.numbers();
        } catch (Throwable t) {
            fail("Something went wrong when creating a new LotteryRow object! More information: " + t);
            return;
        }
        String numbersString = numbers.toString();
        assertEquals("The total number of returned lottery numbers should be 7!", 7, numbers.size());
        row.randomizeNumbers();
        assertTrue(error + "The total number of returned lottery numbers should be 7. Now it was " + row.numbers().size(), 7 == row.numbers().size());
        assertFalse(error + "Your program drew the same numbers again. Quite unlikely!", numbersString.equals(row.numbers().toString()));
    }

    @Test
    public void testMultipleRandomizations() {
        int[] arr = new int[41];
        for (int i = 0; i < 200; i++) {
            for (int x : test()) {
                arr[x]++;
            }
        }

        int howMany = 0;
        for (int i = 1; i <= 40; i++) {
            if (arr[i] > 0) {
                howMany++;
            }
        }

        assertEquals("200 lottery rows generated only " + howMany
                + " different numbers! Not very random!", 40, howMany);
    }
}
