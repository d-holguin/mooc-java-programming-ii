
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MagicSquareTest {

    @Test
    @Points("12-10.1")
    public void sumsOfRowsIsCorrect() {
        int[][] arr = {{1, 2}, {3, 4}};

        MagicSquare ms = createMagicSquare(arr);

        ArrayList<Integer> sumsOfRows = ms.sumsOfRows();
        assertEquals("When the width and height of a magic square is 2, the sumsOfRows method should return a list that contains two values. Now the list had " + sumsOfRows.size() + " values.", 2, sumsOfRows.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfRows method should contain the values 3 ja 7. Now this was not the case.", sumsOfRows.contains(3));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfRows method should contain the values 3 ja 7. Now this was not the case.", sumsOfRows.contains(7));
    }

    @Test
    @Points("12-10.1")
    public void sumsOfRowsIsCorrect2() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        MagicSquare ms = createMagicSquare(arr);

        ArrayList<Integer> sumsOfRows = ms.sumsOfRows();
        assertEquals("When the width and height of a magic square is 3, the sumsOfRows method should return a list that contains three values. Now the list had " + sumsOfRows.size() + " values.", 3, sumsOfRows.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfRows method should contain the values 6, 15 ja 24. Now this was not the case.", sumsOfRows.contains(6));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfRows method should contain the values 6, 15 ja 24. Now this was not the case.", sumsOfRows.contains(15));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfRows method should contain the values 6, 15 ja 24. Now this was not the case.", sumsOfRows.contains(24));
    }

    @Test
    @Points("12-10.2")
    public void sumsOfColumnsIsCorrect() {
        int[][] arr = {{1, 2}, {3, 4}};

        MagicSquare ms = createMagicSquare(arr);

        ArrayList<Integer> sumsOfColumns = ms.sumsOfColumns();
        assertEquals("When the width and height of a magic square is 2, the sumsOfColumns method should return a list that contains two values. Now the list had " + sumsOfColumns.size() + " values.", 2, sumsOfColumns.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfColumns method should contain the values  4 ja 6. Now this was not the case.", sumsOfColumns.contains(4));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfColumns method should contain the values  4 ja 6. Now this was not the case.", sumsOfColumns.contains(6));
    }

    @Test
    @Points("12-10.2")
    public void sumsOfColumnsIsCorrect2() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        MagicSquare ms = createMagicSquare(arr);

        ArrayList<Integer> sumsOfColumns = ms.sumsOfColumns();
        assertEquals("When the width and height of a magic square is 3, the sumsOfColumns method should return a list that contains three values. Now the list had " + sumsOfColumns.size() + " values.", 3, sumsOfColumns.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfColumns method should contain the values  12, 15 ja 18. Now this was not the case.", sumsOfColumns.contains(12));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfColumns method should contain the values  12, 15 ja 18. Now this was not the case.", sumsOfColumns.contains(15));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfColumns method should contain the values  12, 15 ja 18. Now this was not the case.", sumsOfColumns.contains(18));
    }

    @Test
    @Points("12-10.3")
    public void sumsOfDiagonalsIsCorrect() {
        int[][] arr = {{1, 2}, {3, 4}};

        MagicSquare ms = createMagicSquare(arr);

        ArrayList<Integer> sumsOfDiagonals = ms.sumsOfDiagonals();
        assertEquals("When the width and height of a magic square is 2, sumsOfDiagonals method should return a list that contains two values. Now the list had " + sumsOfDiagonals.size() + " values.", 2, sumsOfDiagonals.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfDiagonals method should contain the values 5 ja 5. Now this was not the case.", sumsOfDiagonals.contains(5));

        Collections.sort(sumsOfDiagonals);
        assertEquals("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfDiagonals method should contain the values 5 ja 5. Now this was not the case.", sumsOfDiagonals.get(0), sumsOfDiagonals.get(1));
    }

    @Test
    @Points("12-10.3")
    public void sumsOfDiagonalsIsCorrect2() {
        int[][] t = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        MagicSquare ms = createMagicSquare(t);

        ArrayList<Integer> sumsOfDiagonals = ms.sumsOfDiagonals();
        assertEquals("When the width and height of a magic square is 3, sumsOfDiagonals method should return a list that contains two values. Now the list had " + sumsOfDiagonals.size() + " values.", 2, sumsOfDiagonals.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfDiagonals method should contain the values 15 ja 15. Now this was not the case.", sumsOfDiagonals.contains(15));

        Collections.sort(sumsOfDiagonals);
        assertEquals("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfDiagonals method should contain the values 15 ja 15. Now this was not the case.", sumsOfDiagonals.get(0), sumsOfDiagonals.get(1));
    }

    @Test
    @Points("12-10.3")
    public void sumsOfDiagonalsIsCorrect3() {
        int[][] arr = {{1, 1}, {5, 7}};

        MagicSquare ms = createMagicSquare(arr);

        ArrayList<Integer> sumsOfDiagonals = ms.sumsOfDiagonals();
        assertEquals("When the width and height of a magic square is 2, sumsOfDiagonals method should return a list that contains two values. Now the list had " + sumsOfDiagonals.size() + " values.", 2, sumsOfDiagonals.size());
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfDiagonals method should contain the values 6 ja 8. Now this was not the case.", sumsOfDiagonals.contains(6));
        assertTrue("When the magic square is:\n" + ms + "\nThe list returned by the sumsOfDiagonals method should contain the values 6 ja 8. Now this was not the case.", sumsOfDiagonals.contains(8));
    }

    @Test
    @Points("12-10.4")
    public void testFactory() {
        magicSquareFactory(3);
    }

    @Test
    @Points("12-10.4")
    public void testFactory2() {
        magicSquareFactory(9);
    }

    public void magicSquareFactory(int size) {
        sumsOfRowsIsCorrect();
        sumsOfColumnsIsCorrect();
        sumsOfDiagonalsIsCorrect();

        MagicSquareFactory factory = new MagicSquareFactory();
        MagicSquare ms = factory.createMagicSquare(size);

        ArrayList<Integer> sums = new ArrayList<>();
        sums.addAll(ms.sumsOfRows());
        sums.addAll(ms.sumsOfColumns());
        sums.addAll(ms.sumsOfDiagonals());

   
        assertTrue("The sum methods of the magic square that was created by MagicSquareFactory don't work as they should.", sums.size() > 2);

        for (int i = 1; i < sums.size(); i++) {
            assertEquals("The sums of rows, columns, and diagonals should be the same in a magic square.", sums.get(i - 1), sums.get(i));
        }

        ArrayList<Integer> numbers = ms.giveAllNumbers();
        assertTrue("The giveAllNumbers method of MagicSquare should return a list with all the numbers in the square.", numbers.size() > 2);
        Collections.sort(numbers);

        for (int i = 1; i < numbers.size(); i++) {
            Assert.assertNotSame("Each number in a magic square should be different. Now this was not the case in a magic square of size " + size + ":\n" + ms, numbers.get(i - 1), numbers.get(i));
        }

    }

    public MagicSquare createMagicSquare(int[][] numbers) {
        MagicSquare ms = new MagicSquare(numbers.length);
        for (int y = 0; y < numbers.length; y++) {
            for (int x = 0; x < numbers[y].length; x++) {
                ms.placeValue(x, y, numbers[y][x]);
            }
        }

        return ms;
    }
}
