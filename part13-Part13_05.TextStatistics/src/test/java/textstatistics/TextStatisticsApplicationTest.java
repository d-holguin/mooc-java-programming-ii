package textstatistics;

import textstatistics.TextStatisticsApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-05")
public class TextStatisticsApplicationTest extends ApplicationTest {

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
        TextStatisticsApplication application = new TextStatisticsApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the TextStatisticsApplication class inherits the Application class.");
        }

        try {
            Reflex.reflect(TextStatisticsApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure the TextStatisticsApplication class has a method called start, which gets a Stage object as its parameter. If it has, make sure the method works. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
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
        assertTrue("The BorderPane should have a TextArea object placed in the center. Now the center contained: " + borderPane.getCenter(), borderPane.getCenter() != null && borderPane.getCenter().getClass().isAssignableFrom(TextArea.class));
        assertTrue("The BorderPane should have a HBox object placed at the bottom edge. Now the bottom edge contained: " + borderPane.getBottom(), borderPane.getBottom() != null && borderPane.getBottom().getClass().isAssignableFrom(HBox.class));

        HBox box = (HBox) borderPane.getBottom();
        assertEquals("Expected there to be three text elements along the bottom edge. Now there were only: " + box.getChildren().size(), 3, box.getChildren().size());

        List<Node> textElements = box.getChildren();
        for (Node node : textElements) {
            assertTrue("The elements added to the HBox object should be Label elements. Now they were not. Found: " + node, node.getClass().isAssignableFrom(Label.class));
        }

        assertEquals("The first text element should have the text \"Letters: 0\". Now the text was: \"" + ((Label) textElements.get(0)).getText() + "\"", "Letters: 0", ((Label) textElements.get(0)).getText());
        assertEquals("The second text element should have the text \"Words: 0\". Now the text was: \"" + ((Label) textElements.get(1)).getText() + "\"", "Words: 0", ((Label) textElements.get(1)).getText());
        assertEquals("The third text element should have the text \"The longest word is:\". Now the text was: \"" + ((Label) textElements.get(2)).getText() + "\"", "The longest word is:", ((Label) textElements.get(2)).getText().trim());
    }

}
