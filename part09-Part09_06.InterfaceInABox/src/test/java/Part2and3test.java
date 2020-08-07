
import java.util.Arrays;
import java.lang.reflect.Constructor;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.*;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class Part2and3test<_Box, _Packable, _Book, _CD> {

    static final String bookName = "Book";
    static final String cdName = "CD";
    static final String boxName = "Box";
    static final String packableName = "Packable";
    private Class packableClass;
    String klassName = "Box";
    Reflex.ClassRef<_Box> klass;
    Reflex.ClassRef<_CD> _CDRef;
    Reflex.ClassRef<_Book> _BookRef;
    Reflex.ClassRef<_Packable> _PackableRef;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
        _CDRef = Reflex.reflect(cdName);
        _BookRef = Reflex.reflect(bookName);
        _PackableRef = Reflex.reflect(packableName);

        try {
            packableClass = ReflectionUtils.findClass(packableName);
        } catch (Exception e) {
        }
    }

    public _CD makeCD(String artistName, String name, int publicationYear) throws Throwable {
        return _CDRef.constructor().taking(String.class, String.class, int.class).withNiceError().invoke(artistName, name, publicationYear);
    }

    public _Book makeBook(String author, String name, double weight) throws Throwable {
        return _BookRef.constructor().taking(String.class, String.class, double.class).withNiceError().invoke(author, name, weight);
    }

    public _Box makeBox(double maximumCapacity) throws Throwable {
        return klass.constructor().taking(double.class).withNiceError().invoke(maximumCapacity);
    }

    @Test
    @Points("09-06.2")
    public void classIsPublic() {
        assertTrue("Class " + klassName + " must be public, declared \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("09-06.2 09-06.3")
    public void noUnnecessaryVariables() {
        sanitaryCheck(klassName, 3, "Object variables for packable and the maximum capacity");
    }

    @Test
    @Points("09-06.2")
    public void testBoxConstructor() throws Throwable {
        Reflex.MethodRef1<_Box, _Box, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Declare in class " + klassName + " public constructor: public " + klassName + "(double maximumCapacity)", ctor.isPublic());
        String errormessage = "Error caused by Box box = new Box(10.0);";
        ctor.withNiceError(errormessage).invoke(10.0);
    }

    @Test
    @Points("09-06.2")
    public void testAdd1() throws Throwable {
        _Box box = makeBox(10);
        _CD cd = makeCD("Pink Floyd", "Dark side of the moon", 1972);
        _Packable packable = (_Packable) cd;

        String errormessage = "\nBox box = new Box(10.0); \n"
                + "Packable cd = new CD(\"Pink Floyd\", \"Dark side of the moon\";)\n"
                + "box.add(cd);";

        assertTrue("Class Box must have method public void add(Packable item)",
                klass.method(box, "add").returningVoid().taking(_PackableRef.cls()).withNiceError(errormessage).isPublic());

        klass.method(box, "add").returningVoid().taking(_PackableRef.cls()).withNiceError(errormessage).invoke(packable);
    }

    @Test
    @Points("09-06.2")
    public void testAdd2() throws Throwable {
        _Box box = makeBox(10);
        _Book Book = makeBook("Dostojevski", "Crime and punishment", 1);
        _Packable packable = (_Packable) Book;

        String errormessage = "\nBox box = new Box(10.0); \n"
                + "Packable cd = new CD(\"Pink Floyd\", \"Dark side of the moon\";)\n"
                + "box.add(cd);";

        assertTrue("Class Box must have method public void add(Packable item)",
                klass.method(box, "add").returningVoid().taking(_PackableRef.cls()).withNiceError(errormessage).isPublic());

        klass.method(box, "add").returningVoid().taking(_PackableRef.cls()).withNiceError(errormessage).invoke(packable);
    }

    public Object mk(double weight) throws Throwable {
        Class bookClass = ReflectionUtils.findClass(bookName);
        Constructor c = ReflectionUtils.requireConstructor(bookClass, String.class, String.class, double.class);
        return ReflectionUtils.invokeConstructor(c, "ISO", "BOOK", weight);
    }

    @Test
    @Points("09-06.2")
    public void boxWorks() throws Throwable {
        Class boxClass = ReflectionUtils.findClass(boxName);
        Constructor boxConstructor = ReflectionUtils.requireConstructor(boxClass, double.class);
        Method addMethod = ReflectionUtils.requireMethod(boxClass, "add", packableClass);
        Object boxObject = ReflectionUtils.invokeConstructor(boxConstructor, 10.0);

        ReflectionUtils.invokeMethod(Void.TYPE, addMethod, boxObject, mk(3));

        assertFalse("Define in class Box toString-method",boxObject.toString().contains("@"));

        assertEquals("Make sure that the class " + boxName + " method toString works correctly!",
                "Box: 1 items, total weight 3.0 kg",
                boxObject.toString());

        ReflectionUtils.invokeMethod(Void.TYPE, addMethod, boxObject, mk(4));

        assertEquals("Make sure that the class " + boxName + " method toString works correctly!",
                "Box: 2 items, total weight 7.0 kg",
                boxObject.toString());

        ReflectionUtils.invokeMethod(Void.TYPE, addMethod, boxObject, mk(4));

        assertEquals("Make sure that too heavy items cannot be added to a box!",
                "Box: 2 items, total weight 7.0 kg",
                boxObject.toString());
    }

    @Test
    @Points("09-06.3")
    public void weightMethod() throws Throwable {
        _Box box = makeBox(10);

        String errormessage = "\nBox box = new Box(10.0); \n"
                + "box.weight);";

        assertTrue("Class Box must have method public double weight()",
                klass.method(box, "weight").returning(double.class).takingNoParams().withNiceError(errormessage).isPublic());

        klass.method(box, "weight").returning(double.class).takingNoParams().withNiceError(errormessage).invoke();
    }

    @Test
    @Points("09-06.3")
    public void weightMethodWorks() throws Throwable {
        Object boxObject = Reflex.reflect(boxName).constructor().taking(double.class).invoke(10.0);
        MethodRef0<Object, Double> weight = Reflex.reflect(boxName).method("weight").returning(double.class).takingNoParams();
        ClassRef packableClass = Reflex.reflect(packableName);
        MethodRef1 add = Reflex.reflect(boxName).method("add").returningVoid().taking(packableClass.getReferencedClass());

        double eps = 0.001;

        assertEquals("Empty box should have weight 0!",
                0,
                weight.invokeOn(boxObject),
                eps);

        add.invokeOn(boxObject, mk(5));

        assertEquals("Weight should increase when an item is added to a box!",
                5,
                weight.invokeOn(boxObject),
                eps);

        add.invokeOn(boxObject, mk(0.5));

        assertEquals("Weight should increase when an item is added to a box!",
                5.5,
                weight.invokeOn(boxObject),
                eps);

        add.invokeOn(boxObject, mk(1000));

        assertEquals("Weight should not increase when too heavy an item is added!",
                5.5,
                weight.invokeOn(boxObject),
                eps);
    }

    @Test
    @Points("09-06.4")
    public void boxImplementsPackable() {
        Class packable = Reflex.reflect(packableName).getReferencedClass();
        Class box = Reflex.reflect(boxName).getReferencedClass();

        Class is[] = box.getInterfaces();
        Class[] correct = {packable};

        assertTrue("Make sure that the class " + boxName + " implements (only!) interface Packable",
                Arrays.equals(is, correct));
    }

    @Test
    @Points("09-06.4")
    public void boxUsesMethodToCalculateWeight() throws Throwable {
        Object boxObject = Reflex.reflect(boxName).constructor().taking(double.class).invoke(10.0);
        Object box = Reflex.reflect(boxName).constructor().taking(double.class).invoke(20.0);
        MethodRef0<Object, Double> weight = Reflex.reflect(boxName).method("weight").returning(double.class).takingNoParams();
        ClassRef packable = Reflex.reflect(packableName);
        MethodRef1 add = Reflex.reflect(boxName).method("add").returningVoid().taking(packable.getReferencedClass());

        double eps = 0.001;
        add.invokeOn(box, boxObject);

        assertEquals("Weight of an empty box should be 0!",
                0,
                weight.invokeOn(boxObject),
                eps);

        add.invokeOn(boxObject, mk(5));

        assertEquals("Weight should increase as new items are added to a box! Check code\n"
                + "Box box = new Box(10); "
                + "box.add( new Book(\"Horstman\", \"Core Java\",5) );\n"
                + "box.weight();\n",
                5,
                weight.invokeOn(boxObject),
                eps);

        assertEquals("Weight should increase as new items are added to a box!\n"
                + "Box bigBox = new Box(20); \n"
                + "Box box = new Box(10); \n"
                + "bigBox.add(box);\n"
                + "box.add( new Book(\"Horstman\", \"Core Java\",5) );\n"
                + "bigBox.weight();\n",
                5,
                weight.invokeOn(box),
                eps);

        add.invokeOn(boxObject, mk(0.5));

        assertEquals("Weight should increase as new items are added to a box!\n"
                + "Box box = new Box(10); "
                + "box.add( new Book(\"Horstman\", \"Core Java\",5) );\n"
                + "box.add( new Book(\"Beck\", \"Test Driven Development\",0.5) );\n"
                + "box.weight();\n",
                5.5,
                weight.invokeOn(boxObject),
                eps);

        assertEquals("Weight should increase as new items are added to a box!\n"
                + "Box bigBox = new Box(20); \n"
                + "Box box = new Box(10); \n"
                + "bigBox.add(box);\n"
                + "box.add( new Book(\"Horstman\", \"Core Java\",5) );\n"
                + "box.add( new Book(\"Beck\", \"Test Driven Development\",0.5) );\n"
                + "bigBox.weight();\n",
                5.5,
                weight.invokeOn(box),
                eps);


        add.invokeOn(boxObject, mk(1000));

        assertEquals("Weight should not increase if too heavy item is added to a box!\n"
                + "Box box = new Box(10); "
                + "box.add( new Book(\"Horstman\", \"Core Java\",5) );\n"
                + "box.add( new Book(\"Beck\", \"Test Driven Development\",0.5) );\n"
                + "box.add( new Book(\"Nietzsche\", \"Also spracht Zarahustra\",1000) );\n"
                + "box.weight();\n",
                5.5,
                weight.invokeOn(boxObject),
                eps);
    }

    private void sanitaryCheck(String klassName, int numberOfVariables, String errorMessage) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need \"static variable\", remove from class " + klassName + " variables " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all object variables of a class must be private, but from  class " + klassName + " had: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("You do not need for" + klassName + "-class anything else than " + errorMessage + ", remove extras", var <= numberOfVariables);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
