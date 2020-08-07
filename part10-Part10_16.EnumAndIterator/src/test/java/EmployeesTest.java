
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class EmployeesTest<_Education, _Person, _Employees> {

    Reflex.ClassRef<_Education> _EducationRef;
    Reflex.ClassRef<_Person> _PersonRef;
    Reflex.ClassRef<_Employees> _EmployeesRef;

    @Before
    public void atStart() {
        try {
            _EducationRef = Reflex.reflect("Education");
            _PersonRef = Reflex.reflect("Person");
            _EmployeesRef = Reflex.reflect("Employees");
        } catch (Throwable e) {
        }
    }

    @Test
    @Points("10-16.1")
    public void hasEnumEducation() {
        String className = "Education";
        try {
            _EducationRef = Reflex.reflect(className);
        } catch (Throwable t) {
            fail("make enum Education");
        }
        assertTrue("make enum Education", _EducationRef.isPublic());
        Class c = _EducationRef.cls();
        assertTrue("make enum Education, now you might have made a regular class", c.isEnum());
    }

    @Test
    @Points("10-16.1")
    public void enumHasCorrectValues() {
        String className = "Education";
        Class c = ReflectionUtils.findClass(className);
        Object[] enumerators = c.getEnumConstants();
        assertEquals("enum Education should have the correct number of enumerators", 4, enumerators.length);

        String[] t = {"HS", "BA", "MA", "PHD"};

        for (String enumerator : t) {
            assertTrue("Enum Education should have the enumerator " + enumerator, contains(enumerator, enumerators));
        }
    }

    @Test
    @Points("10-16.2")
    public void hasClassPerson() {
        String className = "Person";
        _PersonRef = Reflex.reflect(className);
        assertTrue("make class Person", _PersonRef.isPublic());
    }

    public _Person createPerson(String name, _Education k) throws Throwable {
        return _PersonRef.constructor().taking(String.class, _EducationRef.cls()).invoke(name, k);
    }

    @Test
    @Points("10-16.2")
    public void PersonHasCorrectConstructor() throws Throwable {
        assertTrue("Make for class Person constructor public Person(String name, Education education)",
                _PersonRef.constructor().taking(String.class, _EducationRef.cls()).isPublic());

        String className = "Person";
        Class c = ReflectionUtils.findClass(className);

        _Education k =  education("PHD");

        assertEquals("Class Person must have only one constructor, now it has some other number", 1, c.getConstructors().length);

        _PersonRef.constructor().taking(String.class, _EducationRef.cls()).withNiceError("\nError caused by new Person(\"Arto\", Education.PHD); ").invoke("arto", k);
    }

    @Test
    @Points("10-16.2")
    public void PersonHasCorrectObjectVariables() {
        String className = "Person";

        Class c = ReflectionUtils.findClass(className);

        boolean k = false;
        boolean n = false;
        int fc = 0;
        for (Field f : c.getDeclaredFields()) {
            String name = f.toString();
            if (name.contains("String")) {
                n = true;
                fc++;
            } else if (name.contains("Education")) {
                k = true;
                fc++;
            } else {
                fail(k + " remove " + f.getName());
            }
            assertTrue("Class Person object variables should be private, but " + f.getName(), name.contains("private"));
        }
        assertTrue("Class Person should have String object variable", n);
        assertTrue("Class Person should have Education object variable", k);
        assertTrue("Class Person should have 2 object variables", fc == 2);
    }

    @Test
    @Points("10-16.2")
    public void PersonToString() {
        Object person = Person("Arto", "PHD");
        assertFalse("Make class Person method toString according to the exercise quidelines", person.toString().contains("@"));

        assertEquals("h = new Person(\"Arto\", Education.PHD); \nSystem.out.print(h);\n", "Arto, PHD", person.toString());
    }

    @Test
    @Points("10-16.2")
    public void PersonnToString2() {
        String[][] tt = {{"Pekka", "HS"}, {"Mikke", "BA"}, {"Thomas", "MA"}, {"Esko", "PHD"}};

        for (String[] namesAndEducations : tt) {
            Object person = Person(namesAndEducations[0], namesAndEducations[1]);

            assertEquals("h = new Person(\"" + namesAndEducations[0] + "\", Education." + namesAndEducations[1] + "); \nSystem.out.print(h);\n", namesAndEducations[0] + ", " + namesAndEducations[1], person.toString());
        }

    }

    @Test
    @Points("10-16.2")
    public void testGetEducation() {
        String methodToTest = "getEducation";
        String errorMessage = "add class Person method public Education getEducation()";

        assertTrue(errorMessage, _PersonRef.method(methodToTest).returning(_EducationRef.cls()).takingNoParams().isPublic());
    }

    @Test
    @Points("10-16.2")
    public void getEducationThrowsNoError() throws Throwable {
        _Person Person = createPerson("Pekka", education("PHD"));
        _Education education = _PersonRef.method("getEducation").returning(_EducationRef.cls()).
                takingNoParams().withNiceError("\nError caused by\nPerson h = new Person(\"Arto\", Education.PHD); \nh.getEducation();\n").invokeOn(Person);

        assertEquals("Person h = new Person(\"Arto\", Education.PHD); \nh.getEducation();\n", education("PHD"), education);
    }

    @Test
    @Points("10-16.2")
    public void getEducationWorks() throws Exception {
        String[][] tt = {{"Pekka", "HS"}, {"Mikke", "BA"}, {"Thomas", "MA"}, {"Esko", "PHD"}};

        for (String[] namesAndEducations : tt) {
            Object person = Person(namesAndEducations[0], namesAndEducations[1]);

            assertEquals("h = new Person(\"" + namesAndEducations[0] + "\", Education." + namesAndEducations[1] + "); \nh.getEducation();\n", namesAndEducations[1], getEducation(person).toString());
        }

    }

    @Test
    @Points("10-16.3")
    public void hasEmployeesClass() {
        String className = "Employees";
        _EmployeesRef = Reflex.reflect(className);
        assertTrue("make class Employees", _EmployeesRef.isPublic());
    }

    @Test
    @Points("10-16.3")
    public void employeesHasCorrectConstructor() throws Throwable {
        assertTrue("Make class Employees constructor public Employees()",
                _EmployeesRef.constructor().takingNoParams().isPublic());

        _EmployeesRef.constructor().takingNoParams().withNiceError("\nError caused by new Employees(); ").invoke();
    }

    private _Employees createEmployees() throws Throwable {
        return _EmployeesRef.constructor().takingNoParams().invoke();
    }

    @Test
    @Points("10-16.3")
    public void hasMethodAddPerson() throws Throwable {
        String methodToTest = "add";
        String errorMessage = "Make class Employees method public void add(Person personToAdd)";
        _Person person = createPerson("Arto", education("PHD"));

        _Employees tt = createEmployees();

        assertTrue(errorMessage, _EmployeesRef.method(tt, methodToTest).returningVoid().taking(_PersonRef.cls()).isPublic());
        String v = "Error caused by\n"
                + "Employees tt = new Employees();\n"
                + "tt.add( new Person(\"Arto\", education.PHD));";
        _EmployeesRef.method(tt, methodToTest).returningVoid().taking(_PersonRef.cls()).withNiceError(v).invoke(person);
    }

    private void add(_Employees tt, _Person person, String v) throws Throwable {
        _EmployeesRef.method(tt, "add").returningVoid().taking(_PersonRef.cls()).withNiceError(v).invoke(person);
    }

    private void add(_Employees tt, List h, String v) throws Throwable {
        _EmployeesRef.method(tt, "add").returningVoid().taking(List.class).withNiceError(v).invoke(h);
    }

    private void print(_Employees tt, String v) throws Throwable {
        _EmployeesRef.method(tt, "print").returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private void print(_Employees tt, _Education k, String v) throws Throwable {
        _EmployeesRef.method(tt, "print").returningVoid().taking(_EducationRef.cls()).withNiceError(v).invoke(k);
    }

    @Test
    @Points("10-16.3")
    public void hasMethodAddList() throws Throwable {
        String methodToTest = "add";
        String errorMessage = "make class Employees method public void add(List<Person> peopleToAdd)";

        _Person person = createPerson("Arto", education("PHD"));
        _Person henkilo2 = createPerson("Pekka", education("MA"));

        _Employees tt = createEmployees();

        assertTrue(errorMessage, _EmployeesRef.method(tt, methodToTest).returningVoid().taking(List.class).isPublic());
        String v = "Error caused by\n"
                + "List Persont = new ArrayList<Person>();\n"
                + "Persont.add(new Person(\"Arto\", education.PHD));\n"
                + "Persont.add(new Person(\"Pekka\", education.MA));\n"
                + "Employees tt = new Employees();\n"
                + "tt.add(Persont);";

        List h = new ArrayList();
        h.add(person);
        h.add(henkilo2);
        _EmployeesRef.method(tt, methodToTest).returningVoid().taking(List.class).withNiceError(v).invoke(h);
    }

    /*
     *
     */
    @Test
    @Points("10-16.3")
    public void hasMethodPrint() throws Throwable {
        String methodToTest = "print";
        String errorMessage = "make class Employees method public void print()";

        _Employees tt = createEmployees();
        assertTrue(errorMessage, _EmployeesRef.method(tt, methodToTest).returningVoid().takingNoParams().isPublic());
        String v = "Error caused by\nEmployees t = new Employees();\nt.print();\n";

        _EmployeesRef.method(tt, methodToTest).returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("10-16.3")
    public void printingWorks1() throws Throwable {
        String v = "Check code \n"
                + "t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); \n"
                + "t.add(h); \n"
                + "t.print(), \n"
                + "output should contain \"Arto, PHD\"\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person, v);
        EmployeesTest.this.print(tt, v);
        String out = io.getOutput();
        assertTrue(v + "output was\n" + out, out.contains(person.toString()));
    }

    @Test
    @Points("10-16.3")
    public void printingWorks2() throws Throwable {
        String v = "Check code t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); \n"
                + "t.add(h); \n"
                + "h2 = new Person(\"Pekka\", Education.HS); \n"
                + "t.add(h2); \n"
                + "t.print();\n"
                + "output should contain \"Arto, PHD\"\n";
        String v2 = "Check code \n"
                + "t = new Employees();\n"
                + "h = new Person(\"Pekka\",Education.PHD); \n"
                + "t.add(h); \n"
                + "t.print()\n"
                + "output should contain \"Pekka, HS\"\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person = createPerson("Arto", education("PHD"));
        _Person person2 = createPerson("Pekka", education("HS"));
        EmployeesTest.this.add(tt, person, v);
        EmployeesTest.this.add(tt, person2, v);
        EmployeesTest.this.print(tt, v);
        String out = io.getOutput();
        assertTrue(v + "output was\n" + out, out.contains(person.toString()));
        assertTrue(v2 + "output was \n" + out, out.contains(person2.toString()));
    }

    @Test
    @Points("10-16.3")
    public void printingWorks3() throws Throwable {
        String v = "Check code \nt = new Employees(); \n"
                + "ArrayList<Person> list = new ...; \n"
                + "list.add((\"Arto\", Education.PHD); \n"
                + "list.add(\"Pekka\", Education.HS); \n"
                + "t.add(list); \n"
                + "t.print(); \n"
                + "output should contain \"Arto, PHD\"\n"
                + "";
        String v2 = "Check code \nt = new Employees(); \n"
                + "ArrayList<Person> list = new ...; \n"
                + "list.add((\"Arto\", Education.PHD); \n"
                + "list.add(\"Pekka\", Education.HS); \n"
                + "t.add(list); \n"
                + "t.print();\n "
                + "output should contain \"Pekka, HS\"\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person = createPerson("Arto", education("PHD"));
        _Person person2 = createPerson("Pekka", education("HS"));
        ArrayList l = new ArrayList();
        l.add(person);
        l.add(person2);
        add(tt, l, v);
        EmployeesTest.this.print(tt, v);
        String out = io.getOutput();
        assertTrue(v + "output was \n" + out, out.contains(person.toString()));
        assertTrue(v2 + "output was \n" + out, out.contains(person2.toString()));

    }

    @Test
    @Points("10-16.3")
    public void noUnnecessaryObjectVariables() {
        Object tt = employees();
        String v = "Class employees requires only one object variable"
                + ", a list for Person -objects. Remove extra ones";
        assertEquals(v, 1, tt.getClass().getDeclaredFields().length);

    }

    @Test
    @Points("10-16.3")
    public void printingUsesIterator() {
        usesIterator();
    }

    /*
     *
     */
    @Test
    @Points("10-16.3")
    public void hasMethodPrintEducation() throws Throwable {
        String methodToTest = "print";
        String errorMessage = "make class Employees method public void print(Education education)";

        _Employees tt = createEmployees();
        assertTrue(errorMessage, _EmployeesRef.method(tt, methodToTest).returningVoid().taking(_EducationRef.cls()).isPublic());
        String v = "Error caused by\nEmployees t = new Employees();\nt.print(Education.HS);\n";

        _EmployeesRef.method(tt, methodToTest).returningVoid().taking(_EducationRef.cls()).withNiceError(v).invoke(education("HS"));
    }

    @Test
    @Points("10-16.3")
    public void filteredPrintingWorks() throws Throwable {
        String v = "Check education \n"
                + "t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); \n"
                + "t.add(h); t.print(Education.PHD); \n"
                + "output should contain \"Arto, PHD\"\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person, v);
        print(tt, education("PHD"), v);
        String out = io.getOutput();
        assertTrue(v + "output was\n" + out, out.contains(person.toString()));
    }

    @Test
    @Points("10-16.3")
    public void filteredPrintingWorks1b() throws Throwable {
        String v = "Check code \n"
                + "t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.BA); \n"
                + "t.add(h); \n"
                + "t.print(Education.PHD)\n "
                + "should not print anything\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person, v);
        print(tt, education("BA"), v);
        String out = io.getOutput();
        assertFalse(v + "output was\n" + out, out.contains(person.toString()));
    }

    @Test
    @Points("10-16.3")
    public void filteredPrintingUsesIterator() {
        usesIterator2();
    }

    /*
     *
     */
    @Test
    @Points("10-16.4")
    public void hasMethodFire() throws Throwable {
        String methodToTest = "fire";
        String errorMessage = "Make class Employees method public void fire(Education education)";

        _Employees tt = createEmployees();
        assertTrue(errorMessage, _EmployeesRef.method(tt, methodToTest).returningVoid().taking(_EducationRef.cls()).isPublic());
        String v = "Virheen aiheutti koodi\nEmployees t = new Employees();\n"
                + "t.fire(Education.PHD);\n";

        _EmployeesRef.method(tt, methodToTest).returningVoid().taking(_EducationRef.cls()).withNiceError(v).invoke(education("PHD"));
    }

    private void fire(_Employees tt, _Education k, String v) throws Throwable {
        _EmployeesRef.method(tt, "fire").returningVoid().taking(_EducationRef.cls()).withNiceError(v).invoke(k);

    }

    @Test
    @Points("10-16.4")
    public void firingWorks() throws Throwable {
        String v = "Check code \n"
                + "t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); \n"
                + "t.add(h); \n"
                + "t.fire(Education.PHD); \n"
                + "t.print()\n"
                + " should not print anything\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person, v);

        fire(tt, education("PHD"), v);

        EmployeesTest.this.print(tt, v);
        String out = io.getOutput();
        assertFalse(v + "output was\n" + out, out.contains(person.toString()));
    }

    @Test
    @Points("10-16.4")
    public void firingUsesIterator() {
        usesIterator3();
    }

    @Test
    @Points("10-16.4")
    public void firingWorks2() throws Throwable {
        String v = "Check code \nt = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); t.add(h); \n"
                + "h = new Person(\"Pekka\", Education.BA); t.add(h); \n"
                + "h = new Person(\"Matti\", Education.PHD); t.add(h); \n"
                + "t.fire(Education.PHD);\n t.print();\n. Only Pekka should be printed\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person1 = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person1, v);
        _Person person2 = createPerson("Pekka", education("BA"));
        EmployeesTest.this.add(tt, person2, v);
        _Person person3 = createPerson("Matti", education("PHD"));
        EmployeesTest.this.add(tt, person3, v);

        fire(tt, education("PHD"), v);

        EmployeesTest.this.print(tt, v);
        String out = io.getOutput();
        assertFalse(v + "output was\n" + out, out.contains(person1.toString()));
        assertTrue(v + "output was\n" + out, out.contains(person2.toString()));
        assertFalse(v + "output was\n" + out, out.contains(person3.toString()));
    }

    @Test
    @Points("10-16.4")
    public void firingWorks3() throws Throwable {
        String v = "Check code \n"
                + "t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); t.add(h); \n"
                + "h = new Person(\"Pekka\", Education.BA); t.add(h); \n"
                + "h = new Person(\"Matti\", Education.PHD); t.add(h); \n"
                + "t.fire(Education.MA);\n t.print();\n All should be printed\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person1 = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person1, v);
        _Person person2 = createPerson("Pekka", education("BA"));
        EmployeesTest.this.add(tt, person2, v);
        _Person person3 = createPerson("Matti", education("PHD"));
        EmployeesTest.this.add(tt, person3, v);

        fire(tt, education("MA"), v);

        EmployeesTest.this.print(tt, v);
        String out = io.getOutput();
        assertTrue(v + "output was\n" + out, out.contains(person1.toString()));
        assertTrue(v + "output was\n" + out, out.contains(person2.toString()));
        assertTrue(v + "output was\n" + out, out.contains(person3.toString()));
    }

    @Test
    @Points("10-16.4")
    public void firingWorks4() throws Throwable {
        String errorMessage = "Check code \n"
                + "t = new Employees(); \n"
                + "h = new Person(\"Arto\", Education.PHD); t.add(h); \n"
                + "h = new Person(\"Pekka\", Education.BA); t.add(h); \n"
                + "h = new Person(\"Matti\", Education.PHD); t.add(h); \n"
                + "t.fire(Education.BA);\n "
                + "t.print(); \n"
                + "Arto and Metti should be printed\n";

        MockInOut io = new MockInOut("");
        _Employees tt = createEmployees();
        _Person person1 = createPerson("Arto", education("PHD"));
        EmployeesTest.this.add(tt, person1, errorMessage);
        _Person person2 = createPerson("Pekka", education("BA"));
        EmployeesTest.this.add(tt, person2, errorMessage);
        _Person person3 = createPerson("Matti", education("PHD"));
        EmployeesTest.this.add(tt, person3, errorMessage);

        fire(tt, education("BA"), errorMessage);

        EmployeesTest.this.print(tt, errorMessage);
        String out = io.getOutput();
        assertTrue(errorMessage + "output was\n" + out, out.contains(person1.toString()));
        assertFalse(errorMessage + "output was\n" + out, out.contains(person2.toString()));
        assertTrue(errorMessage + "output was\n" + out, out.contains(person3.toString()));
    }

    /*
     *
     */
    private Object getEducation(Object object) throws Exception {
        String method = "getEducation";
        String errorMessage = "make class Person method public Education getEducation()";

        Object k = education("PHD");
        Class c = ReflectionUtils.findClass("Person");
        Method m = ReflectionUtils.requireMethod(c, k.getClass(), method);

        return m.invoke(object);
    }

    private Object employees() {
        Class c = ReflectionUtils.findClass("Employees");

        Constructor ctor = ReflectionUtils.requireConstructor(c);
        try {
            return ctor.newInstance();
        } catch (Throwable e) {
            fail("new Employees(); caused an error: " + e);
        }
        return null;
    }

    private Object Person(String n, String ktus) {
        Class c = ReflectionUtils.findClass("Person");
        String name = n;
        Object k = education(ktus);
        Constructor ctor = c.getConstructors()[0];
        try {
            return ctor.newInstance(n, k);
        } catch (Throwable e) {
            fail("new Person(\"Arto\", Education.PHD); caused an error: " + e);
        }
        return null;
    }

    private _Education education(String e) {
        String className = "Education";
        Class c = ReflectionUtils.findClass(className);
        Object[] enumerators = c.getEnumConstants();

        for (Object enumerator : enumerators) {
            if (enumerator.toString().equals(e)) {
                return (_Education) enumerator;
            }
        }

        return null;
    }

    private boolean contains(String enumerator, Object[] enumerators) {
        for (Object enumer : enumerators) {
            if (enumer.toString().equals(enumerator)) {
                return true;
            }
        }
        return false;
    }

    private void usesIterator() {
        try {
            Scanner scan = new Scanner(Paths.get("src", "main", "java", "Employees.java").toFile());
            int methodContains = 0;

            int iterator = 0;
            while (scan.hasNext()) {

                String line = scan.nextLine();

                if (line.indexOf("//") > -1) {
                    line = line.substring(0, line.indexOf("//"));
                }

                if (line.contains("void") && line.contains("print")) {
                    methodContains++;

                } else if (methodContains > 0) {
                    if (line.contains("{") && !line.contains("}")) {
                        methodContains++;
                    }

                    if (line.contains("}") && !line.contains("{")) {
                        methodContains--;
                    }

                    if (line.contains("Iterator") && line.contains("<Person>")) {
                        iterator++;
                    }
                    if (line.contains(".hasNext(")) {
                        iterator++;
                    }
                    if (line.contains(".next(")) {
                        iterator++;
                    }

                }

            }
            assertTrue("Class Employees methodn print() must  "
                    + "use an iterator, see the material for details!", iterator > 2);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void usesIterator2() {
        try {
            Scanner scan = new Scanner(Paths.get("src", "main", "java", "Employees.java").toFile());
            int methodContains = 0;

            int iterator = 0;
            while (scan.hasNext()) {

                String line = scan.nextLine();

                if (line.indexOf("//") > -1) {
                    line = line.substring(0, line.indexOf("//"));
                }

                if (line.contains("void") && line.contains("print") && line.contains("Education")) {
                    methodContains++;
                } else if (methodContains > 0) {
                    if (line.contains("{") && !line.contains("}")) {
                        methodContains++;
                    }

                    if (line.contains("}") && !line.contains("{")) {
                        methodContains--;
                    }

                    if (line.contains("Iterator") && line.contains("<Person>")) {
                        iterator++;
                    }
                    if (line.contains(".hasNext(")) {
                        iterator++;
                    }
                    if (line.contains(".next(")) {
                        iterator++;
                    }

                }

            }
            assertTrue("Class Employees method print(Education education) must use "
                    + "an iterator, check the material for details!", iterator > 2);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void usesIterator3() {
        try {
            Scanner scan = new Scanner(Paths.get("src", "main", "java", "Employees.java").toFile());
            int inMethod = 0;

            while (scan.hasNext()) {

                String line = scan.nextLine();

                if (line.indexOf("//") > -1) {
                    line = line.substring(0, line.indexOf("//"));
                }

                if (line.contains("void") && line.contains("fire")) {
                    inMethod++;
                } else if (inMethod > 0) {
                    if (line.contains("{") && !line.contains("}")) {
                        inMethod++;
                    }

                    if (line.contains("}") && !line.contains("{")) {
                        inMethod--;
                    }

                    if (line.contains("get(")) {
                        fail("Class Employee method fire() must use an iterator"
                                + ", when using an iterator, removing items from the list should not cause any problems. "
                                + "\nSee the material for details.");
                    }

                }

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
