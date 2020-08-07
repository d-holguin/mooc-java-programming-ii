
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("11-10.1 11-10.2")
public class FlightControlTest {

    String klassName = "FlightControl.Main";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void ClassIsPublic() {
        assertTrue("The class Main inside the package FlightControl must be public, so it has to be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void printsMenusAndExits() throws Throwable {
        String input = "x\nx\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Airport Asset Control",
            "[1] Add an airplane",
            "[2] Add a flight",
            "[x] Exit Airport Asset Control",
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

    @Test
    public void outputOnAddingPlane() throws Throwable {
        String input = "1\nAY-123\n108\nx\nx\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Airport Asset Control",
            "[1] Add an airplane",
            "[2] Add a flight",
            "[x] Exit Airport Asset Control",
            "Give the airplane id:",
            "Give the airplane capacity:",
            "[1] Add an airplane",
            "[2] Add a flight",
            "[x] Exit Airport Asset Control",
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

    @Test
    public void outputOnAddingFlight() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "\nx\nx\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Airport Asset Control",
            "[1] Add an airplane",
            "[2] Add a flight",
            "[x] Exit Airport Asset Control",
            "Give the airplane id:",
            "Give the airplane capacity:",
            "[1] Add an airplane",
            "[2] Add a flight",
            "[x] Exit Airport Asset Control",
            "Give the airplane id:",
            "Give the departure airport id:",
            "Give the target airport id: ",
            "[1] Add an airplane",
            "[2] Add a flight",
            "[x] Exit Airport Asset Control",
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

    }

    @Test
    public void printingAirplanes1() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "x\n"
                + "1\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",
            "AY-123 (108 capacity)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

    @Test
    public void printingAirplanes2() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "x\n"
                + "1\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",};

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String line = "AY-123 (108 capacity)";
        assertRight(line, input, output, output.contains(line));

        line = "DE-213 (75 capacity)";
        assertRight(line, input, output, output.contains(line));
    }

    @Test
    public void printingFlights1() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "x\n"
                + "2\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",
            "AY-123 (108 capacity) (HEL-TXL)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

    }

    @Test
    public void printingFlights2() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "2\nAY-123\nJFK\nHEL\n"
                + "x\n"
                + "2\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String line = "AY-123 (108 capacity) (HEL-TXL)";
        assertRight(line, input, output, output.contains(line));

        line = "AY-123 (108 capacity) (JFK-HEL)";
        assertRight(line, input, output, output.contains(line));
    }

    @Test
    public void printingFlights3() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "2\nAY-123\nJFK\nHEL\n"
                + "2\nDE-213\nTXL\nBAL\n"
                + "x\n"
                + "2\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String line = "AY-123 (108 capacity) (HEL-TXL)";
        assertRight(line, input, output, output.contains(line));

        line = "AY-123 (108 capacity) (JFK-HEL)";
        assertRight(line, input, output, output.contains(line));

        line = "DE-213 (75 capacity) (TXL-BAL)";
        assertRight(line, input, output, output.contains(line));
    }

 @Test
    public void airplaneDetails1() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "x\n"
                + "3\n"
                + "AY-123\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",
            "Give the airplane id:",
            "AY-123 (108 capacity)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }
    }

 @Test
    public void airplaneDetails2() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "x\n"
                + "3\n"
                + "AY-123\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",
            "Give the airplane id:",
            "AY-123 (108 capacity)"
        };

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String line = "DE-213";
        assertFalse("Varmista, että ohjelmasi tulostusasu on täsmälleen sama kuin esimerkissä\n"
                + f(input) + "\nohjelman ei olisi pitänyt tulostaa lineä jolla teksti \"" + line + "\"!\n"
                + "ohjelmasi tulosti:\n\n" + output, output.contains("DE-213"));
        assertFalse("Make sure that the prints of your program exactly match the example\n"
                + f(input) + "\nthe program should not have printed the line with the text \"" + line + "\"!\n"
                + "your program printed:\n\n" + output, output.contains("DE-213"));
    }
    @Test
    public void complicatedInput() throws Throwable {
        String input = "1\nAY-123\n108\n"
                + "1\nDE-213\n75\n"
                + "1\nRU-999\n430\n"
                + "2\nAY-123\nHEL\nTXL\n"
                + "2\nAY-123\nJFK\nHEL\n"
                + "2\nDE-213\nTXL\nBAL\n"
                + "x\n"
                + "2\n"
                + "1\n"
                + "3\n"
                + "AY-123\n"
                + "x\n";
        MockInOut io = new MockInOut(input);
        execute(f(input));

        String[] menuLines = {
            "Flight Control",
            "[1] Print airplanes",
            "[2] Print flights",
            "[3] Print airplane details",
            "[x] Quit",};

        String output = io.getOutput();
        String op = output;
        for (String menuLine : menuLines) {
            int ind = op.indexOf(menuLine);
            assertRight(menuLine, input, output, ind > -1);
            op = op.substring(ind + 1);
        }

        String line = "AY-123 (108 capacity) (HEL-TXL)";
        assertRight(line, input, output, output.contains(line));

        line = "AY-123 (108 capacity) (JFK-HEL)";
        assertRight(line, input, output, output.contains(line));

        line = "DE-213 (75 capacity) (TXL-BAL)";
        assertRight(line, input, output, output.contains(line));

        int ind = op.indexOf("DE-213 (75 capacity) (TXL-BAL)");
        op = op.substring(ind + 1);

        line = "AY-123 (108 capacity)";
        assertRight(line, input, output, op.contains(line));

        line = "DE-213 (75 capacity)";
        assertRight(line, input, output, op.contains(line));

        line = "RU-999 (430 capacity)";
        assertRight(line, input, output, op.contains(line));

        ind = op.indexOf("RU-999 (430 capacity)");
        op = op.substring(ind + 1);

        line = "AY-123 (108 capacity)";
        assertRight(line, input, output, op.contains(line));
    }

    private void assertRight(String menuLine, String input, String output, boolean condition) {
        assertTrue("Make sure that the prints of the program exactly match the example\n"
                + f(input) + "\nthe program should have printed the line \"" + menuLine + "\" in the right place\n"
                + "your program printed:\n\n" + output, condition);
    }

    private String f(String input) {
        return "\nthe user input was:\n" + input;
    }

    private void execute(String error) throws Throwable {
        String[] args = new String[0];
        klass.staticMethod("main").
                returningVoid().
                taking(String[].class).withNiceError(error).
                invoke(args);
    }
}
