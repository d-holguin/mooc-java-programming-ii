package application;

import application.JokeApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.stage.Stage;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

@Points("13-11")
public class JokeApplicationTest extends ApplicationTest {

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
        JokeApplication application = new JokeApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that the class JokeApplication inherits the class Application.");
        }

        try {
            Reflex.reflect(JokeApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the class JokeApplication has a method called start that receives a Stage object as its parameter. If it does have, make sure that the method works properly. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void check() {
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("What do you call a bear with no teeth?"));
        clickOn("Joke");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("What do you call a bear with no teeth?"));
        clickOn("Answer");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("A gummy bear."));
        clickOn("Joke");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("What do you call a bear with no teeth?"));
        clickOn("Explanation");
        FxAssert.verifyThat(".label", NodeMatchers.isNotNull());
    }
}
