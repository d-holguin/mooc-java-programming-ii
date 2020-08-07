
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@Points("11-09")
public class ThreePackagesTest {

    @Test
    public void classesExist() {
        classExists("a.A");
        classExists("b.B");
        classExists("c.C");
    }

    public void classExists(String wholeClass) {
        String Package = "";
        String className = "";
        if (wholeClass.contains(".")) {
            Package = wholeClass.substring(0, wholeClass.lastIndexOf("."));
            className = wholeClass.substring(wholeClass.lastIndexOf(".") + 1);
        }

        if (Package.isEmpty()) {
            assertTrue("Class " + wholeClass + " could not be found. Please ensure that it is defined as: public class " + wholeClass + " { ...", Reflex.reflect(wholeClass).isPublic());
        } else {
            assertTrue("Class " + className + " could not be found from the package " + Package + ". Please ensure that it is in the package " + Package + " , and that it is defined as: \npackage " + Package + ";\n public class " + wholeClass + "  ...", Reflex.reflect(wholeClass).isPublic());
        }

    }
}
