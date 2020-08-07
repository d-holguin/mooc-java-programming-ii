
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.junit.*;
import static org.junit.Assert.*;

public class DifferentKindsOfBoxesTest {

    Class boxWithMaxWeight;
    Class oneItemBox;
    Class MisplacingBox;

    @Before
    public void setUp() {
        try {
            boxWithMaxWeight = ReflectionUtils.findClass("BoxWithMaxWeight");
        } catch (Throwable t) {
        }
        try {
            oneItemBox = ReflectionUtils.findClass("OneItemBox");
        } catch (Throwable t) {
        }
        try {
            MisplacingBox = ReflectionUtils.findClass("MisplacingBox");
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("09-04.1")
    public void itemContructorDoesNotThrowExceptionIfWeightIs0k() {
        try {
            Item item = new Item("Hesse", 0);
        } catch (Throwable t) {
            Assert.fail("Exception encountered when executing program.\n"
                    + "Test with code new Item(\"feather\",0);");
        }
    }

    @Test
    @Points("09-04.1")
    public void equalsMethodImplemented() {
        Item item = new Item("Hesse", 5);
        Assert.assertEquals("Make sure you implemented the equals method for the Item-class so that the items being compared must have the same name, but their weigh is ignored.\n"
                + "Try testing with the following code:\n"
                + "Item first = new Item(\"Rock\", 5);\n"
                + "first.equals( new Item(\"Rock\", 1) );\n", true, item.equals(new Item("Hesse")));
        Assert.assertEquals("Make sure you implemented the equals method for the Item-class so that the items being compared must have the same name, but their weigh is ignored.\n"
                + "Try testing with the following code:\n"
                + "Item first = new Item(\"Rock\", 5);\n"
                + "first.equals( new Item(\"Book\", 1) );\n", false, item.equals(new Item("Siddhartha")));
    }

    @Test
    @Points("09-04.1")
    public void hashCodeMethodImplemented() {
        Item item = new Item("Hesse", 5);
        Assert.assertEquals("Make sure you implemented the hashCode method for the Item-class so that the hashcode is derived from the name of the item and it's weight is ignored.\n"
                + "Try testing with the following code:\n"
                + "Item first = new Item(\"Rock\", 5);\n"
                + "Item second = new Item(\"Rock\", 1) );\n"
                + "first.hashCode() == second.hashCode();\n", true, item.hashCode() == new Item("Hesse").hashCode());
        Assert.assertEquals("Make sure you implemented the hashCode method for the Item-class so that the hashcode is derived from the name of the item and it's weight is ignored.\n"
                + "Try testing with the following code:\n"
                + "Item first = new Item(\"Rock\", 5);\n"
                + "Item second = new Item(\"Book\", 1) );\n"
                + "first.hashCode() == second.hashCode();\n", false, item.hashCode() == new Item("Siddhartha").hashCode());
    }

    /*
     *
     */
    @Test
    @Points("09-04.2")
    public void maxWeightBox() throws Throwable {
        Assert.assertNotNull("Make sure you implemented the class BoxWithMaxWeight.", boxWithMaxWeight);
        checkInheritance(boxWithMaxWeight);

        cleanlinessCheck("BoxWithMaxWeight", 2, " two object variables. One for "
                + "maxWeight and one for saving the boxes");

        Constructor maxWeightBoxConstructor = null;
        try {
            maxWeightBoxConstructor = ReflectionUtils.requireConstructor(boxWithMaxWeight, int.class);
        } catch (Throwable t) {
            Assert.fail("Make sure the BoxWithMaxWeight has the constructor: public BoxWithMaxWeight(int capacity).");
        }

        Box box = null;
        try {
            box = (Box) ReflectionUtils.invokeConstructor(maxWeightBoxConstructor, 5);
        } catch (Throwable ex) {
            Assert.fail("Constructor call for the class BoxWithMaxWeight failed when invoked with capacity 5. Error: " + ex.getMessage());
        }

        String v = "\n"
                + "Box box = new BoxWithMaxWeight(5);\n"
                + "box.add(new Item(\"a\", 1));\n";

        addMWB(box, new Item("a", 1), v);
        v += "box.add(new Item(\"b\", 2));\n";
        addMWB(box, new Item("b", 2), v);
        v += "box.add(new Item(\"c\", 2));\n";
        addMWB(box, new Item("c", 2), v);
        v += "box.add(new Item(\"d\", 1));\n";
        addMWB(box, new Item("d", 1), v);
        v += "box.add(new Item(\"f\", 0));\n";
        addMWB(box, new Item("f", 0), v);

        Assert.assertEquals("Make sure items actually go into the box when added.\n"
                + "Try the  following code:\n" + v
                + "box.isInBox(new Item(\"a\"))\n", true, isInBox(box, new Item("a"), v + "box.isInBox(new Item(\"a\"));\n "));
        
        Assert.assertEquals("Make sure items actually go into the box when added.\n"
                + "Try the  following code:\n" + v
                + "box.isInBox(new Item(\"b\"))\n", true, isInBox(box, new Item("b"), v + "box.isInBox(new Item(\"b\"));\n "));
        
        Assert.assertEquals("Make sure an item added to the box, goes into the box, when the total weight of the items in the box with the new item is exactly the max weight of the box."
                + "\nTry the  following code:\n" + v
                + "box.isInBox(new Item(\"c\"))\n", true, isInBox(box, new Item("c"), v + "box.isInBox(new Item(\"c\"));\n "));
        
        Assert.assertEquals("Make sure items can't be added to the box when doing so would take the boxes total weight above its max weight.\n"
                + "\nTry the  following code:\n" + v
                + "box.isInBox(new Item(\"d\"))\n", false, isInBox(box, new Item("d"), v + "box.isInBox(new Item(\"d\"));\n "));
        
        Assert.assertEquals("Make suren that even when the box is full, you should be able to add items with weight 0.\n"
                + "\nTry the  following code:\n" + v
                + "laatikko.onkoLaatikossa(new Tavara(\"f\"))\n", true, isInBox(box, new Item("f"), v + "laatikko.onkoLaatikossa(new Tavara(\"f\"));\n "));
        
        Assert.assertEquals("There aren't any extra items in the box are there?\n"
                + "Try the  following code:\n" + v
                + "box.isInBox(new Item(\"rock\"))\n", false, isInBox(box, new Item("rock"), v + "box.isInBox(new Item(\"rock\"));\n "));
    }

    private void addMWB(Object object, Item i, String s) throws Throwable {
        String klassName = "BoxWithMaxWeight";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        classRef.method(object, "add").returningVoid()
                .taking(Item.class).withNiceError(s).invoke(i);
    }

    private void addOIB(Object object, Item i, String s) throws Throwable {
        String klassName = "OneItemBox";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        classRef.method(object, "add").returningVoid()
                .taking(Item.class).withNiceError(s).invoke(i);
    }

    private void addMB(Object object, Item i, String s) throws Throwable {
        String klassName = "MisplacingBox";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        classRef.method(object, "add").returningVoid()
                .taking(Item.class).withNiceError(s).invoke(i);
    }

    private boolean isInBox(Object olio, Item t, String v) throws Throwable {
        String klassName = "BoxWithMaxWeight";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        return classRef.method(olio, "isInBox").returning(boolean.class)
                .taking(Item.class).withNiceError(v).invoke(t);
    }

    private boolean isInOIBox(Object object, Item i, String s) throws Throwable {
        String klassName = "OneItemBox";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        return classRef.method(object, "isInBox").returning(boolean.class)
                .taking(Item.class).withNiceError(s).invoke(i);
    }

    private boolean isInMBox(Object object, Item i, String s) throws Throwable {
        String klassName = "MisplacingBox";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        return classRef.method(object, "isInBox").returning(boolean.class)
                .taking(Item.class).withNiceError(s).invoke(i);
    }

    /*
     *
     */
    @Test
    @Points("09-04.3")
    public void oneItemBoxWorks() throws Throwable {
        Assert.assertNotNull("Make sure you implemented the class: OneItemBox?", oneItemBox);
        checkInheritance(oneItemBox);

        cleanlinessCheck("OneItemBox", 2, "a variable that remembers the item in the box");

        Constructor oneItemBoxConstructor = null;
        try {
            oneItemBoxConstructor = ReflectionUtils.requireConstructor(oneItemBox);
        } catch (Throwable t) {
            Assert.fail("Make sure the class OneItemBox has the constructor public OneItemBox().");
        }

        Box box = null;
        try {
            box = (Box) ReflectionUtils.invokeConstructor(oneItemBoxConstructor);
        } catch (Throwable ex) {
            Assert.fail("Constructor call for the class OneItemBox failed. Error: " + ex.getMessage());
        }

        String s = "\nBox box = new OneItemBox();\n"
                + "box.isInBox(new Item(\"a\"));";

        Assert.assertFalse("Make sure the box starts empty.\n"
                + "Try testing with the following code:\n"
                + "", isInOIBox(box, new Item("a"), s));

        s = "\n"
                + "Box box = new OneItemBox();\n"
                + "box.add(new Item(\"a\", 1));\n";

        addOIB(box, new Item("a", 1), s);
        s += "box.add(new Item(\"b\", 2));\n";
        addOIB(box, new Item("b", 2), s);
        s += "box.add(new Item(\"c\", 2));\n";
        addOIB(box, new Item("c", 2), s);

        Assert.assertEquals("Make sure adding an item to the box actually adds the item into the box.\n"
                + "Try testing with the following code:\n"
                + s + "box.isInBox(new Item(\"a\"));\n", true, isInOIBox(box, new Item("a"), s + "box.isInBox(new Item(\"a\"));"));
        Assert.assertEquals("Kun yhden tavaran laatikossa on jo tavara, sinne ei pitäisi pystyä lisäämään muita tavaroita.\n"
                + "Try testing with the following code:\n"
                + s + "box.isInBox(new Item(\"b\"));\n", false, isInOIBox(box, new Item("b"), s + "box.isInBox(new Item(\"b\"));"));
        Assert.assertEquals("When there is already an item in a OneItemBox, adding another item shouldn't replace it.\n"
                + "Try testing with the following code:\n"
                + s + "box.isInBox(new Item(\"c\"));\n", false, isInOIBox(box, new Item("c"), s + "box.isInBox(new Item(\"c\"));"));
    }

    @Test
    @Points("09-04.3")
    public void misplacingBoxWorks() throws Throwable {
        Assert.assertNotNull("Make sure you implemented the class MisplacingBox", MisplacingBox);
        checkInheritance(MisplacingBox);

        Constructor misplacingBoxConstructor = null;
        try {
            misplacingBoxConstructor = ReflectionUtils.requireConstructor(MisplacingBox);
        } catch (Throwable t) {
            Assert.fail("Make sure the MisplacingBox class has the constructor public MisplacingBox().");
        }

        Box box = null;
        try {
            box = (Box) ReflectionUtils.invokeConstructor(misplacingBoxConstructor);
        } catch (Throwable ex) {
            Assert.fail("Constructor call for the class Misplacing box failed. Error: " + ex.getMessage());
        }

        String s = "\n"
                + "Box box = new MisplacingBox();\n"
                + "box.isInBox(new Item(\"a\", 1));\n";
        Assert.assertEquals("Make sure the misplacing box misplaces any items added.\n"
                + "Try testing with the following code:\n"
                + s, false, isInMBox(box, new Item("a"), s));

        s = "\n"
                + "Box box = new MisplacingBox();\n"
                + "box.add(new Item(\"a\", 1));\n";

        addMB(box, new Item("a", 1), s);
        s += "box.add(new Item(\"b\", 2));\n";
        addMB(box, new Item("b", 1), s);

        Assert.assertEquals("Make sure the misplacing box misplaces any items added.\n"
                + "Try testing with the following code:\n"
                + s + "box.isInBox(new Item(\"a\"));", false, isInMBox(box, new Item("a"), s + "box.isInBox(new Item(\"a\"));"));

        Assert.assertEquals("Make sure the misplacing box misplaces any items added.\n"
                + "Try testing with the following code:\n"
                + s + "box.isInBox(new Item(\"b\"));", false, isInMBox(box, new Item("b"), s + "box.isInBox(new Item(\"b\"));"));

    }

    private void checkInheritance(Class clazz) {
        if (!(clazz.getSuperclass().equals(Box.class))) {
            Assert.fail("Make sure the class" + s(clazz.getName()) + " inherits the class box?");
        }
    }

    private void cleanlinessCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You don't need \"static variables\", in the " + s(klassName) + " class. Remove/change the variable " + field(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables should be private in the  " + s(klassName) + " class, but found the variable: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("For the " + s(klassName) + "-class, you only need " + m + ", remove any surplus", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        if (!klassName.contains(".")) {
            return klassName;
        }

        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
