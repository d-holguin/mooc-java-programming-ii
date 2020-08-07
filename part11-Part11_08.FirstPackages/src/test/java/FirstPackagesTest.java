
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

public class FirstPackagesTest {

    @Test
    @Points("11-08.1")
    public void uiInterfaceExists() {
        Class clazz = null;
        try {
            clazz = ReflectionUtils.findClass("mooc.ui.UserInterface");
        } catch (Throwable e) {
            fail("Create the package mooc.ui and add a class called UserInterface in it:\n\n"
                    + "public interface UserInterface{\n  \\\\...\n}");
        }

        if (clazz == null) {
            fail("Please ensure that you have created an interface to the package mooc.ui and that it is defined as public.");
        }

        if (!clazz.isInterface()) {
            fail("Please ensure that UserInterface is defined as an interface");
        }

        boolean found = false;
        for (Method m : clazz.getMethods()) {
            if (!m.getReturnType().equals(void.class)) {
                continue;
            }

            if (!m.getName().equals("update")) {
                continue;
            }

            found = true;
        }

        if (!found) {
            fail("Please ensure that the interface UserInterface has the method: void update()");
        }
    }

    /*
     *
     */
    @Test
    @Points("11-08.2")
    public void TextInterfaceExists() {
        Class clazz = ReflectionUtils.findClass("mooc.ui.TextInterface");

        if (clazz == null) {
            fail("Please ensure that you have created the class TextInterface in the package mooc.ui and that it is defined as public.");
        }

        Class ui = ReflectionUtils.findClass("mooc.ui.UserInterface");
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(ui)) {
                return;
            }
        }

        fail("Please ensure that the class TextInterface implements the interface UserInterface");
    }

    @Test
    @Points("11-08.2")
    public void TextInterfacePrints() {
        Class clazz = ReflectionUtils.findClass("mooc.ui.TextInterface");
        Object ui = createTextInterface();

        MockInOut io = new MockInOut("");

        Method m = ReflectionUtils.requireMethod(clazz, "update");
        try {
            m.invoke(ui);
        } catch (Throwable t) {
            fail("There was an error executing your code. Test what happens when you execute it yourself.\n"
                    + "UserInterface kali = new TextInterface();\n"
                    + "kali.update()\n");
        }

        Assert.assertTrue("Please ensure that the update method of the TextInterface class prints something. \n"
                + "What it printed out now:\n" + io.getOutput(), io.getOutput() != null && io.getOutput().length() > 1);
        Assert.assertTrue("The update-method of the TextInterface-class should print a newline \n"
                + "What it printed out now:\n" + io.getOutput(), io.getOutput() != null && io.getOutput().contains("\n"));
        Assert.assertTrue("The update-method of the TextInterface-class should only print one line. \n"
                + "NWhat it printed out now:\n" + io.getOutput(), io.getOutput() != null && io.getOutput().split("\n").length == 1);
    }

    /*
     *
     */
    @Test
    @Points("11-08.3")
    public void ApplicationLogicExists() {
        Class ApplicationLogicClass = ReflectionUtils.findClass("mooc.logic.ApplicationLogic");

        if (ApplicationLogicClass == null) {
            fail("Please ensure that you have created the class 'ApplicationLogic' in the package mooc.logic and that it is defined as public.");
        }
        Class uiClass = ReflectionUtils.findClass("mooc.ui.UserInterface");

        Constructor constructor = ReflectionUtils.requireConstructor(ApplicationLogicClass, uiClass);
        if (constructor == null) {
            fail("Please ensure that the class ApplicationLogic has the constructor 'public ApplicationLogic(UserInterface ui)'");
        }

        Method m = null;

        try {
            m = ReflectionUtils.requireMethod(ApplicationLogicClass, "execute", int.class);
        } catch (Throwable t) {
            fail("The ApplicationLogic-class is missing the method: public void execute(int times)");
        }

        assertTrue("The ApplicationLogic-class is missing the method: public void execute(int times)", m.toString().contains("public"));
    }

    @Test
    @Points("11-08.3")
    public void limitVariableCount() {
        sanitezationCheck("mooc.logic.ApplicationLogic", 1, "a UserInterface-type object variable");
    }

    @Test
    @Points("11-08.3")
    public void creatingApplicationLogicObject() throws IllegalArgumentException, IllegalAccessException {
        Class ApplicationLogicClass = ReflectionUtils.findClass("mooc.logic.ApplicationLogic");
        Object ApplicationLogic = createApplicationLogicObject();
        if (ApplicationLogic == null) {
            fail("Please ensure that the ApplicationLogic class has the constructor 'public ApplicationLogic(UserInterface ui)'");
        }

        Method executeMethod = ReflectionUtils.requireMethod(ApplicationLogicClass, "execute", int.class);

        Class clazz = ReflectionUtils.findClass("mooc.ui.TextInterface");
        Object kali = createTextInterface();

        MockInOut io = new MockInOut("");

        Method m = ReflectionUtils.requireMethod(clazz, "update");
        try {
            m.invoke(kali);
        } catch (Throwable t) {
            fail("There was an error executing your code. Test what happens when you execute it yourself.\n"
                    + "UserInterface ui = new TextInterface();\n"
                    + "ui.update()\n");
        }

        Assert.assertTrue("Please ensure that the update method of the TextInterface class prints something.", io.getOutput() != null && io.getOutput().length() > 5);
        String output = io.getOutput();

        Field[] fields = ReflectionUtils.findClass("mooc.logic.ApplicationLogic").getDeclaredFields();
        assertTrue("Please ensure that the ApplicationLogic-class has a UserInterface type objectvariable.", fields.length == 1);
        assertTrue("Please ensure that the ApplicationLogic-class has a UserInterface type objectvariable.", fields[0].toString().contains("UserInterface"));
        fields[0].setAccessible(true);
        assertFalse("the objectvariable of ApplicationLogic " + fields[0].toString().replace("mooc.logic.ApplicationLogic", "") + " "
                + "null. \nPlease set it to the variable passed from the appLogic's constructor!", fields[0].get(ApplicationLogic) == null);

        try {
            executeMethod.invoke(ApplicationLogic, 3);
        } catch (Throwable t) {
            fail("There was an error executing your code. Test what happens when you execute it yourself.\n"
                    + "UserInterface ui = new TextInterface();\n"
                    + "ApplicationLogic app = new ApplicationLogic(ui);\n"
                    + "app.execute(3);");
        }

        String executionOutput = io.getOutput().substring(output.length());

        assertTrue("With the code\n"
                + "UserInterface kali = new TextInterface();\n"
                + "ApplicationLogic app = new ApplicationLogic(kali);\n"
                + "app.execute(3);\n"
                + "6 lines should be printed.\nWhat was printed now: \n" + executionOutput, executionOutput.split("\n").length > 5 && executionOutput.split("\n").length < 8);

        if (executionOutput.length() < output.length() * 3) {
            fail("Please ensure that you call the UserInterface interface's update method exactly as many times as given by the variable 'times' in the ApplicationLogic's execute-methods loop construct.");
        }
    }

    @Test
    @Points("11-08.3")
    public void anotherApplication() throws IllegalArgumentException, IllegalAccessException {
        Class appLogicClass = ReflectionUtils.findClass("mooc.logic.ApplicationLogic");
        Object appLogic = createApplicationLogicObject();
        if (appLogic == null) {
            fail("Please ensure that the ApplicationLogic class has the constructor 'public ApplicationLogic(UserInterface ui)'");
        }

        Method executeMethod = ReflectionUtils.requireMethod(appLogicClass, "execute", int.class);

        Class clazz = ReflectionUtils.findClass("mooc.ui.TextInterface");
        Object kali = createTextInterface();

        MockInOut io = new MockInOut("");

        Method m = ReflectionUtils.requireMethod(clazz, "update");
        try {
            m.invoke(kali);
        } catch (Throwable t) {
            fail("There was an error executing your code. Test what happens when you execute it yourself.\n"
                    + "UserInterface ui = new TextInterface();\n"
                    + "ui.update()\n");
        }

        Assert.assertTrue("Please ensure that the update-method of the TextInterface class prints something", io.getOutput() != null && io.getOutput().length() > 5);
        String output = io.getOutput();

        Field[] fields = ReflectionUtils.findClass("mooc.logic.ApplicationLogic").getDeclaredFields();
        assertTrue("Please ensure that the ApplicationLogic class has a UserInterface type objectvariable", fields.length == 1);
        assertTrue("Please ensure that the ApplicationLogic class has a UserInterface type objectvariable", fields[0].toString().contains("ui"));
        fields[0].setAccessible(true);
        assertFalse("the ApplicationLogic's variable " + fields[0].toString().replace("mooc.logiikka.Sovelluslogiikka.", "") + " "
                + "null. \nPlease set it to the variable passed from the appLogic's constructor!", fields[0].get(appLogic) == null);

        try {
            executeMethod.invoke(appLogic, 5);
        } catch (Throwable t) {
            fail("There was an error executing your code. Test what happens when you execute it yourself.\n"
                    + "UserInterface ui = new TextInterface();\n"
                    + "ApplicationLogic app = new ApplicationLogic(ui);\n"
                    + "app.execute(3);");
        }

        String executionOutput = io.getOutput().substring(output.length());

        assertTrue("With the code:\n"
                + "UserInterface ui = new TextInterface();\n"
                + "ApplicationLogic app = new ApplicationLogic(ui);\n"
                + "app.execute(5);\n"
                + "6 lines should be printed.\nWhat was printed now: \n" + executionOutput, executionOutput.split("\n").length > 9 && executionOutput.split("\n").length < 12);
    }

    @Test
    @Points("11-08.3")
    public void applicationCallsTheUI() {
        Field[] fields = ReflectionUtils.findClass("mooc.logic.ApplicationLogic").getDeclaredFields();
        String variable = fields[0].toString();
        variable = variable.substring(variable.lastIndexOf(".") + 1);
        assertTrue("The execute method of the ApplicationLogic class must call for the update method of the UserInterface", includesCall(variable + ".update()"));
    }

    private boolean includesCall(String call) {

        try {
            Scanner scanner = new Scanner(new File("src/main/java/mooc/logic/ApplicationLogic.java"));
            int inMethod = 0;

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.indexOf("//") > -1) {
                    continue;
                }

                if (inMethod > 0 && line.contains(call)) {
                    return true;
                }

                if (line.contains("void") && line.contains("execute")) {
                    inMethod++;
                } else if (inMethod > 0) {
                    if (line.contains("{") && !line.contains("}")) {
                        inMethod++;
                    }

                    if (line.contains("}") && !line.contains("{")) {
                        inMethod--;
                    }
                }

            }

        } catch (Exception e) {
            fail(e.getMessage());
        }

        return false;
    }

    private Object createTextInterface() {
        Class clazz = ReflectionUtils.findClass("mooc.ui.TextInterface");
        Object ui = null;
        try {
            ui = ReflectionUtils.invokeConstructor(ReflectionUtils.requireConstructor(clazz));
        } catch (Throwable ex) {
        }

        if (ui == null) {
            fail("Please ensure that the class TextInterface has a constructor with 0 parameters");
        }

        return ui;
    }

    private Object createApplicationLogicObject() {
        Class ApplicationLogicClass = ReflectionUtils.findClass("mooc.logic.ApplicationLogic");

        if (ApplicationLogicClass == null) {
            fail("Please ensure that you have created the class ApplicationLogic, and that it is defined as public.");
        }
        Class uiClass = ReflectionUtils.findClass("mooc.ui.UserInterface");

        Constructor constructor = ReflectionUtils.requireConstructor(ApplicationLogicClass, uiClass);

        try {
            return ReflectionUtils.invokeConstructor(constructor, createTextInterface());
        } catch (Throwable ex) {
        }

        return null;
    }

    private void sanitezationCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field CurrentField : fields) {
            assertFalse("you dont need \"static variables\", delete the variable " + field(CurrentField.toString(), klassName) + " from the class " + klassName , CurrentField.toString().contains("static") && !CurrentField.toString().contains("final"));
            //assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + field(CurrentField.toString(), klassName), CurrentField.toString().contains("private"));
            assertTrue("all variables of the classes should be private, but the class " + klassName + " had: " + field(CurrentField.toString(), klassName) + " as a public variable.", CurrentField.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            //assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
            assertTrue("You sholdn't need more than " + m + " for the class " + klassName + ", please delete the unnecessary ones", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
