

import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class B_HandTest<_Hand> {

    String className = "Hand";
    String fullName = className;
    Reflex.ClassRef<Object> klass;
    Class handClass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(fullName);

        try {
            handClass = ReflectionUtils.findClass(fullName);
        } catch (Throwable t) {
            fail("Make sure you have a class \"" + className + "\".");
        }

        assertTrue("Class " + className + " must be public, so it needs to be defined as\npublic class " + className + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("10-17.2")
    public void noExtraVariables() {
        sanitizingCheck(fullName, 1, "variable that stores the cards");
    }

    @Test
    @Points("10-17.2")
    public void isConstructor() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Define a public constructor to class " + className + ": public " + className + "()", ctor.isPublic());
        String v = "error created by code new Hand();";
        ctor.withNiceError(v).invoke();
    }

    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    @Points("10-17.2")
    public void methodAdd() throws Throwable {
        String method = "add";

        Object obj = create();

        assertTrue("in " + className + ", add a method public void " + method + "(Card c)",
                klass.method(obj, method)
                        .returningVoid().taking(Card.class).isPublic());

        String v = "Hand hand = new Hand();\n"
                + "Card card = new Card(12,Suit.HEART );  \n"
                + "hand.add(card);";

        klass.method(obj, method)
                .returningVoid().taking(Card.class).withNiceError("error was caused by code\n" + v).
                invoke(new Card(12, Suit.HEART));
    }

    private void add(Object obj, Card c) throws Throwable {
        klass.method(obj, "add")
                .returningVoid()
                .taking(Card.class)
                .invoke(c);
    }

    @Test
    @Points("10-17.2")
    public void methodPrint() throws Throwable {
        String metodi = "print";

        Object obj = create();

        assertTrue("in class " + className + ", add a method public void " + metodi + "()",
                klass.method(obj, metodi)
                        .returningVoid().takingNoParams().isPublic());

        String v = "Hand hand = new Hand();\n"
                + "hand.print();";

        klass.method(obj, metodi)
                .returningVoid().takingNoParams().withNiceError("error was caused by code\n" + v).
                invoke();
    }

    @Test
    @Points("10-17.2")
    public void printingWorks() throws Throwable {
        MockInOut io = new MockInOut("");

        Object obj = create();

        Card c = new Card(12, Suit.HEART);

        add(obj, c);

        String v = "Hand hand = new Hand();\n"
                + "Card card = new Card(12,Suit.HEART); \n"
                + "hand.add(card);\n"
                + "hand.print()\n";

        klass.method(obj, "print")
                .returningVoid().takingNoParams().withNiceError("error was caused by code\n" + v).
                invoke();

        String out = io.getOutput();

        assertTrue("With code \n" + v + "the output was\n" + out, out.contains(c.toString()));

        io = new MockInOut("");
        Card c2 = new Card(2, Suit.SPADE);

        add(obj, c2);
        Card c3 = new Card(14, Suit.DIAMOND);

        add(obj, c3);

        v = "Hand hand = new Hand();\n"
                + "hand.add( new Card(12,Suit.HEART) ); \n"
                + "hand.add( new Card(2,Suit.SPADE) ); \n"
                + "hand.add( new Card(14,Suit.DIAMOND) ); \n"
                + "hand.print()\n";

        klass.method(obj, "print")
                .returningVoid().takingNoParams().withNiceError("error was caused by code\n" + v).
                invoke();

        out = io.getOutput();

        assertTrue("With code \n" + v + "the output should be 3 rows, now the output was \n" + out, out.split("\n").length > 2);

        assertTrue("With code \n" + v + "output was\n" + out, out.contains(c2.toString()));
        assertTrue("With code \n" + v + "output was\n" + out, out.contains(c3.toString()));
        assertTrue("With code \n" + v + "output was\n" + out, out.contains(c.toString()));

    }

    /*
     *
     */
    @Test
    @Points("10-17.3")
    public void methodSort() throws Throwable {
        String method = "sort";

        Object obj = create();

        assertTrue("in class " + className + ", add method public void " + method + "()",
                klass.method(obj, method)
                        .returningVoid().takingNoParams().isPublic());

        String v = "Hand hand = new Hand();\n"
                + "hand.print();";

        klass.method(obj, method)
                .returningVoid().takingNoParams().withNiceError("error was caused by code\n" + v).
                invoke();
    }

    @Test
    @Points("10-17.3")
    public void sortedPrintingWorks() throws Throwable {
        MockInOut io = new MockInOut("");

        Object obj = create();

        Card c = new Card(12, Suit.HEART);
        Card c3 = new Card(14, Suit.DIAMOND);
        Card c2 = new Card(2, Suit.SPADE);
        Card c4 = new Card(2, Suit.CLUB);

        add(obj, c);
        add(obj, c3);
        add(obj, c2);
        add(obj, c4);

        String v = "Hand hand = new Hand();\n"
                + "hand.add( new Card(12,Suit.HEART) ); \n"
                + "hand.add( new Card(14,Suit.DIAMOND) ); \n"
                + "hand.add( new Card(2,Suit.SPADE) ); \n"
                + "hand.add( new Card(2,Suit.CLUB) ); \n"
                + "hand.sort();\n"
                + "hand.print()\n";

        klass.method(obj, "sort")
                .returningVoid().takingNoParams().withNiceError("error was caused by code\n" + v).
                invoke();

        klass.method(obj, "print")
                .returningVoid().takingNoParams().withNiceError("error was caused by code\n" + v).
                invoke();

        String out = io.getOutput();

        assertTrue("With code \n" + v + "output should be 4 rows, now it was\n" + out, out.split("\n").length > 3);

        int j1 = out.indexOf(c4.toString());
        int j2 = out.indexOf(c2.toString());
        int j3 = out.indexOf(c.toString());
        int j4 = out.indexOf(c3.toString());

        assertTrue("Every card was not printed with the code \nprint was oli\n" + out, j1 > -1 && j2 > -1 && j3 > -1 && j4 > -1);

        assertTrue("With code \n" + v + "second card should be " + c2 + "\nprint was\n" + out, j2 < j3 && j2 < j4);
        assertTrue("With code \n" + v + "third card should be " + c + "\nprint was\n" + out, j3 < j4);
        assertTrue("With code \n" + v + "first card should be " + c4 + "\nprint was\n" + out, j1 < j2 && j1 < j3 && j1 < j4);
    }

    /*
     *  käsi comparable
     */
    @Points("10-17.4")
    @Test
    public void onImplementComparableHand() {
        Class kl;
        try {
            kl = ReflectionUtils.findClass(fullName);
            Class is[] = kl.getInterfaces();
            Class correct[] = {java.lang.Comparable.class};
            for (int i = 0; i < is.length; i++) {
            }
            assertTrue("Make sure the class " + className + " has only one interface, and that that interface is Comparable",
                    Arrays.equals(is, correct));

        } catch (Throwable t) {
            fail("Make sure the class " + className + " has interface \"Comparable<Hand>\"");
        }
    }

    @Points("10-17.4")
    @Test
    public void onCompareToMethod() throws Throwable {
        String method = "compareTo";

        Reflex.ClassRef<_Hand> _HandRef = Reflex.reflect(fullName);

        _Hand c1 = _HandRef.constructor().takingNoParams().invoke();
        _Hand c2 = _HandRef.constructor().takingNoParams().invoke();

        assertTrue("in class " + className + " add a method public int " + method + "(Hand comparable)",
                klass.method(c1, method)
                        .returning(int.class).taking(_HandRef.cls()).isPublic());

        String v = "\nError was caused by code:\n"
                + "Hand hand1 = new Hand();\n"
                + "Hand hand2 = new Hand();\n"
                + "hand1.compareTo(hand2);";

        klass.method(c1, method)
                .returning(int.class).taking(_HandRef.cls()).withNiceError(v).invoke(c2);

    }

    @Points("10-17.4")
    @Test
    public void comparisonWorks1() throws Throwable {
        Reflex.ClassRef<_Hand> _HandRef = Reflex.reflect(fullName);

        _Hand hand1 = _HandRef.constructor().takingNoParams().invoke();
        _Hand hand2 = _HandRef.constructor().takingNoParams().invoke();

        Card k = new Card(12, Suit.HEART);
        Card c2 = new Card(14, Suit.DIAMOND);

        add(hand1, k);
        add(hand1, c2);

        Card c3 = new Card(3, Suit.SPADE);

        add(hand2, c3);

        String v = "Hand hand1 = new Hand();\n"
                + "hand1.add( new Card(12,Suit.HEART) ); \n"
                + "hand1.add( new Card(14,Suit.DIAMOND) ); \n"
                + "Hand hand2 = new Hand();\n"
                + "hand2.add( new Card(3,Suit.HEART) ); \n"
                + "hand1.compareTo(hand2)\n"
                + "result was: ";

        int vast = klass.method(hand1, "compareTo")
                .returning(int.class).taking(_HandRef.cls()).withNiceError(v).invoke(hand2);

        String od = "The result should have been positive with code:\n";

        assertTrue(od + v + vast, vast > 0);
    }

    @Points("10-17.4")
    @Test
    public void comparisonWorks2() throws Throwable {
        Reflex.ClassRef<_Hand> _HandRef = Reflex.reflect(fullName);

        _Hand hand1 = _HandRef.constructor().takingNoParams().invoke();
        _Hand hand2 = _HandRef.constructor().takingNoParams().invoke();

        Card c = new Card(12, Suit.HEART);
        Card c2 = new Card(14, Suit.DIAMOND);

        add(hand1, c);
        add(hand1, c2);

        Card c3 = new Card(3, Suit.SPADE);

        add(hand2, c3);

        String v = "Hand hand1 = new Hand();\n"
                + "hand1.add( new Card(12,Suit.HEART) ); \n"
                + "hand1.add( new Card(14,Suit.DIAMOND) ); \n"
                + "Hand hand2 = new Hand();\n"
                + "hand2.add( new Card(3,Suit.HEART) ); \n"
                + "hand2.compareTo(hand1)\n"
                + "result was: ";

        int vast = klass.method(hand2, "compareTo")
                .returning(int.class).taking(_HandRef.cls()).withNiceError(v).invoke(hand1);

        String od = "The result should have been negative with code:\n";

        assertTrue(od + v + vast, vast < 0);
    }

    @Points("10-17.4")
    @Test
    public void comparisonWorks3() throws Throwable {
        Reflex.ClassRef<_Hand> _HandRef = Reflex.reflect(fullName);

        _Hand hand1 = _HandRef.constructor().takingNoParams().invoke();
        _Hand hand2 = _HandRef.constructor().takingNoParams().invoke();

        Card k = new Card(12, Suit.HEART);

        add(hand1, k);

        Card c3 = new Card(3, Suit.SPADE);
        Card c2 = new Card(9, Suit.SPADE);

        add(hand2, c3);
        add(hand2, c2);

        String v = "Hand hand1 = new Hand();\n"
                + "hand1.add( new Card(12,Suit.HEART) ); \n"
                + "Hand hand2 = new Hand();\n"
                + "hand2.add( new Card(3,Suit.SPADE) ); \n"
                + "hand2.add( new Card(9,Suit.SPADE) ); \n"
                + "hand2.compareTo(hand1)\n";

        int vast = klass.method(hand2, "compareTo")
                .returning(int.class).taking(_HandRef.cls()).withNiceError(v).invoke(hand1);

        assertEquals(v, 0, vast);
    }

    @Points("10-17.4")
    @Test
    public void comparisonWorks4() throws Throwable {
        Reflex.ClassRef<_Hand> _HandRef = Reflex.reflect(fullName);

        _Hand hand1 = _HandRef.constructor().takingNoParams().invoke();
        _Hand hand2 = _HandRef.constructor().takingNoParams().invoke();

        Card k = new Card(12, Suit.HEART);
        Card c2 = new Card(14, Suit.DIAMOND);

        add(hand1, k);
        add(hand1, c2);

        Card c3 = new Card(3, Suit.SPADE);
        Card c4 = new Card(8, Suit.DIAMOND);
        Card c5 = new Card(7, Suit.CLUB);
        Card c6 = new Card(9, Suit.HEART);

        add(hand2, c3);
        add(hand2, c4);
        add(hand2, c5);
        add(hand2, c6);

        String v = "Hand hand1 = new Hand();\n"
                + "hand1.add( new Card(12,Suit.HEART) ); \n"
                + "hand1.add( new Card(14,Suit.DIAMOND) ); \n"
                + "Hand hand2 = new Hand();\n"
                + "hand2.add( new Card(3,Suit.SPADE) ); \n"
                + "hand2.add( new Card(8,Suit.DIAMOND) ); \n"
                + "hand2.add( new Card(7,Suit.CLUB) ); \n"
                + "hand2.add( new Card(9,Suit.HEART) ); \n"
                + "hand2.compareTo(hand1)\n"
                + "result was: ";

        int vast = klass.method(hand2, "compareTo")
                .returning(int.class).taking(_HandRef.cls()).withNiceError(v).invoke(hand1);

        String od = "The result should have been positive with code:\n";

        assertTrue(od + v + vast, vast > 0);
    }

    @Points("10-17.4")
    @Test
    public void comparisonWorks5() throws Throwable {
        Reflex.ClassRef<_Hand> _HandRef = Reflex.reflect(fullName);

        _Hand hand1 = _HandRef.constructor().takingNoParams().invoke();
        _Hand hand2 = _HandRef.constructor().takingNoParams().invoke();

        Card k = new Card(12, Suit.HEART);
        Card c2 = new Card(14, Suit.DIAMOND);

        add(hand1, k);
        add(hand1, c2);

        Card c3 = new Card(3, Suit.SPADE);
        Card c4 = new Card(8, Suit.DIAMOND);
        Card c5 = new Card(7, Suit.CLUB);
        Card c6 = new Card(9, Suit.HEART);

        add(hand2, c3);
        add(hand2, c4);
        add(hand2, c5);
        add(hand2, c6);

        String v = "Hand hand1 = new Hand();\n"
                + "hand1.add( new Card(12,Suit.HEART) ); \n"
                + "hand1.add( new Card(14,Suit.DIAMOND) ); \n"
                + "Hand hand2 = new Hand();\n"
                + "hand2.add( new Card(3,Suit.SPADE) ); \n"
                + "hand2.add( new Card(8,Suit.DIAMOND) ); \n"
                + "hand2.add( new Card(7,Suit.CLUB) ); \n"
                + "hand2.add( new Card(9,Suit.HEART) ); \n"
                + "hand1.compareTo(hand2)\n"
                + "result was: ";

        int vast = klass.method(hand1, "compareTo")
                .returning(int.class).taking(_HandRef.cls()).withNiceError(v).invoke(hand2);

        String od = "The result should have been negative with code:\n";

        assertTrue(od + v + vast, vast < 0);
    }

    /*
     * komparaattorit
     */
    private void sanitizingCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", in class " + klassName + ", please remove variable " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the variables in the class need to be private, in class " + klassName + " there was a: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("in class " + klassName + ", you only need " + m + ", please remove any extras", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
