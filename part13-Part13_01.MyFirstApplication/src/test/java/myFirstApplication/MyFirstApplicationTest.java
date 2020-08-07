package myFirstApplication;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

public class MyFirstApplicationTest extends ApplicationTest {

    private Stage stage;

    static {
        if (Boolean.getBoolean("SERVER")) {
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        MyFirstApplication myFirstTestApplication = new MyFirstApplication();

        try {
            Application app = Application.class.cast(myFirstTestApplication);
        } catch (Throwable t) {
            fail("Make sure the class inherits the class Application");
        }

        try {
            Reflex.reflect(MyFirstApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(myFirstTestApplication, stage);
        } catch (Throwable ex) {
            fail("Make sure the class MyFirstApplication has method start, which gets a Stage object as a parameter. If it has, make sure the method works. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("13-01")
    public void titleIsCorrect() {
        assertEquals("My first application", stage.getTitle());
    }

    @Test
    @Points("13-01")
    public void windowOpens() {
        assertTrue("Make sure you call the method show() to show the window. Now the window does not open.", stage.isShowing());
    }

}
