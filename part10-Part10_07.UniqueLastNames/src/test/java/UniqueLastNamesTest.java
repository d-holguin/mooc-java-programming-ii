
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Rule;

@Points("10-07")
public class UniqueLastNamesTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void twoPersons() {
        String input = "k\nAda\nLovelace\n1815\nk\nGrace\nHopper\n1906\nquit\n";
        io.setSysIn(input);
        try {
            UniqueLastNames.main(new String[]{});
        } catch (Throwable t) {
            fail("error executing your main class: " + t.getMessage());
        }

        checkOrder(input, "Hopper", "Lovelace");
    }

    @Test
    public void threePersons() {
        String input = "k\nAlan\nTuring\n1912\nk\nAda\nLovelace\n1815\nk\nGrace\nHopper\n1906\nquit\n";
        io.setSysIn(input);
        try {
            UniqueLastNames.main(new String[]{});
        } catch (Throwable t) {
            fail("error executing your main class: " + t.getMessage());
        }

        checkOrder(input, "Hopper", "Lovelace", "Turing");
    }

    @Test
    public void printedNamesAreUnique() {
        String input = "k\nAda\nLovelace\n1815\nk\nGrace\nHopper\n1906\nk\nAda\nLovelace\n1815\nquit\n";
        io.setSysIn(input);
        try {
            UniqueLastNames.main(new String[]{});
        } catch (Throwable t) {
            fail("error executing your main class: " + t.getMessage());
        }

        checkOrder(input, "Hopper", "Lovelace");
    }
    
    private void checkOrder(String input, String... strings) {

        List<String> lines = lines();

        for (String string : strings) {
            assertTrue("When the input is:\n" + input + "\nThe output must contain the string " + string + ".", lines.indexOf(string) >= 0);
            assertTrue("When the input is:\n" + input + "\nTHe string " + string + " only one time in the output.", Collections.frequency(lines, string) == 1);
        }

        for (int i = 0; i < strings.length - 1; i++) {
            String first = strings[i];
            String second = strings[i + 1];

            assertTrue("When the input is:\n" + input + "\nThe output must contain the consecuantial strings:\n" + first + "\n" + second + "", lines.indexOf(first) + 1 == lines.indexOf(second));
        }
    }

    private List<String> lines() {
        return Arrays
                .asList(io
                        .getSysOut().split("\n"));
    }
}
