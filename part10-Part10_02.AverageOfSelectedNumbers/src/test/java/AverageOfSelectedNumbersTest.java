
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("10-02")
public class AverageOfSelectedNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void firstInputTest() {
        String input = "-1\n1\n2\nend\nn\n";
        io.setSysIn(input);
        AverageOfSelectedNumbers.main(new String[]{});

        assertTrue("When input equals:\n" + input + "\nthe average of the negative numbers should be: -1.0\nInstead it was: " + io.getSysOut(), io.getSysOut().contains("-1.0"));
        assertFalse("When input equals:\n" + input + "\nthe average of the positive numbers should not be printed.\nInstead it was: " + io.getSysOut(), io.getSysOut().contains("1.5"));
    }

    @Test
    public void secondInputTest() {
        String input = "-1\n1\n2\nend\np\n";
        io.setSysIn(input);
        AverageOfSelectedNumbers.main(new String[]{});

        assertTrue("When input equals:\n" + input + "\nthe average of the positive numbers should be: 1.5\nInstead it was: " + io.getSysOut(), io.getSysOut().contains("1.5"));
        assertFalse("When input equals:\n" + input + "\nthe average of the negative numbers should not be printed.\nInstead it was: " + io.getSysOut(), io.getSysOut().contains("-1.0"));
    }

    @Test
    public void randomInputTestNeg() {
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

        input += "end\nn\n";

        io.setSysIn(input);
        AverageOfSelectedNumbers.main(new String[]{});

        double average = numbers.stream().filter(l -> l < 0).mapToInt(i -> i).average().getAsDouble();
        String averageString = "" + average;
        if (averageString.length() > 6) {
            averageString = averageString.substring(0, 6);
        }

        assertTrue("When input equals:\n" + input + "\nthe average of the negative numbers should be: " + average + "\nInstead it was:\n" + io.getSysOut(), io.getSysOut().contains(averageString));
    }

    @Test
    public void randomInputTestPos() {
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

        input += "end\np\n";

        io.setSysIn(input);
        AverageOfSelectedNumbers.main(new String[]{});

        double average = numbers.stream().filter(l -> l > 0).mapToInt(i -> i).average().getAsDouble();
        String averageString = "" + average;
        if (averageString.length() > 6) {
            averageString = averageString.substring(0, 6);
        }

        assertTrue("When input equals:\n" + input + "\nthe average of the positive numbers should be: " + average + "\nInstead it was:\n" + io.getSysOut(), io.getSysOut().contains(averageString));
    }
}
