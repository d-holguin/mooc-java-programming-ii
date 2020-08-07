package borderpane;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

public class BorderPaneApplicationTest extends ApplicationTest {

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
        BorderPaneApplication application = new BorderPaneApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the BorderPaneApplication class inherits the Application class.");
        }

        try {
            Reflex.reflect(BorderPaneApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure the BorderPaneApplication class has a method called start, which gets a Stage object as its parameter. If it has, make sure the method works. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("13-04")
    public void theDesiredElementsAreFound() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a scene object. Currently the call to getScene of stage returned a null reference after the execution of the start method.", scene);
        Parent rootOfElements = scene.getRoot();
        assertNotNull("The Scene object should receive object meant for laying out the user interface components (in this case BorderPane) as its parameter. Currently the Scene object could not find an object containing the components.", rootOfElements);

        BorderPane borderPane = null;
        try {
            borderPane = BorderPane.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Make sure you're using the BorderPane class for laying out user interface components.");
        }

        assertNotNull("The Scene object should receive a BorderPane object, which is meant for laying out the user interface components, as its parameter.", borderPane);
        assertTrue("The BorderPane should have a text field placed at the top edge. Now the top edge contained: " + borderPane.getTop(), borderPane.getTop() != null && borderPane.getTop().getClass().isAssignableFrom(Label.class));
        assertTrue("The BorderPane should have a text field placed along right edge. Now the right edge contained: " + borderPane.getRight(), borderPane.getRight() != null && borderPane.getRight().getClass().isAssignableFrom(Label.class));
        assertTrue("The BorderPane should have a text field placed at the bottom edge. Now the bottom edge contained: " + borderPane.getBottom(), borderPane.getBottom() != null && borderPane.getBottom().getClass().isAssignableFrom(Label.class));

        assertEquals("The top edge should have the text \"NORTH\". Now the text was: " + ((Label) borderPane.getTop()).getText(), "NORTH", ((Label) borderPane.getTop()).getText());
        assertEquals("The right edge should have the text \"EAST\". Now the text was: " + ((Label) borderPane.getRight()).getText(), "EAST", ((Label) borderPane.getRight()).getText());
        assertEquals("The bottom edge should have the text \"SOUTH\". Now the text was: " + ((Label) borderPane.getBottom()).getText(), "SOUTH", ((Label) borderPane.getBottom()).getText());
    }

}
