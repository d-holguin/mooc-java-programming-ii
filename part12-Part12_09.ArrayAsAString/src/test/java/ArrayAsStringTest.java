
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;

import org.junit.Test;

@Points("12-09")
public class ArrayAsStringTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void method() {
        assertTrue("The class Program must have the method public static String arrayAsString(int[][] array).", Reflex.reflect(Program.class).staticMethod("arrayAsString").returning(String.class).taking(int[][].class).isPublic());
    }

    @Test
    public void methodPrintsNothing() {
        String out = io.getSysOut();
        int[][] matrix = {
            {0, 5, 0},
            {3, 0, 7}
        };

        try {
            Reflex.reflect(Program.class).staticMethod("arrayAsString").returning(String.class).taking(int[][].class).invoke(matrix);
        } catch (Throwable t) {

        }

        assertTrue("The method arrayAsString should print nothing.", out.equals(io.getSysOut()));
    }

    @Test
    public void test1() {

        int[][] matrix = {
            {0, 5, 0},
            {3, 0, 7}
        };

        String result = null;
        try {
            result = Reflex.reflect(Program.class).staticMethod("arrayAsString").returning(String.class).taking(int[][].class).invoke(matrix);
        } catch (Throwable t) {

        }

        result = result.trim();
        assertEquals("Test the method with the code:\nint[][] matrix = {\n"
                + "  {0, 5, 0},\n"
                + "  {3, 0, 7}\n"
                + "};\n\n"
                + "System.out.println(arrayAsString(matrix));", "050\n307", result);

    }

    @Test
    public void test2() {
        int[][] matrix = {
            {3, 2, 7, 6},
            {2, 4, 1, 0},
            {3, 2, 1, 0}
        };

        String result = null;
        try {
            result = Reflex.reflect(Program.class).staticMethod("arrayAsString").returning(String.class).taking(int[][].class).invoke(matrix);
        } catch (Throwable t) {

        }

        result = result.trim();
        assertEquals("Test the method with the code:\nint[][] matrix = {\n"
                + "  {3, 2, 7, 6},\n"
                + "  {2, 4, 1, 0},\n"
                + "  {3, 2, 1, 0}\n"
                + "};\n\n"
                + "System.out.println(arrayAsString(matrix));",
                "3276\n"
                + "2410\n"
                + "3210", result);
    }

    @Test
    public void test3() {
        int[][] matrix = {
            {1},
            {2, 2},
            {3, 3, 3},
            {4, 4, 4, 4}
        };

        String result = null;
        try {
            result = Reflex.reflect(Program.class).staticMethod("arrayAsString").returning(String.class).taking(int[][].class).invoke(matrix);
        } catch (Throwable t) {

        }

        result = result.trim();
        assertEquals("Test the method with the code:\nint[][] matrix = {\n"
                + "  {1},\n"
                + "  {2, 2},\n"
                + "  {3, 3, 3},\n"
                + "  {4, 4, 4, 4}\n"
                + "};\n\n"
                + "System.out.println(arrayAsString(matrix));",
                "1\n"
                + "22\n"
                + "333\n"
                + "4444", result);
    }

}
