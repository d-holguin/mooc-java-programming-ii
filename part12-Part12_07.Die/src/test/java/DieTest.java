
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("12-07")
public class DieTest {

    @Test
    public void dieProvidesAllNumbers() {
        int faces = new Random().nextInt(15) + 2;
        Die d = new Die(faces);

        int number = d.throwDie();
        assertTrue("When we create Die d = new Die(" + faces + "); and call d.throwDie(), the returned value was " + number + " even though the result should be withing the range 1..." + faces, number > 0 && number <= faces);
        int i = 0;
        while (true) {
            int newNumber = d.throwDie();
            if (newNumber != number) {
                break;
            }
            assertTrue("When we create Die d = new Die(" + faces + "); and call d.throwDie(), the returned value was " + number + " even though the result should be withing the range 1..." + faces, number > 0 && number <= faces);

            i++;
            if (i == 20) {
                fail("Your die produces the same number on every throw! Make sure the sample main program works as intended");
            }
        }
        int[] numbers = new int[faces + 1];
        for (int j = 0; j < 1000; j++) {
            number = d.throwDie();
            assertTrue("When we create Die d = new Die(" + faces + "); and call d.throwDie(), palautus oli " + number + " even though the result should be withing the range 1..." + faces, number > 0 && number <= faces);
            numbers[number]++;
        }

        for (int j = 1; j < numbers.length; j++) {
            assertTrue("Is your die working properly? When Die d = new Die(" + faces + "); was created, after a thousand throws the number " + j + " appeared only " + numbers[j] + " times.", numbers[j] > 10);
        }
    }
}
