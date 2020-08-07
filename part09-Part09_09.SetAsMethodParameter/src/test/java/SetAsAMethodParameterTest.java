
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("09-09")
public class SetAsAMethodParameterTest {

    @Test
    public void methodReturnSizeExits() {
        Reflex.reflect(Main.class).staticMethod("returnSize").returning(int.class).taking(Set.class).requireExists();
    }

    @Test
    public void methodReturnsTheSizeOfAGivenSet() throws Throwable {
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int size = rnd.nextInt(10);
            Set<String> set = null;

            if (rnd.nextBoolean()) {
                set = new HashSet<>();
            } else {
                set = new TreeSet<>();
            }

            for (int j = 0; j < size; j++) {
                set.add(UUID.randomUUID().toString());
            }

            int sizeReturned = (int) Reflex.reflect(Main.class).staticMethod("returnSize").returning(int.class).taking(Set.class).invoke(set);
            assertEquals("When the method returnSize is given set with " + size + " elements, it should return " + size + ". Instead it returned " + sizeReturned, size, sizeReturned);
        }

    }

}
