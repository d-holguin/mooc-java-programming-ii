

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("09-11.1")
public class OrganismTest {

    private String klassName = "Organism";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setUp() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    public void classExists() {
        assertTrue("The class " + s(klassName) + " should be public, so it has to be defined as\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanityCheck(klassName, 2, "object variables to remember the x and y coordinates of the position");
    }

    @Test
    public void Constructor() throws Throwable {

        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = classRef.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Define for the class " + s(klassName) + " a public constructor: \n"
                + "public " + s(klassName) + "(int x, int y)", ctor.isPublic());
        String v = "the error was caused by the code new Organism(5,10);\n";
        ctor.withNiceError(v).invoke(5, 10);
    }

    public Movable newOrganism(int t1, int t2) throws Throwable {
        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = classRef
                .constructor().taking(int.class, int.class).withNiceError();
        return (Movable) ctor.invoke(t1, t2);
    }

    @Test
    public void isMovable() {
        Class clazz = ReflectionUtils.findClass(klassName);
        boolean implementsInterface = false;
        Class itrface = Movable.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(itrface)) {
                implementsInterface = true;
            }
        }

        if (!implementsInterface) {
            fail("Are you sure the class Organism implements the Movable interface?");
        }
    }

    @Test
    public void hasMethodMove() throws Throwable {
        Movable o = newOrganism(5, 10);

        assertTrue("Luokalla Organism must have the method public void move(int dx, int dy)",
                classRef.method(o, "move").returningVoid().taking(int.class, int.class).isPublic());

        String e = "the error was caused by the code\n"
                + "Organism r = new Organism(5,10);\n"
                + "o.move(1,1);\n";

        classRef.method(o, "move").returningVoid().taking(int.class, int.class).withNiceError(e).invoke(1, 1);
    }

    private void move(Object e, int dx, int dy, String v) throws Throwable {
        classRef.method(e, "move").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(dx, dy);
    }

    @Test
    public void toStringIsDefined() throws Throwable {
        Movable o = newOrganism(5, 10);
        assertFalse("define for the class Organism a toString that matches the instructions", o.toString().contains("@"));
        String e = "Organism o = new Organism(5,10);\n"
                + "o.toString();\n";
        assertEquals(e, "x: 5; y: 10", o.toString());

        o = newOrganism(1, 9);
        e = "Organism e = new Organism(1,9);\n"
                + "e.toString();\n";
        assertEquals(e, "x: 1; y: 9", o.toString());
    }

    @Test
    public void movesCorrectly1() throws Throwable {
        String e = ""
                + "Organism o = new Organism(5,10);\n"
                + "o.move(1,0);\n"
                + "o.toString()";

        Movable o = newOrganism(5,10);
        move(o, 1, 0, e);
        assertEquals(e, "x: 6; y: 10", o.toString());
    }

    @Test
    public void movesCorrectly2() throws Throwable {
        String e = ""
                + "Organism o = new Organism(5,10);\n"
                + "o.move(0,1);\n"
                + "o.toString()";

        Movable o = newOrganism(5,10);
        move(o, 0, 1, e);
        assertEquals(e, "x: 5; y: 11", o.toString());
    }

    @Test
    public void movesCorrectly3() throws Throwable {
        String e = ""
                + "Organism o = new Organism(5,10);\n"
                + "o.move(2,-8);\n"
                + "o.toString()";

        Movable o = newOrganism(5,10);
        move(o, 2, -8, e);
        assertEquals(e, "x: 7; y: 2", o.toString());
    }

    @Test
    public void movesCorrectly4() throws Throwable {
        String e = ""
                + "Organism o = new Organism(0,0);\n"
                + "o.move(2,5);\n"
                + "o.move(1,4);\n"
                + "o.move(5,-11);\n"
                + "o.toString()";

        Movable o = newOrganism(0,0);
        move(o, 2, 5, e);
        move(o, 1, 4, e);
        move(o, 5, -11, e);
        assertEquals(e, "x: 8; y: -2", o.toString());
    }

    private void sanityCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove from the class " + s(klassName) + " the variable " + field(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables should be private, but the class " + s(klassName) + " contained: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + s(klassName) + " only needs " + m + ", remove the extra variables", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
