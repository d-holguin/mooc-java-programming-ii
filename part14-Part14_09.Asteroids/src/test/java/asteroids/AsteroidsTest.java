package asteroids;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class AsteroidsTest {

    @Test
    @Points("14-09.1")
    public void part1Done() {
        assertTrue(AsteroidsApplication.partsCompleted() >= 1);
    }

    @Test
    @Points("14-09.2")
    public void part2Done() {
        assertTrue(AsteroidsApplication.partsCompleted() >= 2);
    }

    @Test
    @Points("14-09.3")
    public void part3Done() {
        assertTrue(AsteroidsApplication.partsCompleted() >= 3);
    }

    @Test
    @Points("14-09.4")
    public void part4Done() {
        assertTrue(AsteroidsApplication.partsCompleted() >= 4);
    }
}
