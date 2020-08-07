
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class C_ShoppingCartTest {

    String klassName = "ShoppingCart";
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
    @Points("09-10.5")
    public void theClassShoppingCartExits() {
        assertTrue("The class " + klassName + " must be public, i.e, it must be declared as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("09-10.5")
    @Test
    public void hasMapOrList() {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        String k = "Store the " + klassName + "'s items either in a instance variable Map<String, Item> items;\n"
                + "or a instance variable List<Item> items; ";

        int maps = 0;
        for (Field field : fields) {
            assertFalse(k
                    + "so change " + fieldName(field.toString()) + " to the right type", field.toString().contains("HashMap"));
            assertFalse(k
                    + "so change " + fieldName(field.toString()) + " to the right type", field.toString().contains("ArrayList"));

            assertFalse(k + " You don't need any other instance variables, remove: " + fieldName(field.toString()), !field.toString().contains("Map") && !field.toString().contains("List"));

            if (field.toString().contains("Map") || field.toString().contains("List")) {
                maps++;
            }
        }
        assertTrue(k, maps > 0 && maps < 3);

    }

    @Test
    @Points("09-10.5")
    public void noExtraVariables() {
        cleanlinessCheck(klassName, 1, "an instance variable that stores Item objects");
    }

    @Test
    @Points("09-10.5")
    public void constructorCheck() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("For the " + klassName + " class, define the public constructor: public " + klassName + "()", ctor.isPublic());
        String v = "this error was caused trying to run the code: new ShoppingCart();";
        ctor.withNiceError(v).invoke();
    }

    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    @Points("09-10.5")
    public void methodPriceExits() throws Throwable {
        String method = "price";

        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method public int " + method + "()",
                klass.method(object, method)
                .returning(int.class).takingNoParams().isPublic());

        String v = "cart = new ShoppingCart(); cart.price()";

        klass.method(object, method)
                .returning(int.class).takingNoParams().withNiceError("this error was caused by the code \n" + v).invoke();
    }

    @Test
    @Points("09-10.5")
    public void priceOfAnEmptyCartIsZero() throws Throwable {
        String k = "cart = new ShoppingCart(); cart.hinta()";
        Object cart = newShoppingCart();
        int price = price(cart);
        assertEquals(k, 0, price);
    }

    @Test
    @Points("09-10.5")
    public void methodAddExits() throws Throwable {
        String method = "add";

        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method public void " + method + "(String product, int price)",
                klass.method(object, method)
                .returningVoid().taking(String.class, int.class).isPublic());

        String v = "cart = new ShoppingCart(); cart.add(\"milk\",3)";

        klass.method(object, method)
                .returningVoid().taking(String.class, int.class).withNiceError("this error was caused by trying to run the code: \n" + v).invoke("milk", 3);
    }

    @Test
    @Points("09-10.5")
    public void addingAnItemIncreasesCartsPrice() throws Throwable {
        String k = "cart = new ShoppingCart(); cart.add(\"milk\",3); cart.price()";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        int price = price(cart);
        assertEquals(k, 3, price);
    }

    @Test
    @Points("09-10.5")
    public void addingTwoDifferentProductsIncreasesCartsPrice() throws Throwable {
        String k = "cart = new ShoppingCart(); cart.add(\"milk\",3); cart.add(\"butter\",5); cart.price()";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        add(cart, "butter", 5);
        int price = price(cart);
        assertEquals(k, 8, price);
    }

    @Test
    @Points("09-10.5")
    public void addingThreeDifferentProducsIncreasesCartsPrice() throws Throwable {
        String k = "cart = new ShoppingCart(); cart.add(\"milk\",3); cart.add(\"butter\",5);cart.add(\"bread\",4); cart.price()";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        add(cart, "butter", 5);
        add(cart, "bread", 4);
        int price = price(cart);
        assertEquals(k, 12, price);
    }

    /*
     *
     */
    @Test
    @Points("09-10.6")
    public void methodPrintExits() throws Throwable {
        String metodi = "print";

        Object object = create();

        assertTrue("For the " + klassName + " class, implemnt the method public void " + metodi + "()",
                klass.method(object, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "cart = new ShoppingCart(); cart.print()";

        klass.method(object, metodi)
                .returningVoid().takingNoParams().withNiceError("this error was caused by trying to run the code: \n" + v).invoke();

    }

    @Test
    @Points("09-10.6")
    public void printingWorks() throws Throwable {
        MockInOut io = new MockInOut("");

        String k = "cart = new ShoppingCart(); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.add(\"butter\",5);\n"
                + "cart.add(\"bread\",4); \n"
                + "cart.print()\n";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        add(cart, "butter", 5);
        add(cart, "bread", 4);
        print(cart);

        String[] t = io.getOutput().split("\n");
        assertEquals("Make sure ShoppingCart's method print functions like shown in the example, \n"
                + "" + k + " number of lines to print", 3, t.length);
        String searched = "milk: 1";
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example,  \n"
                + k + " the line " + searched + " should be printed. Instead you printed:\n"+io.getOutput(), contains(t, searched));
        searched = "butter: 1";
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example,  \n"
                + k + " the line " + searched + " should be printed. Instead you printed:\n"+io.getOutput(), contains(t, searched));
        searched = "bread: 1";
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example,  \n"
                + k + " the line " + searched + " should be printed. Instead you printed:\n"+io.getOutput(), contains(t, searched));
    }

    @Test
    @Points("09-10.7")
    public void addingTheSameProductTwiceIncreasesCartsPrice() throws Throwable {
        String k = "cart = new ShoppingCart(); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.price()";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        add(cart, "milk", 3);
        int hinta = price(cart);
        assertEquals(k, 6, hinta);
    }

    @Test
    @Points("09-10.7")
    public void addingTheSameProductTwiceDoesNotCreateTwoItems() throws Throwable {
        MockInOut io = new MockInOut("");

        String k = "cart = new ShoppingCart(); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.print()";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        add(cart, "milk", 3);
        print(cart);

        String[] t = io.getOutput().split("\n");
        assertEquals("Make sure ShoppingCart's method print functions like shown in the example, "
                + "when the same product is added to the cart twice \n"
                + k + " the number of lines to print", 1, t.length);
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example, "
                + "when the same product is added to the cart twice \n"
                + k + "\n"
                + "the only line printed should be: milk: 2, instead you printed \n" + t[0] + "\n", t[0].contains("milk: 2"));
    }

    @Test
    @Points("09-10.7")
    public void oneProductMultipleTimesAndSeveralOtherProducts() throws Throwable {
        MockInOut io = new MockInOut("");
        String k = "cart = new ShoppingCart(); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.add(\"sausage\",7); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.add(\"milk\",3); \n"
                + "cart.add(\"sausage\",7); \n"
                + "cart.add(\"cream\", 2);\n"
                + "cart.price()";

        Object cart = newShoppingCart();
        add(cart, "milk", 3);
        add(cart, "sausage", 7);
        add(cart, "milk", 3);
        add(cart, "milk", 3);
        add(cart, "sausage", 7);
        add(cart, "cream", 2);
        int price = price(cart);
        assertEquals(k, 25, price);

        print(cart);

        String[] t = io.getOutput().split("\n");

        assertEquals("Make sure ShoppingCart's method print functions like shown in the example, "
                + "when one product was added to the cart multiple times \n"
                + k + " the number of lines printed", 3, t.length);
        String searched = "milk: 3";
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example,  \n"
                + k + " the line " + searched + " should be printed, ", contains(t, searched));
        searched = "sausage: 2";
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example,  \n"
                + k + " the line " + searched + " should be printed, ", contains(t, searched));
        searched = "cream: 1";
        assertTrue("Make sure ShoppingCart's method print functions like shown in the example,  \n"
                + k + " the line " + searched + " should be printed, ", contains(t, searched));
    }

    /*
     *
     */
    @Test
    @Points("09-10.8")
    public void storeClassExists() {
        try {
            ReflectionUtils.findClass("Store");
        } catch (Throwable e) {
            fail("Create the class Store in your program, and copypaste the code templete from the exercise description there");
        }
    }

    @Test
    @Points("09-10.8")
    public void shoppingWorks() throws Throwable {
        int stock = 0;
        String lines[] = null;
        Object w = null;

        try {
            MockInOut io = new MockInOut("");
            Scanner sk = new Scanner("coffee\nbread\nwater\n\n");
            w = newWarehouse();

            addToWarehouse(w, "coffee", 5, 10);
            addToWarehouse(w, "milk", 3, 20);
            addToWarehouse(w, "cream", 2, 55);
            addToWarehouse(w, "bread", 7, 8);
            Object store = newStore(w, sk);
            shop(store, "John");
            stock = stock(w, "coffee");
            lines = io.getOutput().split("\n");


        } catch (Throwable t) {
            fail("the store was created just like in the example and the customers input was coffee<enter>bread<enter>water<enter><enter>\n encountered exception " + t + "\n"
                    + "make sure you copypasted the code from the exercise description into the Store class");
        }
        assertEquals("the store was created just like in the example and the customers input was coffee<enter>bread<enter>water<enter><enter>\n the stock of coffee should be reduced by one", 9, stock);
        stock = stock(w, "bread");
        assertEquals("the store was created just like in the example and the customers input was coffee<enter>bread<enter>water<enter><enter>\n the stock of bread should be reduced by one", 7, stock);
        assertTrue("the store was created just like in the example and the customers input was coffee<enter>bread<enter>water<enter><enter>\n the price of the cart should be 12, but it was " + lines[lines.length - 1], lines[lines.length - 1].contains("12"));
    }

    private void shop(Object object, String name) throws Throwable {
        try {
            Class clzz = ReflectionUtils.findClass("Store");
            Method method = ReflectionUtils.requireMethod(clzz, "shop", String.class);
            ReflectionUtils.invokeMethod(void.class, method, object, name);
        } catch (Throwable t) {
            throw t;
        }
    }

    private int stock(Object object, String product) throws Throwable {
        try {
            Class clzz = ReflectionUtils.findClass("Warehouse");
            Method method = ReflectionUtils.requireMethod(clzz, "stock", String.class);
            return ReflectionUtils.invokeMethod(int.class, method, object, product);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void addToWarehouse(Object object, String product, int price, int stock) throws Throwable {
        try {
            Class clzz = ReflectionUtils.findClass("Warehouse");
            Method method = ReflectionUtils.requireMethod(clzz, "addProduct", String.class, int.class, int.class);
            List<String> l = null;

            ReflectionUtils.invokeMethod(void.class, method, object, product, price, stock);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Object newWarehouse() throws Throwable {
        String nameOfTheClass = "Warehouse";
        try {
            Class clzz = ReflectionUtils.findClass(nameOfTheClass);
            return ReflectionUtils.invokeConstructor(clzz.getConstructor());
        } catch (Throwable t) {
            fail("For the " + nameOfTheClass + " class, implment a public constructor with no parameters");
        }
        return null;
    }

    private Object newStore(Object warehouse, Scanner scanner) throws Throwable {
        String cclassName = "Store";
        try {
            Class clzz = ReflectionUtils.findClass(cclassName);
            return clzz.getConstructors()[0].newInstance(warehouse, scanner);
            //return ReflectionUtils.invokeConstructor(clzz.getConstructor(), varasto, lukija);
        } catch (Throwable t) {
            fail("Add a public constructor with no parameter for the " + cclassName + " class");
        }
        return null;
    }

    private void add(Object object, String product, int price) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "add", String.class, int.class);
            ReflectionUtils.invokeMethod(void.class, method, object, product, price);
        } catch (Throwable t) {
            throw t;
        }
    }

    private int price(Object object) throws Throwable {
        try {
            Method metodi = ReflectionUtils.requireMethod(c, "price");

            return ReflectionUtils.invokeMethod(int.class, metodi, object);
        } catch (Throwable t) {
            throw t;
        }
    }

    private void print(Object object) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "print");

            ReflectionUtils.invokeMethod(void.class, method, object);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Object newShoppingCart() throws Throwable {
        try {
            c = ReflectionUtils.findClass(klassName);
            return ReflectionUtils.invokeConstructor(c.getConstructor());
        } catch (Throwable t) {
            fail("Implement a public constructor without parameters for the " + klassName + " class");
        }
        return null;
    }

    private String fieldName(String toString) {
        return toString.replace(klassName + ".", "");
    }

    private boolean contains(String[] t, String mj) {
        for (String line : t) {
            if (line.contains(mj)) {
                return true;
            }
        }
        return false;
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
