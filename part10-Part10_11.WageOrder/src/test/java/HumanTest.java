
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("10-11")
public class HumanTest {

    String klassName = "Human";
    Class c;
    Reflex.ClassRef<Object> klass;
    private Class HumanClass;
    private final String answer = "compareTo-method must return a positive number, if this.wage is smaller than the wage of the object at comparison.\n"
            + "When the wages are equal, the method should return the number 0.\n"
            + "When the object at comparison has a smaller wage, the method should return a negative number.\n";

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);

        try {
            HumanClass = ReflectionUtils.findClass(klassName);
        } catch (Throwable t) {
            fail("Please ensure that the class Human exists.");
        }
    }

    @Test
    public void onLuokkaHuman() {
        assertTrue("The class " + klassName + " must be public:\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void ImplementsComparableHuman() {
        String name = "Human";
        Class targetClass;
        try {
            targetClass = HumanClass;
            Class targetTable[] = targetClass.getInterfaces();
            Class right[] = {java.lang.Comparable.class};
            for (int i = 0; i < targetTable.length; i++) {
            }
            assertTrue("Please ensure tha the class " + name + " implements (only!) the interface Comparable",
                    Arrays.equals(targetTable, right));

        } catch (Throwable t) {
            fail("Please ensure that the class " + name + " implements the interface \"Comparable<Human>\"");
        }
    }

    @Test
    public void CompareToMethodExists() throws Throwable {
        String method = "compareTo";

        Human pekka = new Human("Pekka", 1600);
        Human arto = new Human("Arto", 3500);

        assertTrue("tee luokalle " + klassName + " method public int " + method + "(Human verrattava)",
                klass.method(pekka, method)
                .returning(int.class).taking(Human.class).isPublic());

        String v = "\nThe error was caused by running the code:\n"
                + "Human pekka = new Human(\"Pekka\",1600);\n"
                + "Human arto = new Human(\"Arto\",3500);\n"
                + "pekka.compareTo(arto);";

        klass.method(pekka, method)
                .returning(int.class).taking(Human.class).withNiceError(v).invoke(arto);
    }

    private Object createHuman(String name, int wage) {
        try {
            Constructor HumanConst = ReflectionUtils.requireConstructor(HumanClass, String.class, int.class);
            Object HumanObject = ReflectionUtils.invokeConstructor(HumanConst, name, wage);
            return HumanObject;
        } catch (Throwable t) {
            fail("Please ensure that the class \"Human\" exists");
            return null;
        }
    }

    private Method HumanCompareToMethod() {
        Method m = ReflectionUtils.requireMethod(HumanClass, "compareTo", Human.class);
        return m;
    }

    public int testTwo(String firstName, int firstWage, String secondName, int secondWage) {
        try {
            Object henkilo1 = createHuman(firstName, firstWage);
            Object henkilo2 = createHuman(secondName, secondWage);
            Method m = HumanCompareToMethod();
//            int tulos = ReflectionUtils.invokeMethod(int.class, m, henkilo1, henkilo2);
            return ReflectionUtils.invokeMethod(int.class, m, henkilo1, henkilo2);
        } catch (Throwable ex) {

            fail("Please ensure that the class \"Human\" implements the method \"public int compareTo(Human another)\".\n"
                    + "And that it also implements the interface Comparable");
            return -111;
        }
    }

    @Test
    public void biggerWage() {
        String firstName = "Aku";
        String secondName = "Roope";
        int firstWage = 0;
        int secondWage = Integer.MAX_VALUE;
        int result = testTwo(firstName, firstWage, secondName, secondWage);

        String extraHint = ""
                + "Human first = new Human(" + firstName + ", " + firstWage + ");\n"
                + "Human second = new Human(" + secondName + ", " + secondWage + ");\n"
                + "System.out.println(first.compareTo(second));\n"
                + "output: "+result;

        if (result == -111) {
            fail("Please ensure that the class \"Human\" implements the method \"public int compareTo(Human another)\".\n"
                    + "And that it also implements the interface Comparable");
        } else {
            assertTrue(answer + "\n" + extraHint, result > 0);
        }
    }

    @Test
    public void smallerWage() {
        String firstName = "Roope";
        String secondName = "Aku";
        int firstWage = Integer.MAX_VALUE;
        int secondWage = 0;

        int result = testTwo(firstName, firstWage, secondName, secondWage);

        String extraHint = ""
                + "Human first = new Human(" + firstName + ", " + firstWage + ");\n"
                + "Human second = new Human(" + secondName + ", " + secondWage + ");\n"
                + "System.out.println(first.compareTo(second));\n"
                + "output: "+result;

        assertTrue(answer + "\n" + extraHint, result < 0);
    }

    @Test
    public void sameWages() {
        String firstName = "Hessu";
        String secondName = "Taavi";
        int firstWage = 3000;
        int secondWage = 3000;

        int result = testTwo(firstName, firstWage, secondName, secondWage);

        String extraHint = ""
                + "Human first = new Human(" + firstName + ", " + firstWage + ");\n"
                + "Human second = new Human(" + secondName + ", " + secondWage + ");\n"
                + "System.out.println(first.compareTo(second));\n"
                + "output: "+result;

        assertTrue(answer + "\n" + extraHint, result == 0);
    }

    @Test
    public void extraTests() {
        String firstName = "Hessu";
        String secondName = "Taavi";
        int firstWage = 3000;
        int secondWage = 30000;
        int answerInt = testTwo(firstName, firstWage, secondName, secondWage);

        String extraHint = ""
                + "Human first = new Human(" + firstName + ", " + firstWage + ");\n"
                + "Human second = new Human(" + secondName + ", " + secondWage + ");\n"
                + "System.out.println(first.compareTo(second));\n"
                + "output: "+answerInt;

        assertTrue("your compareTo-method answered incorrectly. When this.wage was: " + firstWage
                + ", and the compared.wage was " + secondWage + " Your compareTo-method gives " + answerInt + " as the answer.\n"
                + extraHint, answerInt > 0);


        firstWage = 0;
        secondWage = 0;
        answerInt = testTwo(firstName, firstWage, secondName, secondWage);
        assertTrue("your compareTo-method answered incorrectly. When this.wage was: " + firstWage
                + ", and the compared.wage was " + secondWage + " Your compareTo-method gives " + answerInt + " as the answer.\n"
                + extraHint, answerInt == 0);

        firstWage = 300;
        secondWage = 10;
        answerInt = testTwo(firstName, firstWage, secondName, secondWage);
        assertTrue("your compareTo-method answered incorrectly. When this.wage was: " + firstWage
                + ", and the compared.wage was " + secondWage + " Your compareTo-method gives " + answerInt + " as the answer.\n"
                + extraHint, answerInt < 0);

    }
}
