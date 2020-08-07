

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("10-06")
public class LimitedNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void numTest() {
        String input = "7\n"
                + "14\n"
                + "4\n"
                + "5\n"
                + "4\n"
                + "-1\n";
        io.setSysIn(input);
        LimitedNumbers.main(new String[0]);

        assertTrue("The input was:\n" + input + ", expected output was:\n4\n5\n4\nInstead it was:\n" + input, io.getSysOut().contains("4"));
        assertTrue("The input was:\n" + input + ", expected output was:\n4\n5\n4\nInstead it was:\n" + input, io.getSysOut().contains("5"));
        assertTrue("The input was:\n" + input + ", expected output was:\n4\n5\n4\nInstead it was:\n" + input, !io.getSysOut().contains("14"));
        assertTrue("The input was:\n" + input + ", expected output was:\n4\n5\n4\nInstead it was:\n" + input, !io.getSysOut().contains("7"));
        assertTrue("The input was:\n" + input + ", expected output was:\n4\n5\n4\nInstead it was:\n" + input, !io.getSysOut().contains("-1"));
    }

    @Test
    public void numTest2() {
        String input = "13\n22\n5\n3242\n-1\n";
        io.setSysIn(input);
        LimitedNumbers.main(new String[0]);

        assertTrue("The input was:\n" + input + ", expected output was:\n" + 5 + "\nInstead it was:\n" + input, io.getSysOut().contains("5"));
        assertTrue("The input was:\n" + input + ", expected output was:\n" + 5 + "\nInstead it was:\n" + input, !io.getSysOut().contains("22"));
        assertTrue("The input was:\n" + input + ", expected output was:\n" + 5 + "\nInstead it was:\n" + input, !io.getSysOut().contains("3242"));
        assertTrue("The input was:\n" + input + ", expected output was:\n" + 5 + "\nInstead it was:\n" + input, !io.getSysOut().contains("13"));
        assertTrue("The input was:\n" + input + ", expected output was:\n" + 5 + "\nInstead it was:\n" + input, !io.getSysOut().contains("-1"));
    }

}
