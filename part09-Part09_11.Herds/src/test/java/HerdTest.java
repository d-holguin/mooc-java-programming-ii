

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("09-11.2")
public class HerdTest {

    private String herdKlassName = "Herd";
    private Reflex.ClassRef<Object> herdKlassRef;
    
    private String organismKlassName = "Organism";
    private Reflex.ClassRef<Object> organismKlassRef;
    
    @Before
    public void setUp() {
        herdKlassRef = Reflex.reflect(herdKlassName);
        organismKlassRef = Reflex.reflect(organismKlassName);
    }

    @Test
    public void classExists() {
        assertTrue("The class " + s(herdKlassName) + " should be public, so it must be defined as\n"
                + "public class " + herdKlassName + " {...\n}", herdKlassRef.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanityCheck(herdKlassName, 1, "the object variables taht remember the members of the herd");
    }

    @Test
    public void Constructor() throws Throwable {

        Reflex.MethodRef0<Object, Object> ctor = herdKlassRef.constructor().takingNoParams().withNiceError();
        assertTrue("Define for the class " + s(herdKlassName) + " a public constructor: \n"
                + "public " + s(herdKlassName) + "()", ctor.isPublic());
        String e = "the error was caused by the code new Herd();\n";
        ctor.withNiceError(e).invoke();
    }

    public Movable newOrganism(int t1, int t2) throws Throwable {

        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(organismKlassName);
        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = classRef
                .constructor().taking(int.class, int.class).withNiceError();
        return (Movable) ctor.invoke(t1, t2);
    }

    public Movable newHerd() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = herdKlassRef
                .constructor().takingNoParams().withNiceError();
        return (Movable) ctor.invoke();
    }

    @Test
    public void isMovable() {
        Class clazz = ReflectionUtils.findClass(herdKlassName);
        boolean implementsInterface = false;
        Class itrface = Movable.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(itrface)) {
                implementsInterface = true;
            }
        }

        if (!implementsInterface) {
            fail("Are you sure class Herd implements the interface Movable?");
        }
    }

    @Test
    public void methodMoveExists() throws Throwable {
        Movable e = newHerd();

        assertTrue("The class Herd must have the method public void move(int dx, int dy)",
                herdKlassRef.method(e, "move").returningVoid().taking(int.class, int.class).isPublic());

        String v = "the error was caused by the code\n"
                + "Herd e = new Herd();\n"
                + "e.move(1,1);\n";

        herdKlassRef.method(e, "move").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(1, 1);
    }

    private void move(Object e, int dx, int dy, String v) throws Throwable {
        herdKlassRef.method(e, "move").returningVoid().taking(int.class, int.class).withNiceError(v).invoke(dx, dy);
    }

    @Test
    public void methodAddToHerdExists() throws Throwable {
        Movable e = newHerd();

        assertTrue("The class Herd must have the method public void addToHerd(Movable movable)",
                herdKlassRef.method(e, "addToHerd").returningVoid().taking(Movable.class).isPublic());

        String v = "the error was caused by the code\n"
                + "Herd e = new Herd();\n"
                + "e.addToHerd(new Organism(1,1));\n";

        herdKlassRef.method(e, "addToHerd")
                .returningVoid()
                .taking(Movable.class).withNiceError(v).invoke(newOrganism(1, 1));
    }

    private void addToHerd(Object e, Movable s, String v) throws Throwable {
        herdKlassRef.method(e, "addToHerd").returningVoid().taking(Movable.class).withNiceError(v).invoke(s);
    }

    @Test
    public void toStringDefined() throws Throwable {
        Movable h = newHerd();
        assertFalse("define for the class Herd ta toString-method that is described in the exercise", h.toString().contains("@"));
        String e = "Herd h = new Herd();\n"
                + "h.addToHerd(new Organism(1,9));\n"
                + "h.addToHerd(new Organism(4,2));\n"
                + "System.out.println(h.toString());\n";

        Movable e1 = newOrganism(1, 9);
        Movable e2 = newOrganism(4, 2);

        addToHerd(h, e1, e);
        addToHerd(h, e2, e);

        String str = h.toString();

        assertTrue("The string representation should be two rows with the code \n" + e + ""
                + "the string representation was\n" + str, str.split("\n").length > 1);
        assertTrue("The string representation should tulostua \"" + e1 + "\"\n" + e + ""
                + "the string representation was\n" + str, str.contains(e1.toString()));
        assertTrue("The string representation should tulostua \"" + e2 + "\"\n" + e + ""
                + "the string representation was\n" + str, str.contains(e1.toString()));
    }

    @Test
    public void herdOfOneMovesCorrectly1() throws Throwable {
        String e = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.move(1,0);\n"
                + "System.out.println(herd.toString());";

        Movable h = newHerd();
        addToHerd(h, newOrganism(5, 10), e);
        move(h, 1, 0, e);
        assertTrue("the position of the only member of the herd should be"
                + "x: 6; y: 10 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(), h.toString().contains("x: 6; y: 10"));
    }

    @Test
    public void herdOfOneMovesCorrectly2() throws Throwable {
        String e = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.move(0,1);\n"
                + "System.out.println(herd.toString());";

        Movable h = newHerd();
        addToHerd(h, newOrganism(5, 10), e);
        move(h, 0, 1, e);
        assertTrue("the position of the only member of the herd should be"
                + "x: 5; y: 11 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(), h.toString().contains("x: 5; y: 11"));
    }

    @Test
    public void herdOfOneMovesCorrectly3() throws Throwable {
        String e = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.move(0,1);\n"
                + "herd.move(3,5);\n"
                + "herd.move(-20,2);\n"
                + "herd.move(9,3);\n"
                + "System.out.println(herd.toString());";

        Movable h = newHerd();
        addToHerd(h, newOrganism(5, 10), e);
        move(h, 0, 1, e);
        move(h, 3, 5, e);
        move(h, -20, 2, e);
        move(h, 9, 3, e);
        assertTrue("the position of the only member of the herd should be"
                + "x: -3; y: 21 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(),
                h.toString().contains("x: -3; y: 21"));
    }

    @Test
    public void herdOfTwoMovesCorrectly1() throws Throwable {
        String v = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.addToHerd(new Organism(2,8));\n"
                + "herd.move(1,0);\n"
                + "System.out.println(herd.toString());";

        Movable l = newHerd();
        addToHerd(l, newOrganism(5, 10), v);
        addToHerd(l, newOrganism(2, 8), v);
        move(l, 1, 0, v);
        assertTrue("the positions of the herd members should be"
                + "x: 6; y: 10  ja x: 3; y: 8 when the following code is executed\n" + v + "\n"
                + "\naccording to your code the new position is\n" + l.toString(), l.toString().contains("x: 6; y: 10"));
        assertTrue("the positions of the herd members should be"
                + "x: 6; y: 10  ja x: 3; y: 8 when the following code is executed\n" + v + "\n"
                + "\naccording to your code the new position is\n" + l.toString(), l.toString().contains("x: 3; y: 8"));
    }

    @Test
    public void herdOfTwoMovesCorrectly2() throws Throwable {
        String e = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.addToHerd(new Organism(2,8));\n"
                + "herd.move(0,1);\n"
                + "System.out.println(herd.toString());";

        Movable h = newHerd();
        addToHerd(h, newOrganism(5, 10), e);
        addToHerd(h, newOrganism(2, 8), e);
        move(h, 0, 1, e);
        assertTrue("the positions of the herd members should be"
                + "x: 5; y: 11  ja x: 2; y: 9 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(), h.toString().contains("x: 5; y: 11"));
        assertTrue("the positions of the herd members should be"
                + "x: 5; y: 11  ja x: 2; y: 9 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(), h.toString().contains("x: 2; y: 9"));
    }

    @Test
    public void herdOfTwoMovesCorrectly3() throws Throwable {
        String e = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.addToHerd(new Organism(2,8));\n"
                + "herd.move(0,1);\n"
                + "herd.move(8,-3);\n"
                + "herd.move(11,1);\n"
                + "System.out.println(herd.toString());";

        Movable h = newHerd();
        addToHerd(h, newOrganism(5, 10), e);
        addToHerd(h, newOrganism(2, 8), e);
        move(h, 0, 1, e);
        move(h, 8, -3, e);
        move(h, 11, 1, e);
        assertTrue("the positions of the herd members should be"
                + "x: 24; y: 9  ja x: 21; y: 7 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(),
                h.toString().contains("x: 24; y: 9"));
        assertTrue("the positions of the herd members should be"
                + "x: 24; y: 9  ja x: 21; y: 7 when the following code is executed\n" + e + "\n"
                + "\naccording to your code the new position is\n" + h.toString(),
                h.toString().contains("x: 21; y: 7"));
    }

    @Test
    public void bigHerdMovesCorrectly() throws Throwable {
        String v = ""
                + "Herd herd = new Herd();\n"
                + "herd.addToHerd(new Organism(5,10));\n"
                + "herd.addToHerd(new Organism(2,8));\n"
                + "herd.addToHerd(new Organism(7,-4));\n"
                + "herd.addToHerd(new Organism(99,-200));\n"
                + "herd.move(5,-2);\n"
                + "herd.move(1,4);\n"
                + "System.out.println(herd.toString());";

        Movable h = newHerd();
        addToHerd(h, newOrganism(5, 10), v);
        addToHerd(h, newOrganism(2, 8), v);
        addToHerd(h, newOrganism(7, -4), v);
        addToHerd(h, newOrganism(99, -200), v);
        move(h, 5, -2, v);
        move(h, 1, 4, v);
        assertTrue("the herd does not move correctly when the following code is executed\n" + v + "\n"
                + "the string representation of the end was\n" + h.toString(), h.toString().contains("x: 11; y: 12"));
        assertTrue("the herd does not move correctly when the following code is executed\n" + v + "\n"
                + "the string representation of the end was\n" + h.toString(), h.toString().contains("x: 8; y: 10"));
        assertTrue("the herd does not move correctly when the following code is executed\n" + v + "\n"
                + "the string representation of the end was\n" + h.toString(), h.toString().contains("x: 13; y: -2"));
        assertTrue("the herd does not move correctly when the following code is executed\n" + v + "\n"
                + "the string representation of the end was\n" + h.toString(), h.toString().contains("x: 105; y: -198"));


    }

    /*
     *
     */
    private void sanityCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove from the class " + s(klassName) + " the variable " + field(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all object variables should be privateprivate, but the class " + s(klassName) + " had: " + field(field.toString(), klassName), field.toString().contains("private"));
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
