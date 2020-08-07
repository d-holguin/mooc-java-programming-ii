
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class RegularExpressionsTest {

    String klassName = "Checker";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Points("10-15.1")
    @Test
    public void methodIsDayOfWeekExists() {
        String method = "isDayOfWeek";
        assertTrue("create in the class Checker the method public boolean isDayOfWeek(String merkkijono)", klass.method(method)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    @Points("10-15.1")
    @Test
    public void noForbiddenCommands() {
        noForbiddens();
    }

    @Points("10-15.1")
    @Test
    public void isDayOfWeekAccepts() throws Throwable {
        String[] strings = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

        for (String string : strings) {
            String e = "test the following code in the main method: "
                    + "new Checker().isDayOfWeek(\"" + string + "\")\n";
            assertEquals(e, true, isDayOfWeek(string, e));
        }

    }

    @Points("10-15.1")
    @Test
    public void isDayOfWeekRejects() throws Throwable {
        String[] strings = {"m", "monn", "monday", "", "titi", "arto", "exam", "mon "};

        for (String string : strings) {
            String e = "check the code: new Checker().isDayOfWeek(\"" + string + "\")\n";
            assertEquals(e, false, isDayOfWeek(string, e));
        }
    }

    @Points("10-15.2")
    @Test
    public void methodAllVowelsExists() {
        String error = "create in the class Checker the method public boolean allVowels(String string)";
        String method = "allVowels";
        assertTrue(error, klass.method(method)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    @Points("10-15.2")
    @Test
    public void acceptsAllVowelStrings() throws Throwable {
        String[] strings = {"a", "aeiou", "aaa", "uiuiui", "uaa", "aaai", "ai"};

        for (String string : strings) {
            String e = "check the code: new Checker().allVowels(\"" + string + "\")\n";
            assertEquals(e, true, allVowels(string, e));
        }

    }

    @Points("10-15.2")
    @Test
    public void failIfConsonantsInString() throws Throwable {
        String[] strings = {"vain", "aaaab", "waeiou", "x", "aaaaaaqaaaaaaaaa", "eke"};

        for (String string : strings) {
            String e = "check the code: new Checker().allVowels(\"" + string + "\")\n";
            assertEquals(e, false, allVowels(string, e));
        }
    }

    @Points("10-15.2")
    @Test
    public void noForbiddenCommands2() {
        noForbiddens();
    }

    @Points("10-15.3")
    @Test
    public void onMetodiKellonaika() {
        String error = "create in the class Checker the method public boolean timeOfDay(String string)";
        String method = "timeOfDay";
        assertTrue(error, klass.method(method)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    private boolean timeOfDay(String string, String e) throws Throwable {
        Object t = Reflex.reflect(klassName).constructor().takingNoParams().invoke();
        String method = "timeOfDay";
        return klass.method(method)
                .returning(boolean.class).taking(String.class).withNiceError(e).invokeOn(t, string);
    }

    private boolean isDayOfWeek(String mj, String v) throws Throwable {
        Object t = Reflex.reflect(klassName).constructor().takingNoParams().invoke();
        String method = "isDayOfWeek";
        return klass.method(method)
                .returning(boolean.class).taking(String.class).withNiceError(v).invokeOn(t, mj);
    }

    @Points("10-15.3")
    @Test
    public void kellonaikaHyvaksyyOikeat() throws Throwable {
        String[] mj = {"20:00:00", "11:24:00", "04:59:31", "14:41:16", "23:32:23", "20:00:59", "18:38:59"};

        for (String pv : mj) {
            String e = "check the code: new Checker().timeOfDay(\"" + pv + "\")\n";
            assertEquals(e, true, timeOfDay(pv, e));
        }

    }

    @Points("10-15.3")
    @Test
    public void timeOfDayRefusesWrongOnes() throws Throwable {
        String[] strings = {"a", "aaaaaaa", "3:59:31", "24:41:16", "23:61:23", "20:00:79",
            "13:9:31", "21:41:6", "23,61:23", "20:00;79"};

        for (String time : strings) {
            String e = "check the code: new Checker().timeOfDay(\"" + time + "\")\n";
            assertEquals(e, false, timeOfDay(time, e));
        }

    }

    private boolean allVowels(String m, String e) throws Throwable {
        String method = "allVowels";

        Object t = Reflex.reflect(klassName).constructor().takingNoParams().invoke();

        return klass.method(method)
                .returning(boolean.class).taking(String.class).withNiceError(e).invokeOn(t, m);
    }

    private void noForbiddens() {
        try {
            Scanner scanner = new Scanner(Paths.get("src", "main", "java", "Checker.java").toFile());
            int inMain = 0;
            while (scanner.hasNext()) {

                String error = "Since we're practising using the command String.match-komennon, don't use the command ";

                String row = scanner.nextLine();

                if (row.contains("void main(") || row.contains("boolean timeOfDay(")) {
                    inMain++;
                } else if (inMain > 0) {

                    if (row.contains("{") && !row.contains("}")) {
                        inMain++;
                    }

                    if (row.contains("}") && !row.contains("{")) {
                        inMain--;
                    }
                    continue;
                }

                if (inMain > 0) {
                    continue;
                }

                String f = "equals";
                if (row.contains(f)) {
                    fail(error + f + " problem on row " + row);
                }

                f = "charAt";
                if (row.contains(f)) {
                    fail(error + f + " problem on row " + row);
                }

                f = "indexOf";
                if (row.contains(f)) {
                    fail(error + f + " problem on row " + row);
                }

                f = "contains";
                if (row.contains(f)) {
                    fail(error + f + " problem on row " + row);
                }

                f = "substring";
                if (row.contains(f)) {
                    fail(error + f + " problem on row " + row);
                }

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
