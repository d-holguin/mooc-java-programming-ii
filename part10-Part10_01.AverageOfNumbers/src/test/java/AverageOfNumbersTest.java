

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("10-01")
public class AverageOfNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void firstInputTest() {
        String input = "2\n4\n6\nend\n";
        io.setSysIn(input);
        AverageOfNumbers.main(new String[]{});

        assertTrue("When input equals:\n" + input + "\nthe average should be: 4.0\nInstead it was: " + io.getSysOut(), io.getSysOut().contains("4.0"));
        assertFalse("Character sequence 0.6666 was found in the program output when the input was:\n" + input, io.getSysOut().contains("0.6666"));
    }

    @Test
    public void secondInputTest() {
        String input = "-1\n1\n2\nend\n";
        io.setSysIn(input);
        AverageOfNumbers.main(new String[]{});

        assertTrue("When input equals:\n" + input + "\nthe average should be: 0.666666\nInstead it was: " + io.getSysOut(), io.getSysOut().contains("0.666666"));
        assertFalse("Character sequence 0.4 was found in the program output when the input was:\n" + input, io.getSysOut().contains("4.0"));
    }

    @Test
    public void randomInputTest() {
        Random rnd = new Random();
        int numCount = rnd.nextInt(5) + 3;
        List<Integer> numbers = new ArrayList<>();

        String input = "";
        for (int i = 0; i < numCount; i++) {
            int num = rnd.nextInt() % 10000;
            numbers.add(num);

            input += num;
            input += "\n";
        }

        input += "end\n";

        io.setSysIn(input);
        AverageOfNumbers.main(new String[]{});

        double average = numbers.stream().mapToInt(i -> i).average().getAsDouble();
        String averageString = "" + average;
        if (averageString.length() > 6) {
            averageString = averageString.substring(0, 6);
        }

        assertTrue("When input equals:\n" + input + "\nthe average should be: " + average + "\nInstead it was: \n" + io.getSysOut(), io.getSysOut().contains(averageString));
    }

}
