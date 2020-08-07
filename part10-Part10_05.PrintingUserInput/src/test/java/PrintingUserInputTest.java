


import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("10-05")
public class PrintingUserInputTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void firstTest() {
        String input = "one\ntwo\n\n";
        io.setSysIn(input);
        PrintingUserInput.main(new String[0]);

        assertTrue("The input was:\n" + input + ", the expected output was:\n" + input + "\nInstead it was:\n" + input, io.getSysOut().contains("one"));
        assertTrue("The input was:\n" + input + ", the expected output was:\n" + input + "\nInstead it was:\n" + input, io.getSysOut().contains("two"));
    }

    @Test
    public void secondTest() {
        String input = "13\n22\n1984\n\n";
        io.setSysIn(input);
        PrintingUserInput.main(new String[0]);

        assertTrue("The input was:\n" + input + ", the expected output was:\n" + input + "\nInstead it was:\n" + input, io.getSysOut().contains("13"));
        assertTrue("The input was:\n" + input + ", the expected output was:\n" + input + "\nInstead it was:\n" + input, io.getSysOut().contains("22"));
        assertTrue("The input was:\n" + input + ", the expected output was:\n" + input + "\nInstead it was:\n" + input, io.getSysOut().contains("1984"));
    }

}
