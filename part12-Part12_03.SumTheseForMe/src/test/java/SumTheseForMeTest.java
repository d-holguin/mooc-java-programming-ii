
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("12-03")
public class SumTheseForMeTest {

    @Test
    public void methodExists() {
        assertTrue("Could not find method public static int sum(int[] array, int fromWhere, int toWhere, int smallest, int largest) from class Program.", Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).isPublic());
    }

    @Test
    public void wrongLimitsAreHandled() {
        Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        int[] numbers = {1};
        try {
            Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(numbers, -1, 3, 0, 5);
        } catch (Throwable t) {
            fail("Make sure that limits outside of the array size are handled correctly. Try:\n"
                    + "int[] numbers = {1};\n"
                    + "System.out.println(sum(numbers, -2, 3, 0, 999));");
        }
    }

    @Test
    public void testSumMethod1() {
        Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        String errorMessage = "Error occurred when calling the sum method. Try:\n"
                + "int[] numbers = {8, 2, 9, 1, 1};\n"
                + "System.out.println(sum(numbers, 0, numbers.length, 0, 999));";
        int[] numbers = {8, 2, 9, 1, 1};

        int sumOfNumbers = 0;
        try {
            sumOfNumbers = Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(numbers, 0, numbers.length, 0, 999);
        } catch (Throwable t) {
            fail(errorMessage);
        }

        assertTrue(errorMessage, sumOfNumbers == 21);
    }

    @Test
    public void testSumMethod2() {
        Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        String errorMessage = "Error occurred when calling the sum method. Try:\n"
                + "int[] numbers = {8, -2, 3, 1, 1};\n"
                + "System.out.println(sum(numbers, 0, numbers.length, 0, 999));";
        int[] numbers = {8, -2, 3, 1, 1};

        int sumOfNumbers = 0;
        try {
            sumOfNumbers = Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(numbers, 0, numbers.length, 0, 999);
        } catch (Throwable t) {
            fail(errorMessage);
        }

        assertTrue(errorMessage, sumOfNumbers == 13);
    }

    @Test
    public void testSumMethod3() {
        Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class);

        String errorMessage = "Error occurred when calling the sum method. Try:\n"
                + "int[] numbers = {8, -2, 3, 1, 1, 1, 2, 3};\n"
                + "System.out.println(sum(numbers, 5, numbers.length, 0, 999));";
        int[] numbers = {8, -2, 3, 1, 1, 1, 2, 3};

        int sumOfNumbers = 0;
        try {
            sumOfNumbers = Reflex.reflect(Program.class).staticMethod("sum").returning(int.class).taking(int[].class, int.class, int.class, int.class, int.class).invoke(numbers, 5, numbers.length, 0, 999);
        } catch (Throwable t) {
            fail(errorMessage);
        }

        assertTrue(errorMessage, sumOfNumbers == 6);
    }

}
