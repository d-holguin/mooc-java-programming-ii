

import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class D_HandTest<_Hand> {

    String klassName = "Hand";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    private void add(Object obj, Card c) throws Throwable {
        klass.method(obj, "add")
                .returningVoid()
                .taking(Card.class)
                .invoke(c);
    }

    @Test
    @Points("10-17.6")
    public void methodSortBySuit() throws Throwable {
        String method = "sortBySuit";

        Object obj = create();

        assertTrue("in class" + klassName + ", add a method public void " + method + "()",
                klass.method(obj, method)
                        .returningVoid().takingNoParams().isPublic());

        String v = "Hand hand = new Hand();\n"
                + "hand.print();";

        klass.method(obj, method)
                .returningVoid().takingNoParams().withNiceError("error was caused by code:\n" + v).
                invoke();
    }

    @Test
    @Points("10-17.6")
    public void sortedBySuitPrintWorks() throws Throwable {
        MockInOut io = new MockInOut("");

        Object obj = create();

        Card c0 = new Card(14, Suit.SPADE);
        Card c = new Card(12, Suit.HEART);
        Card c3 = new Card(14, Suit.DIAMOND);
        Card c2 = new Card(2, Suit.SPADE);
        Card c4 = new Card(7, Suit.DIAMOND);

        add(obj, c0);
        add(obj, c);
        add(obj, c3);
        add(obj, c2);
        add(obj, c4);

        String v = "Hand hand = new Hand();\n"
                + "hand.add( new Card(14,Suit.SPADE) ); \n"
                + "hand.add( new Card(12,Suit.HEART) ); \n"
                + "hand.add( new Card(14,Suit.DIAMOND) ); \n"
                + "hand.add( new Card(2,Suit.SPADE) ); \n"
                + "hand.add( new Card(7,Suit.DIAMOND) ); \n"
                + "hand.sortBySuit();\n"
                + "hand.print()\n";

        klass.method(obj, "sortBySuit")
                .returningVoid().takingNoParams().withNiceError("error was caused by code:\n" + v).
                invoke();

        klass.method(obj, "print")
                .returningVoid().takingNoParams().withNiceError("error was caused by code:\n" + v).
                invoke();

        String out = io.getOutput();

        assertTrue("With code \n" + v + "output should have 5 rows, now the output was\n" + out, out.split("\n").length > 4);

        int j1 = out.indexOf(c4.toString());
        int j2 = out.indexOf(c3.toString());
        int j3 = out.indexOf(c.toString());
        int j4 = out.indexOf(c2.toString());
        int j5 = out.indexOf(c0.toString());

        assertTrue("Not every card was printed with the code\noutput was\n" + out, j1 > -1 && j2 > -1 && j3 > -1 && j4 > -1 && j5 > -1);

        assertTrue("With code\n" + v + "first card should be" + c4 + "\noutput was\n" + out, j1 < j2 && j1 < j3 && j1 < j4 && j1 < j5);
        assertTrue("With code\n" + v + "second card should be" + c3 + "\noutput was\n" + out, j2 < j3 && j2 < j4 && j2 < j4);
        assertTrue("With code\n" + v + "third card should be" + c + "\noutput was\n" + out, j3 < j4 && j3 < j5);
        assertTrue("With code\n" + v + "fourth card should be" + c2 + "\noutput was\n" + out, j4 < j5);

//        String left = out;
//        assertTrue("With code\n" + v + "ensin card should be" + c4 + "\noutput was\n" + out, left.contains(c4.toString()));
//        left = left.substring(left.indexOf(c4.toString()));
//        assertTrue("With code\n" + v + "toisena card should be" + c3 + "\noutput was\n" + out, left.contains(c3.toString()));
//        left = left.substring(left.indexOf(c3.toString()));
//        assertTrue("With code\n" + v + "kolmantena card should be" + k + "\noutput was\n" + out, left.contains(k.toString()));
//        left = left.substring(left.indexOf(k.toString()));
//        assertTrue("With code\n" + v + "neljäntenä card should be" + c2 + "\noutput was\n" + out, left.contains(c2.toString()));
//        left = left.substring(left.indexOf(c2.toString()));
//        assertTrue("With code\n" + v + "viimeisenä card should be" + c0 + "\noutput was\n" + out, left.contains(c0.toString()));
    }
}
