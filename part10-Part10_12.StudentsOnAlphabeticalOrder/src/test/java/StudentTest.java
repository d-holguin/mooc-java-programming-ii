
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

@Points("10-12")
public class StudentTest {

    Class StudentClass;
    String klassName = "Student";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);

        try {
            StudentClass = ReflectionUtils.findClass(klassName);
        } catch (Throwable t) {
            fail("Please ensure that the project contains the class \"Student\".");
        }
    }

    @Test
    public void classExists() {
        assertTrue("The class " + klassName + " must be public, ie: \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void onImplementComparableStudent() {
        String name = "Student";
        Class kl;
        try {
            kl = StudentClass;
            Class is[] = kl.getInterfaces();
            Class oikein[] = {java.lang.Comparable.class};
            for (int i = 0; i < is.length; i++) {
            }
            assertTrue("Please ensure that the class " + name + " implements (only!) the interface Comparable",
                    Arrays.equals(is, oikein));

        } catch (Throwable t) {
            fail("Please ensure that the class " + name + " implements the interface \"Comparable<Student>\"");
        }
    }

    @Test
    public void CompareToMethodExists() throws Throwable {
        String method = "compareTo";

        Student pekka = new Student("Pekka");
        Student arto = new Student("Arto");

        assertTrue("Create the class " + klassName + " the method: public int " + method + "(Student toCompare)",
                klass.method(pekka, method)
                        .returning(int.class).taking(Student.class).isPublic());

        String v = "\nRunning this code caused problems:\n"
                + "Student pekka = new Student(\"Pekka\");\n"
                + "Student arto = new Student(\"Arto\");\n"
                + "pekka.compareTo(arto);";

        klass.method(pekka, method)
                .returning(int.class).taking(Student.class).withNiceError(v).invoke(arto);

        try {
            ReflectionUtils.requireMethod(StudentClass, int.class, "compareTo", Student.class);
        } catch (Throwable t) {
            fail("Please ensure that you have created the method \"public int compareTo(Student another)\"");
        }
    }

    private Object createStudent(String name) {
        return new Student(name);
    }

    private Method StudentCompareToMethod() {
        Method m = ReflectionUtils.requireMethod(StudentClass, "compareTo", Student.class);
        return m;
    }

    @Test
    public void testCompareTo() {
        try {
            Object h1 = createStudent("Ville");
            Object h2 = createStudent("Aapo");
            Method m = StudentCompareToMethod();
            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);

        } catch (Throwable ex) {

            fail("Please ensure that the class \"Student\" has the method \"public int compareTo(Student another)\".\n"
                    + "You should also ensure that the Student-class implements the interface Comparable<Student>.");
        }
    }

    @Test
    public void implementsComparable() {
        try {
            assertTrue("Your Student-class does not implement the interface Comparable!", StudentClass.getInterfaces()[0].equals(Comparable.class));
        } catch (Throwable t) {
            fail("Your Student-class does not implement the interface Comparable!");
        }
    }

    public int testaaKahta(String firstNimi, String secondNimi) {
        try {
            Object h1 = createStudent(firstNimi);
            Object h2 = createStudent(secondNimi);
            Method m = StudentCompareToMethod();

            int tulos = ReflectionUtils.invokeMethod(int.class, m, h1, h2);
            return tulos;
        } catch (Throwable ex) {

            fail("Please ensure that the class \"Student\" has the method \"public int compareTo(Student another)\".\n"
                    + "You should also ensure that the Student-class implements the interface Comparable<Student>");
            return -111;
        }
    }

    //Checks the list one student at the time
    public void onkoJarjestyksessa(List<String> list) {
        Collections.sort(list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (testaaKahta(list.get(i), (list.get(i + 1))) > 0) {
                fail("A problem occured while testing the code: \n"
                        + "Student first = new Student(\"" + list.get(i) + "\");\n"
                        + "Student second = new Student(\"" + list.get(i + 1) + "\");\n"
                        + "System.out.println(first.compareTo(second));\n"
                        + "output: " + testaaKahta(list.get(i), list.get(i + 1)));
            }
        }
    }

    public void test(String first, String second) {
        int answer = testaaKahta(first, second);
        String t = first.compareTo(second) > 0 ? "positive" : "negative";
        boolean output = first.compareTo(second) > 0 ? answer > 0 : answer < 0;

        
        assertTrue("The output should hace been a " + t + " number with the code\n"
                + "Student first = new Student(\"" + first + "\");\n"
                + "Student second = new Student(\"" + second + "\");\n"
                + "first.compareTo(second)\n"
                + "output was: " + answer, output);
    }

    @Test
    public void notInOrder() {
        String[][] words = {
            {"Pekka", "Aku"},
            {"Aku", "Aapo"},
            {"Gödel", "Dijkstra"},
            {"Jukka", "Jaana"},
            {"Arto", "Edsger"},
            {"Kalle", "Kaarle"}
        };

        for (String[] strings : words) {
            test(strings[0], strings[1]);
        }

    }

    @Test
    public void testSameNames() {
        String first = "Aku";
        String second = "Aku";
        testSame(first, second);

        first = "Aapo";
        second = "Aapo";
        testSame(first, second);

        first = "Pelle";
        second = "Pelle";
        testSame(first, second);
    }

    private void testSame(String first, String second) {
        int answer = testaaKahta(first, second);
        assertEquals("Student first = new Student(\"" + first + "\");\n"
                + "Student second = new Student(\"" + second + "\");\n"
                + "first.compareTo(second);", (int) answer, 0);
    }
}
