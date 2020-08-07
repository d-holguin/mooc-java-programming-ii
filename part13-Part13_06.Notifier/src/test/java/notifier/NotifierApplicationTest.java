package notifier;

import notifier.NotifierApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-06")
public class NotifierApplicationTest extends ApplicationTest {

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
        NotifierApplication application = new NotifierApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the NotifierApplication class inherits the Application class.");
        }

        try {
            Reflex.reflect(NotifierApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure the NotifierApplication class has a method called start, which gets a Stage object as its parameter. If it has, make sure the method works. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void worksAsExpected() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a scene object. Currently the call to getScene of stage returned a null reference after the execution of the start method.", scene);
        Parent rootOfElements = scene.getRoot();
        assertNotNull("The Scene object should receive object meant for laying out the user interface components (in this case BorderPane) as its parameter. Currently the Scene object could not find an object containing the components.", rootOfElements);

        VBox vbox = null;
        try {
            vbox = VBox.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Make sure you're using the VBox class for laying out user interface components.");
        }

        assertNotNull("The Scene object should receive a VBox object, which is meant for laying out the user interface components, as its parameter.", vbox);

        assertEquals("Expected the user interface to contain three text elements. Not there were: " + vbox.getChildren().size(), 3, vbox.getChildren().size());

        Node first = vbox.getChildren().get(0);
        Node second = vbox.getChildren().get(1);
        Node third = vbox.getChildren().get(2);
        assertTrue("The first element added to VBox should be a TextField object. Now it wasn't. Found: " + first, first.getClass().isAssignableFrom(TextField.class));
        assertTrue("The second element added to VBox should be a Button object. Now it wasn't. Found: " + second, second.getClass().isAssignableFrom(Button.class));
        assertTrue("The third element added to VBox should be a Label object. Now it wasn't. Found: " + third, third.getClass().isAssignableFrom(Label.class));

        TextField textField = (TextField) vbox.getChildren().get(0);
        Button button = (Button) vbox.getChildren().get(1);
        Label textElement = (Label) vbox.getChildren().get(2);

        textField.setText("Hello world!");

        clickOn(button);

        assertEquals("When the text field has the text \"Hello world!\" and the button is pressed, then the same text should be copied to the text element. Now the textelement contained: " + textElement.getText(), "Hello world!", textElement.getText());

        textField.setText("And other world!");
        clickOn(button);
        assertEquals("When the text field has the text \"And other world!\" and the button is pressed, then the same text should be copied to the text element. Now the textelement contained: " + textElement.getText(), "And other world!", textElement.getText());
    }
}
