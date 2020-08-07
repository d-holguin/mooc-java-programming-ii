package smiley;

import smiley.SmileyApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.support.Capture;

@Points("14-06")
public class SmileyTest extends ApplicationTest {

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
        SmileyApplication application = new SmileyApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that the SmileyApplication inherits the Application class.");
        }

        try {
            Reflex.reflect(SmileyApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure tht the SmileyApplication class has a method called start that takes a Stage object as its parameter. If this is the case, make sure that the method works correctly. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void sceneOfStageHasBorderPane() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a Scene object. Now, after executing the start method, calling getScene on stage returns the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("The Scene object that is responsible for the first view should receive a component that is responsible for the layout (in this case BorderPane).  Now the Scene object doesn't seem to contain a layout component.", rootElement);

        BorderPane layout = null;
        try {
            layout = BorderPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure you use the BorderPane class for the layout in the first view.");
        }

        assertNotNull("The Scene object responsible for the first view should receive a BorderPane object as its parameter..", layout);
        assertTrue("There should be a Canvas object in the center of the BorderPane. Now the center contained: " + layout.getCenter(), layout.getCenter().getClass().isAssignableFrom(Canvas.class));
    }

    @Test
    public void somethingDrawnOnCanvas() {

        sceneOfStageHasBorderPane();
        Scene scene = stage.getScene();
        Parent rootElement = scene.getRoot();
        BorderPane asettelu = BorderPane.class.cast(rootElement);

        Canvas canvas = Canvas.class.cast(asettelu.getCenter());
        Capture screenCapture = capture(canvas);

        Image image = screenCapture.getImage();

        PixelReader pixelReader = image.getPixelReader();

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        boolean whiteSeen = false;
        boolean blackSeen = false;

        for (int x = 0; x < w; x++) {

            for (int y = 0; y < h; y++) {
                if (pixelReader.getColor(x, y).equals(Color.WHITE)) {
                    whiteSeen = true;
                }
                if (pixelReader.getColor(x, y).equals(Color.BLACK)) {
                    blackSeen = true;
                }
            }
        }

        assertTrue("Make sure your drawing uses the color white (Color.WHITE).", whiteSeen);
        assertTrue("Make sure that your drawing uses the color black (Color.BLACK).", blackSeen);
    }

}
