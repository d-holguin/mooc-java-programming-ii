
import java.util.Arrays;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("09-06.1")
public class Part1test {

    static final String bookName = "Book";
    static final String cdName = "CD";
    static final double eps = 0.00000001;
    Class tallc;
    Reflex.ClassRef<Object> klass;

    @Before
    public void getPackable() {
        try {
            tallc = ReflectionUtils.findClass("Packable");
        } catch (AssertionError e) {
            fail("Add interface Packable!");
        }

        String klassName = "Packable";
        klass = Reflex.reflect(klassName);

        assertTrue("Declare interface " + klassName + " as \npublic interface " + klassName + " {...\n}", klass.isPublic());

    }

    @Test
    public void packableIsCorrect() {

        assertTrue("Packablen must be interface class!", tallc.isInterface());

        Method ms[] = tallc.getDeclaredMethods();

        assertEquals("interface Packable should define one method!", 1, ms.length);

        assertEquals("interface Packable should have method double weight()",
                "public abstract double Packable.weight()",
                ms[0].toString());
    }

    public void implementsPackable(String name) {
        String klassName = name;
        klass = Reflex.reflect(klassName);
        assertTrue("Class " + klassName + " must be public, defined\npublic class " + klassName + " {...\n}", klass.isPublic());

        Class kl = null;
        try {
            kl = ReflectionUtils.findClass(name);
        } catch (Throwable t) {
            fail("Make sure you have made class: " + name);
        }

        Class is[] = kl.getInterfaces();
        Class[] correct = {tallc};

        assertTrue("Make sure that class " + name + " implements (only!) interface Packable",
                Arrays.equals(is, correct));

    }

    @Test
    public void bookImplementsPackable() {
        implementsPackable(bookName);
    }

    @Test
    public void bookWorks() throws Throwable {
        String klassName = "Book";
        klass = Reflex.reflect(klassName);

        Reflex.MethodRef3<Object, Object, String, String, Double> ctor = klass.constructor().
                taking(String.class, String.class, double.class).withNiceError();
        assertTrue("Declare in class " + klassName + " public constructor: public "
                + klassName + "(String author, String name, double weight)", ctor.isPublic());
        String errormessage = "error caused by code new Book(\"Big bad Wolf\", \"Tasty piglet recipes\", 9000.0);";
        Object classObject = ctor.withNiceError(errormessage).invoke("Big bad Wolf", "Tasty piglet recipes", 9000.0);

        String methodToTest = "weight";

        assertTrue("Define in class " + klassName + " method public double " + methodToTest + "() ",
                klass.method(classObject, methodToTest)
                .returning(double.class).takingNoParams().isPublic());

        errormessage = "\nError caused by new Book book = Book(\"Big bad Wolf\", \"Tasty piglet recipes\", 9000.0); "
                + "book.weight();";

        double p = klass.method(classObject, methodToTest)
                .returning(double.class).takingNoParams().withNiceError(errormessage).invoke();

        assertEquals(" new Book book = Book(\"Big bad Wolf\", \"Tasty piglet recipes\", 9000.0); "
                + "book.weight();", 9000., p, 0.01);

        assertFalse("Define in class Book toString-method", classObject.toString().contains("@"));

        assertEquals("toString does not return the correct string", "Big bad Wolf: Tasty piglet recipes", classObject.toString());

    }

    @Test
    public void cdImplementsPackable() {
        implementsPackable(cdName);
    }

    @Test
    public void cdWorks() throws Throwable {
        String klassName = "CD";
        klass = Reflex.reflect(klassName);

        Reflex.MethodRef3<Object, Object, String, String, Integer> ctor = klass.constructor().
                taking(String.class, String.class, int.class).withNiceError();
        assertTrue("Declare in class " + klassName + " public constructor: public "
                + klassName + "(String artist, String name, int publicationyear)", ctor.isPublic());
        String errormessage = "error caused by code new CD(\"Big bad Wolf\", \"piglet-rock\", 1830);";

        Object classObject = ctor.withNiceError(errormessage).invoke("Big bad Wolf", "piglet-rock", 1830);

        String methodToTest = "weight";

        assertTrue("Define in class " + klassName + " method public double " + methodToTest + "() ",
                klass.method(classObject, methodToTest)
                .returning(double.class).takingNoParams().isPublic());

        errormessage = "\nError caused by CD cd = new CD(\"Big bad Wolf\", \"piglet-rock\", 1830); "
                + "cd.weight();";

        double p = klass.method(classObject, methodToTest)
                .returning(double.class).takingNoParams().withNiceError(errormessage).invoke();

        assertEquals("CD cd = new CD(\"Big bad Wolf\", \"piglet-rock\", 1830); "
                + "cd.weight();", .1, p,  0.001);

        assertFalse("Define in class CD  toString-method", classObject.toString().contains("@"));

        assertEquals("toString does not return the correct string", "Big bad Wolf: piglet-rock (1830)", classObject.toString());


    }
}
