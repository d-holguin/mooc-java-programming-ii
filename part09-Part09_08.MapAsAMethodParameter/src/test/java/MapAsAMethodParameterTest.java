
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("09-08")
public class MapAsAMethodParameterTest {

    @Test
    public void hasMethodReturnSize() {
        Reflex.reflect(MainProgram.class).staticMethod("returnSize").returning(int.class).taking(Map.class).requireExists();
    }

    @Test
    public void methodReturnsTheSizeOfAMap() throws Throwable {
        Random randomNumber = new Random();
        for (int i = 0; i < 10; i++) {
            int mapSize = randomNumber.nextInt(10);
            Map<String, String> map = null;

            if (randomNumber.nextBoolean()) {
                map = new HashMap<>();
            } else {
                map = new TreeMap<>();
            }

            for (int j = 0; j < mapSize; j++) {
                map.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
            }

            int values = (int) Reflex.reflect(MainProgram.class).staticMethod("returnSize").returning(int.class).taking(Map.class).invoke(map);
            assertEquals("When method returnSize is given a map sized " + mapSize + " it should return " + mapSize + ". Now the return value was " + values, mapSize, values);
        }
    }
}
