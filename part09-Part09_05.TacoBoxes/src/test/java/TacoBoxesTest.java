
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodRef0;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

public class TacoBoxesTest {

    @Points("09-05.1")
    @Test
    public void testTripleTacoBox() throws Throwable {
        testTacoBox("TripleTacoBox", 3, false);
    }

    @Points("09-05.2")
    @Test
    public void testCustomTacoBox() throws Throwable {
        testTacoBox("CustomTacoBox", 5, true);
        testTacoBox("CustomTacoBox", 10, true);
        testTacoBox("CustomTacoBox", 100, true);
    }

    private <T> void testTacoBox(String klass, int startingTacos, boolean noOfTacosForConstructor) throws Throwable {
        ClassRef<T> classRef;

        classRef = (ClassRef<T>) Reflex.reflect(klass);
        assertTrue("Class " + klass + " must be public, i.e, it must be defined with \npublic class " + klass + " {...\n}", classRef.isPublic());

        String v = "";

        T tBox;
        if (noOfTacosForConstructor) {
            v = "The " + klass + " needs a constructor public " + klass + "(int tacos)";
            assertTrue(v, classRef.constructor().taking(int.class).isPublic());
            v = "Test with: " + klass + " v = new " + klass + "(" + startingTacos + "); ";
            tBox = classRef.constructor().taking(int.class).withNiceError(v).invoke(startingTacos);
        } else {
            v = "The " + klass + " needs a constructor public " + klass + "()";
            assertTrue(v, classRef.constructor().takingNoParams().isPublic());
            v = "Test with: " + klass + " v = new " + klass + "(); ";
            tBox = classRef.constructor().takingNoParams().withNiceError(v).invoke();
        }

        if (!TacoBox.class.isAssignableFrom(
                classRef.getReferencedClass())) {
            Assert.fail("Class " + klass + " does not implement the TacoBox interface");
            return;
        }

        MethodRef0<T, Integer> tacosRemainingMethod;
        tacosRemainingMethod = classRef.method(tBox, "tacosRemaining").
                returning(int.class).takingNoParams();

        assertTrue("Class " + klass + " doesn't contain method: public int tacosRemaining()",
                tacosRemainingMethod.isPublic());

        assertEquals(v + "v.tacosRemaining(); ", startingTacos, (int) tacosRemainingMethod.withNiceError(v).invoke());

        MethodRef0<T, Void> eatMethod;
        eatMethod = classRef.method(tBox, "eat").
                returningVoid().takingNoParams();

        assertTrue("Class " + klass + " doesn't contain the method: public void eat()",
                eatMethod.isPublic());

        v += "v.eat(); ";

        eatMethod.withNiceError(v).invoke();

        v += "v.tacosRemaining(); ";

        assertEquals(v, startingTacos - 1, (int) tacosRemainingMethod.withNiceError(v).invoke());

        Integer remaining;

        for (int i = 1; i < startingTacos; i++) {
            try {
                remaining = tacosRemainingMethod.invoke();
            } catch (Throwable t) {
                Assert.fail("In the " + klass + " class, calling the method tacosRemaining() "
                        + "caused the exception: " + t.toString());
                return;
            }

            Assert.assertTrue(
                    "In the"+klass + "class, eating " + i + " tacos, should leave " + (startingTacos - i) + " tacos your implementation returned: " + remaining,
                    remaining == (startingTacos - i));

            try {
                eatMethod.invoke();
            } catch (Throwable t) {
                Assert.fail("In the " + klass + " class, calling the method eat() "
                        + "caused the exception: " + t.toString());
                return;
            }
        }

        try {
            remaining = tacosRemainingMethod.invoke();
        } catch (Throwable t) {
            Assert.fail("In the " + klass + " class, calling the method tacosRemaining() "
                    + "caused the exception: " + t.toString());
            return;
        }

        Assert.assertTrue(
                "In the "+klass + "-class, after eating everything, tacosRemaining should be zero. Your implementation returned: " + remaining,
                remaining == 0);

        try {
            eatMethod.invoke();
        } catch (Throwable t) {
            Assert.fail("In the " + klass + " class, calling the method eat() "
                    + "caused the exception: " + t.toString());
            return;
        }

        try {
            remaining = tacosRemainingMethod.invoke();
        } catch (Throwable t) {
            Assert.fail("In the " + klass + " class, calling the method tacosRemaining() "
                    + "caused the exception: " + t.toString());
            return;
        }

        Assert.assertTrue(
                "In the "+klass + "-class number of tacos remaining should stay at zero after eating all the tacos, even if the eat()-method is called. Your implementation returned: " + remaining,
                remaining == 0);
    }
}
