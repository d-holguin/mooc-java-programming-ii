

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("10-17.1")
public class A_CardTest extends Help {

    Class cardClass;
    String className = "Card";
    String fullName = className;
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(fullName);

        try {
            cardClass = ReflectionUtils.findClass(fullName);
        } catch (Throwable t) {
            fail("Make sure you have a class \"" + className + "\".");
        }
        
        assertTrue("Class " + className + " must be public, so it needs to be defined as\npublic class " + className + " {...\n}", klass.isPublic());
    }

    @Test
    public void onImplementComparableCard() {
        Class kl;
        try {
            kl = cardClass;
            Class is[] = kl.getInterfaces();
            Class correct[] = {java.lang.Comparable.class};
            assertTrue("Make sure the class " + className + " has just one interface, and that that interface is Comparable",
                    Arrays.equals(is, correct));

        } catch (Throwable t) {
            fail("Make sure the class " + className + " has the interface \"Comparable<Card>\"");
        }
    }

    @Test
    public void onCompareToMethod() throws Throwable {
        String method = "compareTo";

        Card first = new Card(14, Suit.HEART);
        Card second = new Card(10, Suit.CLUB);

        assertTrue("In class " + className + " add a method public int " + method + "(Card comparable)",
                klass.method(first, method)
                        .returning(int.class).taking(Card.class).isPublic());

        String v = "\nError caused by\n"
                + "Card first = new Card(14, Suit.HEART);\n"
                + "Kortti second = new Card(10, Suit.CLUB);\n"
                + "first.compareTo(second);";

        klass.method(first, method)
                .returning(int.class).taking(Card.class).withNiceError(v).invoke(second);

        try {
            ReflectionUtils.requireMethod(cardClass, int.class, "compareTo", Card.class);
        } catch (Throwable t) {
            fail("Make sure you've created the method \"public int compareTo(Card another)\"");
        }
    }

    private Method compareToMethod() {
        Method m = ReflectionUtils.requireMethod(cardClass, "compareTo", Card.class);
        return m;
    }

    @Test
    public void testCompareTo() {
        try {
            Object h1 = new Card(2, Suit.HEART);
            Object h2 = new Card(2, Suit.HEART);
            Method m = compareToMethod();
            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);

        } catch (Throwable ex) {

            fail("Make sure that in class \"Card\" you've created a method \"public int compareTo(Card another)\".\n"
                    + "Does the Card class also have the Comparable<Card> interface?");
        }
    }

    @Test
    public void isComparable() {
        try {
            assertTrue("The Card class is missing the Comparable interface.", cardClass.getInterfaces()[0].equals(Comparable.class));
        } catch (Throwable t) {
            fail("The Card class is missing the Comparable interface.");
        }
    }

    @Test
    public void test() {
        A_CardTest.this.test(2, 2);
        A_CardTest.this.test(5, 2);
        A_CardTest.this.test(14, 3);

        int[][] numbers = {
            {4, 2, 5, 2},
            {3, 2, 4, 3},
            {6, 2, 8, 3},
            {10, 2, 10, 3},
            {11, 1, 11, 2}
        };

        for (int[] is : numbers) {
            test(is[0], is[1], is[2], is[3], false);
            test(is[2], is[3], is[0], is[1], true);
        }
    }

    public int testTwo(Card h1, Card h2) {
        try {
            Method m = compareToMethod();

            int result = ReflectionUtils.invokeMethod(int.class, m, h1, h2);
            return result;
        } catch (Throwable ex) {

            fail("Olethan toteuttanut luokalla \"Kortti\" metodin \"public int compareTo(Kortti toinen)\".\n"
                    + "Toteuttaahan Kortti-luokka myös rajapinnan Comparable<Kortti>?");
            return -111;
        }
    }

    public void test(int a1, int m1, int a2, int m2, boolean pos) {
        int answer = testTwo(new Card(a1, m(m1)), new Card(a2, m(m2)));
        String t = pos ? "positive" : "negative";
        boolean result = pos ? answer > 0 : answer < 0;

        assertTrue("result should have been " + t + " number\n"
                + "Card first = new Card(" + a1 + "," + m(m1) + ");\n"
                + "Card second = new Card(" + a2 + "," + m(m2) + ");\n"
                + "first.compareTo(second)\n"
                + "result was: " + answer, result);
    }

    public void test(int a1, int m1) {
        int answer = testTwo(new Card(a1, m(m1)), new Card(a1, m(m1)));

        assertEquals(
                "Card first = new Card(" + a1 + "," + m(m1) + ");\n"
                + "Card second = new Card(" + a1 + "," + m(m1) + ");\n"
                + "first.compareTo(second)", 0, answer);
    }

}
