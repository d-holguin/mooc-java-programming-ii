package application;

import application.GreeterApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.stage.Stage;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import org.testfx.matcher.control.LabeledMatchers;

@Points("13-10")
public class GreeterApplicationTest extends ApplicationTest {

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
        GreeterApplication application = new GreeterApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that GreeterApplication inherits the class Application.");
        }

        try {
            Reflex.reflect(GreeterApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the class GreeterApplication has a method called start that receives a Stage object as its parameter. If this is the case, ensure that the method works correctly. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void welcomeAda() {
        check("Ada Lovelace");
    }

    @Test
    public void welcomeBilba() {
        check("Bilba Labingi");
    }

    private void check(String name) {
        clickOn(".text-field").write(name);
        clickOn(".button");
        verifyThat(".label", LabeledMatchers.hasText("Welcome " + name + "!"));

        assertFalse(lookup(".text-field").tryQuery().isPresent());
        assertFalse(lookup(".button").tryQuery().isPresent());
    }
}
