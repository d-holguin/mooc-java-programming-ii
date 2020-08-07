
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("09-07")
public class ListAsAMethodParameterTest {

    @Test
    public void hasMethodReturnSize() {
        Reflex.reflect(mainProgram.class).staticMethod("returnSize").returning(int.class).taking(List.class).requireExists();
    }

    @Test
    public void methodReturnsTheSizeOfList() throws Throwable {

        Random randomNumber = new Random();
        for (int i = 0; i < 10; i++) {
            int listSize = randomNumber.nextInt(10);
            List<String> list = null;

            if (randomNumber.nextBoolean()) {
                list = new ArrayList<>();
            } else {
                list = new LinkedList<>();
            }

            for (int j = 0; j < listSize; j++) {
                list.add(UUID.randomUUID().toString());
            }

            int values = (int) Reflex.reflect(mainProgram.class).staticMethod("returnSize").returning(int.class).taking(List.class).invoke(list);
            assertEquals("When the method returnSize() is given list size " + listSize + ", it should return " + listSize + ". Program returned:" + values, listSize, values);
        }

    }

}
