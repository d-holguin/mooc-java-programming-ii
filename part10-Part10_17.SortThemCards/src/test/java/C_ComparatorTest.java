

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class C_ComparatorTest extends Help {

    String className = "BySuitInValueOrder";
    String fullName =  className;
    Reflex.ClassRef<Object> klass;
    Class kasiClass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(fullName);

        try {
            kasiClass = ReflectionUtils.findClass(fullName);
        } catch (Throwable t) {
            fail("Make sure you have a class \"" + className + "\".");
        }

        assertTrue("Class " + className + " must be public, so it needs to be defined as\npublic class " + className + " {...\n}", klass.isPublic());
    }

    @Points("10-17.5")
    @Test
    public void onImplementComparableHand() {
        String nimi = className;
        Class kl;
        try {
            kl = ReflectionUtils.findClass(fullName);
            Class is[] = kl.getInterfaces();
            Class correct[] = {java.util.Comparator.class};

            assertTrue("Make sure that class " + nimi + " has only one interface, and that that interface is Comparator<Card>",
                    Arrays.equals(is, correct));

        } catch (Throwable t) {
            fail("Make sure class " + nimi + " has interface \"Comparator<Card>\"");
        }
    }

    @Test
    @Points("10-17.5")
    public void onCompareMethod() throws Throwable {
        String method = "compare";

        Object c1 = klass.constructor().takingNoParams().invoke();

        assertTrue("in class " + className + ", add method public int " + method + "(Card c1, Card c2)",
                klass.method(c1, method)
                        .returning(int.class).taking(Card.class, Card.class).isPublic());

        String v = "\nError was caused by code:\n"
                + "BySuitInValueOrder comparator = new BySuitInValueOrder();\n"
                + "Card c1 = new Card(3, Maa.HEART);\n"
                + "Card c2 = new Card(4, Maa.SPADE));\n"
                + "comparator.compareTo(c1, c2);";

        klass.method(c1, method)
                .returning(int.class).taking(Card.class, Card.class).withNiceError(v).
                invoke(new Card(3, Suit.HEART), new Card(4, Suit.SPADE));
    }

    @Test
    @Points("10-17.5")
    public void test() throws Throwable {
        test(2, 2);
        test(5, 8);
        test(14, 3);

        int[][] numbers = {
            {2, 2, 4, 3},
            {3, 0, 2, 3},
            {4, 2, 5, 2},
            {7, 4, 12, 4},};

        for (int[] is : numbers) {
            C_ComparatorTest.this.test(is[0], is[1], is[2], is[3], false);
            C_ComparatorTest.this.test(is[2], is[3], is[0], is[1], true);
        }
    }

    public int testTwo(Card h1, Card h2) throws Throwable {
        String method = "compare";

        Object comparator = klass.constructor().takingNoParams().invoke();

        return klass.method(comparator, method)
                .returning(int.class).taking(Card.class, Card.class).
                invoke(h1, h2);
    }

    public void test(int a1, int m1, int a2, int m2, boolean pos) throws Throwable {
        int answer = testTwo(new Card(a1, m(m1)), new Card(a2, m(m2)));
        String t = pos ? "positive" : "negative";
        boolean result = pos ? answer > 0 : answer < 0;

        assertTrue("result should have been a " + t + " number\n"
                + "BySuitInValueOrder comparator = new BySuitInValueOrder();\n"
                + "Card first = new Card(" + a1 + "," + m(m1) + ");\n"
                + "Card second = new Card(" + a2 + "," + m(m2) + ");\n"
                + "comparator.compare(first,second)\n"
                + "result was: " + answer, result);
    }

    public void test(int a1, int m1) throws Throwable {
        int answer = testTwo(new Card(a1, m(m1)), new Card(a1, m(m1)));

        assertEquals(
                "BySuitInValueOrder comparator = new BySuitInValueOrder();\n"
                + "Card first = new Card(" + a1 + "," + m(m1) + ");\n"
                + "Card second = new Card(" + a1 + "," + m(m1) + ");\n"
                + "comparator.compare(first,second)", 0, answer);
    }

}
