package application;

import application.MultipleViews;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("13-09")
public class MultipleViewsTest extends ApplicationTest {

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
        MultipleViews application = new MultipleViews();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that MultipleViews inherits the Application class.");
        }

        try {
            Reflex.reflect(MultipleViews.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the MultipleViews class had the method start that receives a Stage object as a parameter. If this is the case, make sure that the method words correctly. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void firstView() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a Scene object. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("You should give a layout component (BorderPane in this case) as a parameter to the Scene object responsible for the first view. Now the Scene object did not contain a layout component.", rootElement);

        BorderPane layout = null;
        try {
            layout = BorderPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure that you use the BorderPane class to construct the layout of the first view.");
        }

        assertNotNull("The scene object that forms the first view should receive as a parameter a BorderPane object that is responsible for the layout of the components.", layout);
        assertTrue("There should be a Button object at the center of the BorderPane. Now it contained: " + layout.getCenter(), layout.getCenter().getClass().isAssignableFrom(Button.class));
        assertTrue("There should be a Label object at the top of the BorderPane. Now it contained: " + layout.getTop(), layout.getTop().getClass().isAssignableFrom(Label.class));

        clickOn(".button");
        Scene second = stage.getScene();
        assertNotEquals("When the button in the user interface is clicked, the displayed view should change. Now after pressing the button the Scene object of the Stage was the same as before the click.", scene, second);
    }

    @Test
    public void secondView() {
        clickOn(".button");

        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a Scene object. Now after pressing the button the getScene call of the stage returned the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("The scene object that forms the first view should receive as a parameter a layout component (VBOx, in this case). Now the Scene object did not contain a layout component.", rootElement);

        VBox layout = null;
        try {
            layout = VBox.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure that you are using the VBox class to form the layout of the second view.");
        }

        assertNotNull("The Scene object responsible for forming the layout of the second view should receive as its parameter a VBox object. ", layout);

        assertEquals("Two components should have been added to the VBox object. Now their number was: " + layout.getChildren().size(), 2, layout.getChildren().size());

        assertTrue("The first element of the VBox object should be a Button object. Now it was: " + layout.getChildren().get(0), layout.getChildren().get(0).getClass().isAssignableFrom(Button.class));
        assertTrue("The second element of the VBox object should be a Label object. Now it was: " + layout.getChildren().get(1), layout.getChildren().get(1).getClass().isAssignableFrom(Label.class));

        clickOn(".button");
        Scene second = stage.getScene();
        assertNotEquals("When the button in the user interface is clicked, the displayed view should change. Now after the button press in the second view the Stage object's Scene object was the same as it was before the button press.", scene, second);
    }

    @Test
    public void thirdView() {
        Scene first = stage.getScene();
        clickOn(".button");
        clickOn(".button");

        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a Scene object. Now after pressing the button the getScene call of the stage returned the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("The scene object that forms the third view should receive as a parameter a layout component (GridPane, in this case). Now the Scene object did not contain a layout component.", rootElement);

        GridPane layout = null;
        try {
            layout = GridPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure you are using the GridPane class to form the layout of the third view.");
        }

        assertNotNull("The Scene object responsible for the third view should receive as its parameter a GridPane object.", layout);

        assertEquals("Two components should have been added to the GridPane object. Now their number was: " + layout.getChildren().size(), 2, layout.getChildren().size());

        assertTrue("The first element of the GridPane object should be a Label object. Now it was: " + layout.getChildren().get(0), layout.getChildren().get(0).getClass().isAssignableFrom(Label.class));
        assertTrue("The second element of the GridPane object should be a Button object. Now it was: " + layout.getChildren().get(1), layout.getChildren().get(1).getClass().isAssignableFrom(Button.class));

        clickOn(".button");
        Scene second = stage.getScene();
        assertEquals("When you press the button in the third view, you should end up back in the first scene. Now the view that followed the button press was not the first view.", first, second);
    }
}
