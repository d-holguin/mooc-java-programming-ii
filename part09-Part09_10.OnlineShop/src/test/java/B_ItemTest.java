import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class B_ItemTest {

    String klassName = "Item";
    Reflex.ClassRef<Object> klass;
    Class c;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
        try {
            c = ReflectionUtils.findClass(klassName);
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("09-10.4")
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, i.e, it must be declared as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("09-10.4")
    public void constructorExits() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Integer, Integer> ctor = klass.constructor().taking(String.class, int.class, int.class).withNiceError();
        assertTrue("For the " + klassName + " class, implment the public constructor: public " + klassName + "(String product, int qty, int unitPrice)", ctor.isPublic());
        String v = "the error was caused by trying to run: new Item(\"milk\",2,4);";
        ctor.withNiceError(v).invoke("milk", 2, 4);
    }

    public Object create(String product, int price, int qty) throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Integer, Integer> ctor = klass.constructor().taking(String.class, int.class, int.class).withNiceError();
        return ctor.invoke(product, price, qty);
    }

    @Test
    @Points("09-10.4")
    public void noExtraVariables() {
        cleanlinessCheck(klassName, 3, "instance variables for the product name, unit price and quantity");
    }

    @Test
    @Points("09-10.4")
    public void priceMethod() throws Throwable {
        String method = "price";

        Object object = create("milk", 2, 3);

        assertTrue("For the " + klassName + " class, implemnt the method public int " + method + "()",
                klass.method(object, method)
                .returning(int.class).takingNoParams().isPublic());

        String v = "Item item = new Item(\"milk\",2,3);\n"
                + "item.price();";

        assertEquals(v, 6, (int)klass.method(object, method)
                .returning(int.class).takingNoParams().withNiceError("The error was caused by trying to run the code: \n"+v).invoke());
    }

    @Test
    @Points("09-10.4")
    public void priceIsCalculatedCorrectly() throws Throwable {
        String k = "i = new Item(\"bread\", 1, 5); i.price()";

        Object item = newItem("bread", 1, 5);
        int price = price(item);
        assertEquals(k, 5, price);
    }

    /*
     *
     */

    @Test
    @Points("09-10.4")
    public void methodIncreaseQuantityExits() throws Throwable {
        String method = "increaseQuantity";

        Object object = create("milk", 2, 3);

        assertTrue("For the " + klassName + " class, implement he method public void " + method + "()",
                klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "Item item = new Item(\"milk\",1,2);\n"
                + "item.kasvataMaaraa();";

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError("the error was caused by trying to run the code: \n"+v).invoke();

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v+"\nitem.price();").invoke();
    }

    @Test
    @Points("09-10.4")
    public void quantityIncreases() throws Throwable {
        String k = "i = new Item(\"bread\", 1, 5); i.increaseQuantity(), i.price()";

        Object item = newItem("bread", 1, 5);
        increaseQuantity(item);
        int price = price(item);
        assertEquals(k, 10, price);
    }

    @Test
    @Points("09-10.4")
    public void toStringImplemented() {
        Object item = newItem("bread", 1, 5);
        assertFalse("For the Item class, implement the method public String toString()", item.toString().contains("@"));
    }

    @Test
    @Points("09-10.4")
    public void toStringWorksCorrectly() throws Throwable {
        String k = "i = new Item(\"milk\", 2, 4); System.out.println( i )";

        Object item = newItem("milk", 2, 4);
        assertEquals("Make sure the string return by toString follows the format: \"product: qty\""
                + "\n" + k, "milk: 2", item.toString());
    }

    @Test
    @Points("09-10.4")
    public void toStringWorksCorrectly2() throws Throwable {
        String k = "i = new Item(\"cheese\", 17, 3); System.out.println( i )";

        Object item = newItem("cheese", 17, 3);
        assertEquals("Make sure the string return by toString follows the format: \"product: qty\""
                + "\n" + k, "cheese: 17", item.toString());
    }

    /*
     *
     */
    private Object newItem(String product, int qty, int price) {
        try {
            c = ReflectionUtils.findClass(klassName);

            Constructor[] cc = c.getConstructors();
            return cc[0].newInstance(product, qty, price);
        } catch (Throwable t) {
            fail("For the " + klassName + " class, implement the constructor Item(String product, int qty, int unitPrice)\n"
                    + "no other constructors are needed!");
        }
        return null;
    }

    private int price(Object object) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "price");
            return ReflectionUtils.invokeMethod(int.class, method, object);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void increaseQuantity(Object object) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "increaseQuantity");
            ReflectionUtils.invokeMethod(void.class, method, object);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void cleanlinessCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", in the" + klassName + " class, remove variable " + fieldName(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All the instance variable for the class should be private, but in the " + klassName + " class found: " + fieldName(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("For the " + klassName + "-class, you only need " + m + ", remove extras", var <= n);
        }
    }

    private String fieldName(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

}
