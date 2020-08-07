
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.assertTrue;

@Points("10-13.1 10-13.2")
public class LiteracyComparisonTest {

    String expected = "Niger (2015), female, 11.01572\n"
            + "Mali (2015), female, 22.19578\n"
            + "Guinea (2015), female, 22.87104";

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void literacyComparisonTest() {
        LiteracyComparison.main(new String[]{});
        String[] output = io.getSysOut().split("\n");

        String[] expected = this.expected.split("\n");

        assertTrue("Output should include all the rows in the file. Now there were " + output.length, output.length >= expected.length);
        for (int i = 0; i < expected.length; i++) {
            assertTrue("It was expected that row " + (i + 1) + " would be \"" + expected[i] + "\".\nNow the row " + (i + 1) + " was \"" + output[i] + "\"", output[i].trim().equals(expected[i].trim()));
        }

    }
}
