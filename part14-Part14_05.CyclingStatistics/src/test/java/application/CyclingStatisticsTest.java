package application;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("14-05")
public class CyclingStatisticsTest extends ApplicationTest {

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
        CyclingStatisticsApplication application = new CyclingStatisticsApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that the CyclingStatisticsClass inherits the class Application.");
        }

        try {
            Reflex.reflect(CyclingStatisticsApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the CyclingStatisticsApplication has a method called start that takes a Stage object as its parameter. If it has, make sure that the method works correctly. Error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void noLineCharts() throws Throwable {
        long rows = Files.lines(Paths.get("src/main/java/application/CyclingStatisticsApplication.java")).filter(l -> l.contains("LineChart")).count();
        assertEquals("The string 'LineChart' should not appear in the file 'CyclingStatisticsApplication.java'. Now it appeared at least " + rows + " times.", 0, rows);
    }

    @Test
    public void onlyBarChart() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a Scene object. Now, after pressing the button and calling getScene on the stage returned the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("The Scene object responsible for the view should receive as a parameter a layout component (in this case GridPane). Now any objects that contain components could not be found in the Scene object.", rootElement);

        GridPane layout = null;
        try {
            layout = GridPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure you use the GridPane class to lay out the components in the scene.");
        }

        assertNotNull("The Scene object responsible for the view should receive as a parameter a layout component (in this case GridPane).", layout);

        Optional<Node> chartObject = layout.getChildren().stream().filter(child -> {
            try {
                BarChart.class.cast(child);
            } catch (Throwable t) {
                return false;
            }

            return true;
        }).findFirst();

        assertTrue("Make sure you changed the LineChart object to a BarChart object. Now a BarChart object could not be found in the GridPane layout.", chartObject.isPresent());
        BarChart chart = (BarChart) chartObject.get();
    }
}
