package buttonandlabel;

import buttonandlabel.ButtonAndLabelApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-02")
public class ButtonAndLabelTest extends ApplicationTest {

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
        ButtonAndLabelApplication application = new ButtonAndLabelApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the class ButtonAndLabelApplication inherits the class Application.");
        }

        try {
            Reflex.reflect(ButtonAndLabelApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure the class ButtonAndLabelApplication has the method start, which receives a stage object as a parameter. If it does, make sure the method works. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void hasRequiredElements() {
        Scene scene = stage.getScene();
        assertNotNull("Stage object should have a Scene object. Now a getScene call to the stage returned null.", scene);
        Parent elementRoot = scene.getRoot();
        assertNotNull("The Scene object must receive an object for displaying interface components (for ecample a FlowPane). Now the Scene object did not have an object containing the interface components.", elementRoot);
        List<Node> children = new ArrayList(elementRoot.getChildrenUnmodifiable());
        boolean hasLabel = false;
        boolean hasButton = false;

        while (!children.isEmpty()) {
            Node node = children.get(0);
            if (node instanceof Label) {
                hasLabel = true;
            }

            if (node instanceof Button) {
                hasButton = true;
            }

            if (node instanceof Parent) {
                Parent p = (Parent) node;
                children.addAll(p.getChildrenUnmodifiable());
            }

            children.remove(node);
        }

        assertTrue("Scene object did not contain a label", hasLabel);
        assertTrue("Scene object did not contain a button", hasButton);

    }

}
