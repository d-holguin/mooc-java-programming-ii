
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class A_WarehouseTest {

    String klassName = "Warehouse";
    Class c;
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
        try {
            c = ReflectionUtils.findClass(klassName);
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("09-10.1")
    public void classIsPublic() {
        assertTrue("Class " + klassName + " must be public, i.e, it must be defined with\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("09-10.1")
    public void constructor() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("The class " + klassName + " must have a public constructor: public " + klassName + "()", ctor.isPublic());
        String v = "the erreo was caused by running the code: new Warehouse();";
        ctor.withNiceError(v).invoke();
    }

    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Points("09-10.1")
    @Test
    public void mapImplemented() {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        int map = 0;
        for (Field field : fields) {
            assertFalse("Store the" + klassName + "'s product prices in instance variable Map<String, Integer> prices; \n"
                    + "i.e, change the field " + fieldName(field.toString()) + " to the right type", field.toString().contains("HashMap"));
            assertFalse("The class " + klassName + " doesn't need an instance variable with the list type, remove " + fieldName(field.toString()), field.toString().matches("/.*java\\.util\\.([A-Za-zöäåÖÄÅ]*List).*/"));
            if (field.toString().contains("Map")) {
                map++;
            }
        }
        assertTrue("Store the" + klassName + "'s product prices in instance variable Map<String, Integer> prices", map > 0 && map < 3);

    }

    @Test
    @Points("09-10.1")
    public void methodAddProductExits() throws Throwable {
        String method = "addProduct";

        Object object = create();

        assertTrue("For the class " + klassName + " implement the method public void " + method + "(String product, int price, int stock)",
                klass.method(object, method)
                .returningVoid().taking(String.class, int.class, int.class).isPublic());

        String v = "\nThis error was caused by trying: Warehouse w = new Warehouse(); "
                + "w.addProduct(\"coffee\",2, 25);";

        klass.method(object, method)
                .returningVoid().taking(String.class, int.class, int.class).withNiceError(v).invoke("coffee", 2, 25);
    }

    private void addProduct(Object object, String product, int price, int qty) throws Throwable {
        klass.method(object, "addProduct")
                .returningVoid().taking(String.class, int.class, int.class).invoke(product, price, qty);
    }

    @Test
    @Points("09-10.1")
    public void methodPriceImplemented() throws Throwable {
        String method = "price";
        Object object = create();

        assertTrue("For the class " + klassName + " implement the method public int " + method + "(String product)",
                klass.method(object, method)
                .returning(int.class).taking(String.class).isPublic());

        String v = "\nThis error was caused by trying: Warehouse w = new Warehouse; \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.price(\"coffee\");";

        addProduct(object, "coffee", 2, 25);
        addProduct(object, "milk", 3, 10);

        klass.method(object, method)
                .returning(int.class).taking(String.class).withNiceError(v).invoke("coffee");

    }

    @Points("09-10.1")
    @Test
    public void priceMethodWorks() throws Throwable {
        String code = "w = new Warehouse(); \n"
                + "w.addProduct(\"milk\", 3, 10); \n"
                + "w.addProduct(\"coffee\", 5, 7);\n"
                + "w.price(\"milk\"); ";

        Object w = newWarehouse();
        add(w, "milk", 3, 10);
        add(w, "coffee", 5, 7);

        int t = price(w, "milk");
        assertEquals(code, 3, t);

        code += "w.price(\"coffee\"); ";
        t = price(w, "coffee");
        assertEquals(code, 5, t);
    }

    @Points("09-10.1")
    @Test
    public void failedPriceCheckDoesNotCauseAnException() throws Throwable {
        String method = "price";
        Object object = create();

        assertTrue("For the the " + klassName + " class, implement the method public int " + method + "(String product)",
                klass.method(object, method)
                .returning(int.class).taking(String.class).isPublic());

        String v = "\nRemember to adress a situation where price is requested for a product not in the warehouse!"
                + "\nThis error was caused by trying: Warehouse w = new Warehouse; \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.price(\"cheese\");";

        addProduct(object, "coffee", 2, 25);
        addProduct(object, "milk", 3, 10);

        klass.method(object, method)
                .returning(int.class).taking(String.class).withNiceError(v).invoke("cheese");
    }

    @Points("08-10.1")
    @Test
    public void priceCheckForNonexistantProduct() throws Throwable {
        String code = "w = new Warehouse(); \n"
                + "w.addProduct(\"milk\", 3, 10); \n"
                + "w.addProduct(\"coffee\", 5, 7); \n"
                + "w.price(\"bread\"); ";

        Object w = newWarehouse();
        add(w, "milk", 3, 10);
        add(w, "coffee", 5, 7);

        int t = price(w, "bread");
        assertEquals("if a product is not in the warehouse, its price should be returned as -99, " + code, -99, t);
    }

    /*
     *
     */
    @Points("09-10.2")
    @Test
    public void mapExists() {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        int maps = 0;
        for (Field field : fields) {
            assertFalse("The " + klassName + "'s product prices must be stored in the instance variable: Map<String, Integer> quantities; \n"
                    + "so change " + fieldName(field.toString()) + " to the right type", field.toString().contains("HashMap"));
            assertFalse("The class " + klassName + " doesn't need an instance variable with the list type, remove " + fieldName(field.toString()), field.toString().matches("/.*java\\.util\\.([A-Za-zöäåÖÄÅ]*List).*/"));
            if (field.toString().contains("Map")) {
                maps++;
            }
        }
        assertTrue("Store " + klassName + ":s product quantities in the instance variable Map<String, Integer> quantities;\n"
                + "Your class need exactly two Maps, not more or less", maps > 1 && maps < 3);

    }

    @Test
    @Points("09-10.2")
    public void stockMethodExits() throws Throwable {
        String method = "stock";
        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method public int " + method + "(String product)",
                klass.method(object, method)
                .returning(int.class).taking(String.class).isPublic());

        String v = "\nThis error was caused by trying: Warehouse w = new Warehouse; \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.stock(\"coffee\");";

        addProduct(object, "coffee", 2, 25);
        addProduct(object, "milk", 3, 10);

        klass.method(object, method)
                .returning(int.class).taking(String.class).withNiceError(v).invoke("coffee");
    }

    @Points("09-10.2")
    @Test
    public void stockMethodWorksWithProductInTheWarehouse() throws Throwable {
        String code = "w = new Warehouse(); w.addProduct(\"milk\", 3, 10); v.addProduct(\"coffee\", 5, 7); "
                + "w.stock(\"milk\"); ";

        Object w = newWarehouse();
        add(w, "milk", 3, 10);
        add(w, "coffee", 5, 7);

        int t = stock(w, "milk");
        assertEquals(code, 10, t);

        code += "w.stock(\"coffee\"); ";
        t = stock(w, "coffee");
        assertEquals(code, 7, t);
    }

    @Test
    @Points("09-10.2")
    public void stockForAProductNotInTheWarehouse() throws Throwable {
        String method = "stock";
        Object object = create();

        assertTrue("For the " + klassName + " class implement the method public int " + method + "(String product)",
                klass.method(object, method)
                .returning(int.class).taking(String.class).isPublic());

        String v = "Make sure to handle situations where stock is requested for a product not in the warehouse\n"
                + "Warehouse w = new Warehouse(); \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.stock(\"cheese\");";

        addProduct(object, "coffee", 2, 25);
        addProduct(object, "milk", 3, 10);

        assertEquals(v, 0, (int) klass.method(object, method)
                .returning(int.class).taking(String.class).withNiceError("The error was caused by the following code: \n" + v).invoke("cheese"));
    }

    @Test
    @Points("09-10.2")
    public void methodTakeExits() throws Throwable {
        String metodi = "take";
        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method public boolean " + metodi + "(String product)",
                klass.method(object, metodi)
                .returning(boolean.class).taking(String.class).isPublic());

        String v = "\nThe error was caused by the following code: Warehouse w = new Warehouse(); \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.take(\"coffee\");";

        addProduct(object, "coffee", 2, 25);
        addProduct(object, "milk", 3, 10);

        klass.method(object, metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke("coffee");

        v = "\nThe error was caused by the following code: Warehouse w = new Warehouse(); \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.take(\"cheese\");";

        klass.method(object, metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invoke("cheese");
    }

    @Points("09-10.2")
    @Test
    public void takeReducesQuantity() throws Throwable {
        String code = "w = new Warehouse(); \n"
                + "w.addProduct(\"milk\", 3, 10); \n"
                + "w.addProduct(\"coffee\", 5, 7); \n"
                + "w.take(\"coffee\");\n";

        Object w = newWarehouse();
        add(w, "milk", 3, 10);
        add(w, "coffee", 5, 7);

        boolean b = take(w, "coffee");

        assertEquals(code, true, b);

        code += "w.stock(\"coffee\"); ";

        int t = stock(w, "coffee");
        assertEquals(code, 6, t);
    }

    @Points("09-10.2")
    @Test
    public void takeWorksWhenStockBecomesZero() throws Throwable {
        String code = "w = new Warehouse(); \n"
                + "w.addProduct(\"milk\", 3, 10); \n"
                + "w.addProduct(\"coffee\", 5, 1); \n"
                + "w.take(\"milk\");\n"
                + "w.take(\"milk\");\n";

        Object w = newWarehouse();
        add(w, "milk", 3, 10);
        add(w, "coffee", 5, 1);

        take(w, "coffee");
        boolean b = take(w, "coffee");

        assertEquals(code, false, b);

        code += "w.stock(\"coffee\"); ";

        int t = stock(w, "coffee");
        assertEquals(code, 0, t);
    }

    @Points("09-10.2")
    @Test
    public void tryingToTakeAProductThatDoesNotExistReturnsFalse() throws Throwable {
        String code = "w = new Warehouse(); \n"
                + "w.addProduct(\"milk\", 3, 10); \n"
                + "w.addProduct(\"coffee\", 5, 7);\n"
                + "w.take(\"bread\");";

        Object w = newWarehouse();
        add(w, "milk", 3, 10);
        add(w, "coffee", 5, 7);

        boolean b = take(w, "bread");
        assertFalse(code, b);
    }

    /*
     *
     */
    @Test
    @Points("09-10.3")
    public void methodProductsExits() throws Throwable {
        String method = "products";
        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method public Set<String>  " + method + "()",
                klass.method(object, method)
                .returning(Set.class).takingNoParams().isPublic());

        String v = "\nThe error was caused by the following code: Warehouse w = new Warehouse(); \n"
                + "w.addProduct(\"coffee\",2, 25);\n"
                + "w.addProduct(\"milk\",3, 10);\n"
                + "w.products();";

        addProduct(object, "coffee", 2, 25);
        addProduct(object, "milk", 3, 10);

        klass.method(object, method)
                .returning(Set.class).takingNoParams().withNiceError(v).invoke();
    }

    @Points("09-10.3")
    @Test
    public void productsMethodWorks() throws Throwable {
        String code = "w = new Warehouse(); \n"
                + "w.addProduct(\"milk\", 3, 10); \n"
                + "w.addProduct(\"coffee\", 5, 7); \n"
                + "w.addProduct(\"sugar\", 2, 25);\n"
                + "w.products();";

        Object v = newWarehouse();
        add(v, "milk", 3, 10);
        add(v, "coffee", 5, 7);
        add(v, "sugar", 2, 25);
        Set<String> p = products(v);

        assertFalse(code + " returned a set that was null", p == null);

        assertEquals(code + " returned the set +" + p + " its size was ", 3, p.size());
        assertEquals(code + " returned the set +" + p + " \"milk\" is included in the set ", true, p.contains("milk"));
        assertEquals(code + " returned the set +" + p + " \"coffee\" is included in the set ", true, p.contains("coffee"));
        assertEquals(code + " returned the set +" + p + " \"sugar\" is included in the set ", true, p.contains("sugar"));
    }

    @Test
    @Points("09-10.1 09-10.2 09-10.3")
    public void noExtraVariables() {
        cleanlinessCheck(klassName, 2, "instance variables for stock and price");
    }

    /*
     *
     */
    private int price(Object object, String product) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "price", String.class);
            return ReflectionUtils.invokeMethod(int.class, method, object, product);
        } catch (Throwable t) {
            throw t;
        }
    }

    private int stock(Object object, String product) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "stock", String.class);
            return ReflectionUtils.invokeMethod(int.class, method, object, product);
        } catch (Throwable t) {
            throw t;
        }
    }

    private boolean take(Object object, String product) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "take", String.class);
            return ReflectionUtils.invokeMethod(boolean.class, method, object, product);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Set<String> products(Object object) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "products");
            Set<String> res = (Set<String>) method.invoke(object);
            return res;
        } catch (Throwable t) {
            throw t;
        }
    }

    private void add(Object object, String product, int price, int stock) throws Throwable {
        try {
            Method method = ReflectionUtils.requireMethod(c, "addProduct", String.class, int.class, int.class);
            List<String> l = null;

            ReflectionUtils.invokeMethod(void.class, method, object, product, price, stock);
        } catch (Throwable t) {
            throw t;
        }
    }

    private Object newWarehouse() throws Throwable {
        try {
            c = ReflectionUtils.findClass(klassName);
            return ReflectionUtils.invokeConstructor(c.getConstructor());
        } catch (Throwable t) {
            fail("For the " + klassName + " class, implement a public constructor with no parameters.");
        }
        return null;
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

    private String fieldName(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
