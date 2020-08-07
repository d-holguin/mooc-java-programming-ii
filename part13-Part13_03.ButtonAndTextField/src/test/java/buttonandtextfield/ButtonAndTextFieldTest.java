package buttonandtextfield;

import buttonandtextfield.ButtonAndTextFieldApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

public class ButtonAndTextFieldTest extends ApplicationTest {

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
        ButtonAndTextFieldApplication application = new ButtonAndTextFieldApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the class ButtonAndTextFieldApplication inherits the class Application.");
        }

        try {
            Reflex.reflect(ButtonAndTextFieldApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure the class ButtonAndTextFieldApplication has a start method, which receives a stage object. If it does, make sure the method works. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("13-03")
    public void hasRequiredComponents() {
        Scene scene = stage.getScene();
        assertNotNull("Stage object should have a scene object. Now a getScene call to the Stage returns null.", scene);
        Parent componentRoot = scene.getRoot();
        assertNotNull("Scene object should receive an object for displaying components (such as a FlowPane).", componentRoot);
        List<Node> children = new ArrayList(componentRoot.getChildrenUnmodifiable());
        boolean hasTextField = false;
        boolean hasButton = false;

        while (!children.isEmpty()) {
            Node node = children.get(0);
            if (node instanceof TextField) {
                hasTextField = true;
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

        assertTrue("The Scene object did not have a TextField component.", hasTextField);
        assertTrue("The Scene object did not have a Button component.", hasButton);

    }

}
