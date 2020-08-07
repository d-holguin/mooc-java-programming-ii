package application;

import application.PartiesApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("14-02")
public class PartiesApplicationTest extends ApplicationTest {

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
        PartiesApplication application = new PartiesApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the PartiesApplication class inherits Application.");
        }

        try {
            Reflex.reflect(PartiesApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the PartiesApplication class has a method called start that receives a Stage object as its parameter. If it has, make sure that the method works correctly. The error: " + ex.getMessage());
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

        assertTrue("Create the NumberAxis object that represents the x axis so that you give it a lower limit as a parameter to constrain the lowest displayed value.  Since the first data point is at value 1968, a good first value to show on the x axis might be a little before that point.", xAxis.getLowerBound() > 1960 && xAxis.getLowerBound() <= 1968);
        assertTrue("Create the NumberAxis object that represents the x axis so that you give it an upper limit as a parameter to constrain the highest displayed value. Since the last data point is at value 2008, a food final value to show on the x axis might be a little after that point.", xAxis.getUpperBound() >= 2008 && xAxis.getUpperBound() <= 2020);

        
        assertEquals("There should be seven lines in the chart. Now their number was " + chart.getData().size(), 7, chart.getData().size());

        Map<String, XYChart.Series> dataSets = new TreeMap<>();
        for (int i = 0; i < chart.getData().size(); i++) {
            XYChart.Series data = (XYChart.Series) chart.getData().get(i);

            assertNotNull("Make sure to title each line with the corresponding party's name. Now an XYChart.Series object with null as title was found.", data.getName());
            dataSets.put(data.getName(), data);
        }

        List<String> parties = new ArrayList<>(Arrays.asList("KOK", "SDP", "KESK", "VIHR", "VAS", "PS", "RKP"));
        parties.removeAll(dataSets.keySet());
        assertTrue("The chart was missing the party: " + parties.toString().replace("[", "").replace("]", ""), parties.isEmpty());

        Map<String, Map<Integer, Double>> expectedPoints = new TreeMap<>();
        expectedPoints.put("KOK", new TreeMap<>());
        expectedPoints.get("KOK").put(1972, 18.1);
        expectedPoints.get("KOK").put(1992, 19.1);

        expectedPoints.put("VAS", new TreeMap<>());
        expectedPoints.get("VAS").put(1972, 17.5);
        expectedPoints.get("VAS").put(1992, 11.7);

        expectedPoints.put("RKP", new TreeMap<>());
        expectedPoints.get("RKP").put(1968, 5.6);
        expectedPoints.get("RKP").put(2008, 4.7);

        for (String party : expectedPoints.keySet()) {
            assertTrue("The following party was not found in the data: " + party, dataSets.containsKey(party));

            XYChart.Series data = dataSets.get(party);
            List<Data> dataPoints = new ArrayList<>();
            data.getData().stream().forEach(d -> dataPoints.add(XYChart.Data.class.cast(d)));

            for (Map.Entry<Integer, Double> entry : expectedPoints.get(party).entrySet()) {

                Optional<Data> optional = dataPoints.stream().filter(p -> p.getXValue().equals(entry.getKey())).findFirst();
                assertTrue("For the year " + entry.getKey() + " there was no point in the data of the " + party + " party. Did you add to the data the point new XYChart.Data(" + entry.getKey() + ", " + entry.getValue() + ");", optional.isPresent());
                assertEquals("The point for the year " + entry.getKey() + " was incorrect for the party " + party + ". Did you add to the data the point new XYChart.Data(" + entry.getKey() + ", " + entry.getValue() + ");", entry.getValue().doubleValue(), (double) optional.get().getYValue(), 0.1);
            }
        }

    }

}
