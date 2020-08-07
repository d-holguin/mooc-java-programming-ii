package application;

import application.ShanghaiApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("14-01")
public class ShanghaiApplicationTest extends ApplicationTest {

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
        ShanghaiApplication application = new ShanghaiApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that the ShangaiApplication class inherits Application.");
        }

        try {
            Reflex.reflect(ShanghaiApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the ShangaiApplication class has a start method that takes a Stage object as a parameter. If it has, make sure that the method works correctly. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void chartTest() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should have a Scene object. Now after executing the start method, calling getScene on the stage object returned the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("You should give a chart to the Scene object that is responsible for the first view. Now the Scene object contained no chart or other components that include components.", rootElement);

        LineChart chart = null;
        try {
            chart = LineChart.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure you use the LineChart class as the chart.");
        }

        assertNotNull("You should pass a LineChart object to the Scene object as a constructor parameter.", chart);

        NumberAxis xAxis = null;
        try {
            xAxis = NumberAxis.class.cast(chart.getXAxis());
        } catch (Throwable t) {
            fail("Make sure you use the NumberAxis class to create the x axis of the chart.");
        }

        assertTrue("Create the NumberAxis object that represents the x axis so that you give it a lower limit as a parameter to constrain the lowest displayed value.  Since the first data point is at value 2007, a good first value to show on the x axis might be a little before that point.", xAxis.getLowerBound() > 2000 && xAxis.getLowerBound() <= 2007);
        assertTrue("Create the NumberAxis object that represents the x axis so that you give it an upper limit as a parameter to constrain the highest displayed value. Since the last data point is at value 2016, a food final value to show on the x axis might be a little after that point.", xAxis.getUpperBound() >= 2016 && xAxis.getUpperBound() <= 2025);

        assertEquals("The chart should contain one line. Now their number was " + chart.getData().size(), 1, chart.getData().size());

        XYChart.Series data = (XYChart.Series) chart.getData().get(0);

        List<XYChart.Data> points = new ArrayList<>();
        for (int i = 0; i < data.getData().size(); i++) {
            Object piste = data.getData().get(i);
            try {
                points.add(XYChart.Data.class.cast(piste));
            } catch (Throwable t) {
                fail("Make sure you use the XYChart.Data class to represents the data points. Error: " + t.getMessage());
            }
        }

        assertTrue("For the year 2007 there was no data point. Are you certain you added the following point to the data new XYChart.Data(2007, 73);", points.stream().filter(p -> p.getXValue().equals(2007)).findFirst().isPresent());
        assertTrue("The point for year 2007 was incorrect. Are you certain you added the following point to the data: new XYChart.Data(2007, 73);", points.stream().filter(p -> p.getXValue().equals(2007)).findFirst().get().getYValue().equals(73));

        assertTrue("For the year 2011 there was no data point. Are you certain you added the following point to the data: new XYChart.Data(2011, 74);", points.stream().filter(p -> p.getXValue().equals(2011)).findFirst().isPresent());
        assertTrue("The point for year 2007 was incorrect. Are you certain you added the following point to the data: new XYChart.Data(2011, 74);", points.stream().filter(p -> p.getXValue().equals(2011)).findFirst().get().getYValue().equals(74));

        assertTrue("For the year 2016 there was no data point. Are you certain you added the following point to the data: new XYChart.Data(2011, 56);", points.stream().filter(p -> p.getXValue().equals(2016)).findFirst().isPresent());
        assertTrue("The point for year 2016 was incorrect. Are you certain you added the following point to the data: new XYChart.Data(2011, 56);", points.stream().filter(p -> p.getXValue().equals(2016)).findFirst().get().getYValue().equals(56));

    }

}
