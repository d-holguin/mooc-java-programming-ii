package dictionary;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SaveableDictionaryTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "dictionary.SaveableDictionary";

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
        createFile();
    }

    @Points("11-13.1")
    @Test
    public void classIsPublic() {
        assertTrue("The class " + s(klassName) + " should be public: \npackage dictionary;\npublic class " + s(klassName) + " {...\n}", klass.isPublic());
    }

    @Points("11-13.1")
    @Test
    public void noExtraVariables() {
        sanitezationCheck(klassName, 10, "");
    }

    @Test
    @Points("11-13.1")
    public void tyhjaKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Please define the class " + s(klassName) + " a public constructor: public " + klassName + "()", ctor.isPublic());
        String v = "the error was caused by running the code: new SaveableDictionary();";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("11-13.1")
    public void addMethod() throws Throwable {
        String method = "add";

        Object object = SaveableDictionaryTest.this.create();

        assertTrue("Please add the method 'public void " + method + "(String word, String translation) ' for the class" + s(klassName),
                klass.method(object, method)
                        .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nThe error was caused by running the code:\n"
                + "SaveableDictionary s = new SaveableDictionary();\n"
                + "s.add(\"apina\",\"monkey\");\n";

        klass.method(object, method)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkey");
    }

    @Test
    @Points("11-13.1")
    public void translateMethod() throws Throwable {
        String method = "translate";

        Object object = SaveableDictionaryTest.this.create();

        assertTrue("Please create the method 'public String " + method + "(String word)' for the class " + s(klassName),
                klass.method(object, method)
                        .returning(String.class)
                        .taking(String.class)
                        .isPublic());

        String v = "\nThe error was caused by running the code: \n"
                + "SaveableDictionary s = new SaveableDictionary();\n"
                + "s.translate(\"apina\");\n";

        klass.method(object, method)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("apina");
    }

    @Test
    @Points("11-13.1")
    public void addAndTranslateAreWorking() throws Throwable {
        String v = "\n"
                + "SaveableDictionary s = new SaveableDictionary();\n"
                + "s.add(\"apina\", \"monkey\");\n"
                + "s.add(\"tietokone\", \"computer\");\n";

        Object o = SaveableDictionaryTest.this.create();
        add(o, "apina", "monkey", v);
        add(o, "tietokone", "computer", v);

        String w = v + "s.translate(\"apina\");\n";
        assertEquals(w, "monkey", translate(o, "apina", w));
        w = v + "s.translate(\"tietokone\");\n";
        assertEquals(w, "computer", translate(o, "tietokone", w));
        w = v + "s.translate(\"monkey\");\n";
        assertEquals(w, "apina", translate(o, "monkey", w));
        w = v + "s.translate(\"computer\");\n";
        assertEquals(w, "tietokone", translate(o, "computer", w));

        w = v + "s.translate(\"ihminen\");\n";
        assertEquals(w, null, translate(o, "ihminen", w));

        add(o, "apina", "apfe", v);
        w = v + "s.add(\"apina\", \"apfe\");\n "
                + "s.translate(\"apina\");\n";
        assertEquals(w, "monkey", translate(o, "apina", w));
    }

    /*
     *
     */
    @Test
    @Points("11-13.2")
    public void deleteMethod() throws Throwable {
        String method = "delete";

        Object object = SaveableDictionaryTest.this.create();

        assertTrue("tee luokalle " + s(klassName) + " method public void " + method + "(String word) ",
                klass.method(object, method)
                        .returningVoid()
                        .taking(String.class)
                        .isPublic());

        String v = "\nThe error was caused by running the code: \n"
                + "SaveableDictionary s = new SaveableDictionary();\n"
                + "s.add(\"apina\", \"monkey\");\n"
                + "s.delete(\"apina\");\n";

        add(object, "apina", "monkey", v);

        klass.method(object, method)
                .returningVoid().taking(String.class).withNiceError(v).invoke("apina");
    }

    @Test
    @Points("11-13.2")
    public void deletionWorks() throws Throwable {
        String v = "\n"
                + "SaveableDictionary s = new SaveableDictionary();\n"
                + "s.add(\"apina\", \"monkey\");\n"
                + "s.add(\"tietokone\", \"computer\");\n"
                + "s.delete(\"apina\");\n";

        Object o = SaveableDictionaryTest.this.create();
        add(o, "apina", "monkey", v);
        add(o, "tietokone", "computer", v);

        delete(o, "apina", v);

        String w = v + "s.translate(\"apina\");\n";
        assertEquals(w, null, translate(o, "apina", w));
        w = v + "s.translate(\"tietokone\");\n";
        assertEquals(w, "computer", translate(o, "tietokone", w));
        w = v + "s.translate(\"monkey\");\n";
        assertEquals(w, null, translate(o, "monkey", w));
        w = v + "s.translate(\"computer\");\n";
        assertEquals(w, "tietokone", translate(o, "computer", w));

        w = v + "s.translate(\"ihminen\");\n";
        assertEquals(w, null, translate(o, "ihminen", w));

        add(o, "apina", "apfe", v);
        w = v + "s.add(\"apina\", \"apfe\");\n "
                + "s.translate(\"apina\n);\n";
        assertEquals(w, "apfe", translate(o, "apina", w));
    }

    /*
     *
     */
    @Test
    @Points("11-13.3")
    public void ConstructorWithParameterExists() throws Throwable {
        Reflex.MethodRef1<Object, Object, String> ctor = klass.constructor().taking(String.class).withNiceError();
        assertTrue("Please add the class " + s(klassName) + " a public costructor: public " + s(klassName) + "(String file)", ctor.isPublic());
        String v = "The error was caused by running the code: new SaveableDictionary(\"word.txt\");";
        ctor.withNiceError(v).invoke("word.txt");
    }

    @Test
    @Points("11-13.3")
    public void methodload() throws Throwable {
        String method = "load";

        String v = "SaveableDictionary s = new SaveableDictionary(\"word.txt\");\n";

        Object object = create("word.txt", v);

        assertTrue("Please add the class " + s(klassName) + " the method: public boolean " + method
                + "() ",
                klass.method(object, method)
                        .returning(boolean.class)
                        .takingNoParams()
                        .isPublic());

        Class[] e = klass.method(object, method)
                .returning(boolean.class)
                .takingNoParams().getMethod().getExceptionTypes();

        assertFalse("The method: " + method + "() of the class" + s(klassName)
                + " should not throw an exception!\n"
                + "", e.length > 0);

        v = ""
                + "SaveableDictionary s = new SaveableDictionary(\"word.txt\");\n"
                + "s.load();\n";

        assertEquals(v, true, (boolean) klass.method(object, method)
                .returning(boolean.class).takingNoParams()
                .withNiceError("\nThe error was caused by running the code:" + v).invoke());
    }

    @Test
    @Points("11-13.3")
    public void loadedDictionaryWorks() throws Throwable {

        String v = ""
                + "SaveableDictionary s = new SaveableDictionary(\"word.txt\");\n"
                + "s.load();\n";

        Object o = create("word.txt", v);

        assertEquals(v, true, load(o, v));

        String w = v + "s.translate(\"apina\");\n";
        assertEquals(w, "monkey", translate(o, "apina", w));
        w = v + "s.translate(\"alla oleva\");\n";
        assertEquals(w, "below", translate(o, "alla oleva", w));
        w = v + "s.translate(\"monkey\");\n";
        assertEquals(w, "apina", translate(o, "monkey", w));
        w = v + "s.translate(\"below\");\n";
        assertEquals(w, "alla oleva", translate(o, "below", w));
        w = v + "s.translate(\"ihminen\");\n";
        assertEquals(w, null, translate(o, "ihminen", w));

        v += "s.add(\"ohjelmointi\", \"programming\");\n";

        add(o, "ohjelmointi", "programming", v);

        w = v + "s.translate(\"ohjelmointi\");\n";
        assertEquals(v, "programming", translate(o, "ohjelmointi", v));
        w = v + "s.translate(\"programming\");\n";
        assertEquals(v, "ohjelmointi", translate(o, "programming", v));

        v += "s.delete(\"olut\")\n";

        delete(o, "olut", v);
        w = v + "s.translate(\"below\");\n";
        assertEquals(v, "alla oleva", translate(o, "below", v));
        w = v + "s.translate(\"beer\");\n";
        assertEquals(v, null, translate(o, "beer", v));
        w = v + "s.translate(\"olut\");\n";
        assertEquals(v, null, translate(o, "olut", v));
    }

    @Test
    @Points("11-13.3")
    public void eiLadataKuinVastaMethodssa() throws Throwable {

        String v = "Please note that no words are yet loaded: no words should be found!\n"
                + "SaveableDictionary s = new SaveableDictionary(\"word.txt\");\n";

        Object o = create("word.txt", v);

        String w = v + "s.translate(\"apina\");\n";
        assertEquals(w, null, translate(o, "apina", w));
        w = v + "s.translate(\"alla oleva\");\n";
        assertEquals(w, null, translate(o, "alla oleva", w));
        w = v + "s.translate(\"monkey\");\n";
        assertEquals(w, null, translate(o, "monkey", w));
        w = v + "s.translate(\"below\");\n";
        assertEquals(w, null, translate(o, "below", w));
        w = v + "s.translate(\"ihminen\");\n";
        assertEquals(w, null, translate(o, "ihminen", w));
    }

    @Points("11-13.3")
    public void nonexistentDictionaryFile() throws Throwable {
        String v = ""
                + "SaveableDictionary s = new SaveableDictionary(\"fileThatDoesNotExist.txt\");\n"
                + "s.load();\n";

        Object o = create("fileThatDoesNotExist.txt", v);

        assertEquals(v, true, load(o, v));

        String w = v + "s.translate(\"apina\");\n";
        assertEquals(w, null, translate(o, "apina", w));
        w = v + "s.translate(\"alla oleva\");\n";
        assertEquals(w, null, translate(o, "alla oleva", w));
        w = v + "s.translate(\"monkey\");\n";
        assertEquals(w, null, translate(o, "monkey", w));
        w = v + "s.translate(\"below\");\n";
        assertEquals(w, null, translate(o, "below", w));
        w = v + "s.translate(\"ihminen\");\n";
        assertEquals(w, null, translate(o, "ihminen", w));

        v += "s.add(\"apina\", \"monkey\");\n";

        add(o, "apina", "monkey", v);
        add(o, "tietokone", "computer", v);

        w = v + "s.translate(\"apina\");\n";
        assertEquals(w, "monkey", translate(o, "apina", w));

    }

    /*
     *
     */
    @Test
    @Points("11-13.4")
    public void methodsave() throws Throwable {
        String method = "save";

        String v = "SaveableDictionary s = new SaveableDictionary(\"word.txt\");\n";

        Object object = create("word.txt", v);

        assertTrue("Create the method public boolean" + method+ "() for the class" + s(klassName),
                klass.method(object, method)
                        .returning(boolean.class)
                        .takingNoParams()
                        .isPublic());

        Class[] e = klass.method(object, method)
                .returning(boolean.class)
                .takingNoParams().getMethod().getExceptionTypes();

        assertFalse("The classes " + s(klassName) + " method public boolean " + method
                + "() Should not throw an exeption!\n"
                + "", e.length > 0);

        v = ""
                + "SaveableDictionary s = new SaveableDictionary(\"word.txt\");\n"
                + "s.load();\n"
                + "s.save();\n";

        assertEquals(v, true, (boolean) klass.method(object, method)
                .returning(boolean.class).takingNoParams()
                .withNiceError("\nThe error was caused by running the code:" + v).invoke());
    }

    @Test
    @Points("11-13.4")
    public void dictionarySavesIfTheFileDoesNotYetExist() throws Throwable {
        String file = createName();

        String v = ""
                + "SaveableDictionary s = new SaveableDictionary(\"" + file + "\");\n"
                + "s.add(\"tietokone\", \"computer\");\n"
                + "s.save();\n";

        Object o = create(file, v);
        add(o, "tietokone", "computer", v);
        assertEquals(v, true, save(o, v));

        File f = new File(file);
        assertTrue("Seuraavan koodin pitäisi tallentaa dictionary fileon " + file + "\n"
                + v + "\n"
                + "filea ei luotu!", f.exists() && f.canRead());

        List<String> sisalto = read(file);

        assertEquals("Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "rivien määrä ei ollut oikea", 1, sisalto.size());

        String rivi = sisalto.get(0).trim();

        assertTrue("Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea", rivi.equals("tietokone:computer") || rivi.equals("computer:tietokone"));
    }

    @Test
    @Points("11-13.4")
    public void olemassaolevassadictionaryfilessaSailyvatSamatTiedot() throws Throwable {
        String file = "word.txt";

        String v = ""
                + "SaveableDictionary s = new SaveableDictionary(\"" + file + "\");\n"
                + "s.load();\n"
                + "s.translate(\"olut\");\n"
                + "s.save();\n";

        createFile(file);

        Object o = create(file, v);
        load(o, v);

        translate(o, "olut", v);

        assertEquals(v, true, save(o, v));
        List<String> sisalto = read(file);

        assertEquals("Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "rivien määrä ei ollut oikea", 3, sisalto.size());

        assertTrue("Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä olut --> beer was not found", canBeFound(sisalto, "olut", "beer"));

        assertTrue("Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "the contents were incorrect becouse --> monkey was not found", canBeFound(sisalto, "apina", "monkey"));

        assertTrue("Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(sisalto) + "--\n"
                + "sisältö ei ollut oikea sillä alla oleva --> below was not found", canBeFound(sisalto, "alla oleva", "below"));
    }

    @Test
    @Points("11-13.4")
    public void changesToTheExistingFileGetSaved() throws Throwable {
        int luku = new Random().nextInt(10000) + 50;
        String file = "word-" + luku + ".txt";

        String data = "When the file's " + file + " contents are:\n"
                + "apina:monkey\n"
                + "alla oleva:below\n"
                + "olut:beer\n\n";
        String v = "SaveableDictionary s = new SaveableDictionary(\"" + file + "\");\n"
                + "s.load();\n"
                + "s.delete(\"below\");\n"
                + "s.add(\"tieokone\", \"computer\");\n"
                + "s.save();\n";

        createFile(file);

        Object o = create(file, v);

        load(o, v);

        delete(o, "below", v);
        add(o, "tietokone", "computer", v);

        assertEquals(v, true, save(o, v));
        List<String> content = read(file);
        
        try {
            new File(file).delete();
        } catch (Exception e) {
            
        }

        assertEquals(data + "Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(content) + "--\n"
                + "the amount of lines was incorrect", 3, content.size());

        assertTrue(data + "Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(content) + "--\n"
                + "the contents were incorrect becouse olut --> beer was not found", canBeFound(content, "olut", "beer"));

        assertTrue(data + "Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(content) + "--\n"
                + "the contents were incorrect becouse --> monkey was not found", canBeFound(content, "apina", "monkey"));

        assertTrue(data + "Saved with the code\n" + v + "the following was saved to a file\n"
                + "--\n" + flatten(content) + "--\n"
                + "the contents were incorrect becouse tietokone --> computer was not found", canBeFound(content, "tietokone", "computer"));
    }

    /*
     *
     */
    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    public Object create(String t, String v) throws Throwable {
        Reflex.MethodRef1<Object, Object, String> ctor = klass.constructor().taking(String.class).withNiceError();
        return ctor.withNiceError(v).invoke(t);
    }

    private void add(Object o, String s, String w, String v) throws Throwable {
        klass.method(o, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke(s, w);
    }

    private String translate(Object o, String s, String v) throws Throwable {
        return klass.method(o, "translate")
                .returning(String.class).taking(String.class).withNiceError(v).invoke(s);
    }

    private boolean load(Object o, String v) throws Throwable {
        return klass.method(o, "load")
                .returning(boolean.class).takingNoParams().withNiceError(v).invoke();
    }

    private boolean save(Object o, String v) throws Throwable {
        return klass.method(o, "save")
                .returning(boolean.class).takingNoParams().withNiceError(v).invoke();
    }

    private void delete(Object o, String s, String v) throws Throwable {
        klass.method(o, "delete")
                .returningVoid().taking(String.class).withNiceError(v).invoke(s);
    }

    /*
     *
     */
    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private void sanitezationCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You shouldn't need \"static variables\", please delete from the class " + s(klassName) + " the variable " + toField(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the classes variables should be private, but from the class " + s(klassName) + " a non-private variable was found: " + toField(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + s(klassName) + " shouldn't need other variables than " + m + ", please work around the extra variables", var <= n);
        }
    }

    private String toField(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "").replace("java.io.", "");
    }

    private void createFile() {
        createFile("word.txt");
    }

    private void createFile(String file) {

        try {
            FileWriter writer = new FileWriter(file);
            writer.write("apina:monkey\n");
            writer.write("alla oleva:below\n");
            writer.write("olut:beer\n");
            writer.close();
        } catch (Exception e) {
            fail("Creating the testfile " + file + " resulted in an error.\nTry running this your self:\nFileWriter writer = new FileWriter(\"" + file + "\");\n"
                    + "writer.write(\"apina:monkey\\n\");\n"
                    + "writer.write(\"alla oleva:below\\n\");\n"
                    + "writer.write(\"olut:beer\\n\");\n"
                    + "writer.close();");
        }
    }

    private List<String> read(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        ArrayList<String> lines = new ArrayList<String>();

        while (s.hasNextLine()) {
            lines.add(s.nextLine());
        }
        return lines;
    }

    private String createName() {
        Random rnd = new Random();
        int rndD = rnd.nextInt(100000);
        return "test-" + rndD + ".txt";
    }

    private String flatten(List<String> s) {
        String t = "";
        for (String string : s) {
            t += string + "\n";
        }
        return t;
    }

    private boolean canBeFound(List<String> list, String s, String w) {
        for (String rivi : list) {
            if (rivi.equals(s + ":" + w)) {
                return true;
            }
            if (rivi.equals(w + ":" + s)) {
                return true;
            }
        }

        return false;
    }
}
