package application;

import application.SavingsCalculatorApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class SavingsCalculatorTest extends ApplicationTest {

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
        SavingsCalculatorApplication applicationToTest = new SavingsCalculatorApplication();

        try {
            Application app = Application.class.cast(applicationToTest);
        } catch (Throwable t) {
            fail("Make sure the SavingsCalculator class inherits the Application class.");
        }

        try {
            Reflex.reflect(SavingsCalculatorApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(applicationToTest, stage);
        } catch (Throwable ex) {
            fail("Make sure the SavingsCalculator class has method start, which gets a Stage object as a parameter. If it does, make sure the method works. Error:  " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("14-03.1")
    public void uiUsesBorderPane() {
        Scene scene = stage.getScene();
        assertNotNull("Stage object should contain a Scene object. Now a getScene call after calling the start method returned null.", scene);
        Parent rootOfElements = scene.getRoot();

        assertNotNull("The scene object representing the first view should have a BorderPane object. Now the scene object did not have a BorderPane object.", rootOfElements);

        BorderPane setUp = null;
        try {
            setUp = BorderPane.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Scene objecy should have a BorderPane object. ");
        }

        assertNotNull("Scene objecy should have a BorderPane object.", setUp);
    }

    @Test
    @Points("14-03.1")
    public void uiLineChartTest() {
        Scene scene = stage.getScene();
        assertNotNull("Stage object should have a Scene object. Now a getScene call returned null.", scene);
        Parent rootOfElements = scene.getRoot();

        assertNotNull("The scene object representing the first view should have a BorderPane object. Now the scene object did not have a BorderPane object.", rootOfElements);

        BorderPane setUp = null;
        try {
            setUp = BorderPane.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Scene objecy should have a BorderPane object.");
        }

        assertNotNull("Scene object should be given a BorderPane object as a parameter", setUp);

        // getLineChart keskellä
        assertNotNull("There should be a LineChart in the middle of the BorderPane", setUp.getCenter());

        LineChart lineChart = null;
        try {
            lineChart = LineChart.class.cast(setUp.getCenter());
        } catch (Throwable t) {
            fail("Use a LineChart object for the line chart");
        }

        assertNotNull("There should be a LineChart in the middle of the BorderPane", lineChart);

        NumberAxis xAxis = null;
        try {
            xAxis = NumberAxis.class.cast(lineChart.getXAxis());
        } catch (Throwable t) {
            fail("Use NumberAxis for the x-axis of the line chart");
        }

        assertNotNull("Use NumberAxis for the x-axis of the line chart", xAxis);

        NumberAxis yAxis = null;
        try {
            yAxis = NumberAxis.class.cast(lineChart.getYAxis());
        } catch (Throwable t) {
            fail("Use NumberAxis for the y-axis of the line chart");
        }

        assertNotNull("Use NumberAxis for the y-axis of the line chart", yAxis);
    }

    @Test
    @Points("14-03.1")
    public void uiSlidersTest() {
        Scene scene = stage.getScene();
        assertNotNull("A stage object should have a scene object. Now a getScene call returned null. ", scene);
        Parent rootOfElements = scene.getRoot();

        assertNotNull("Make sure the scene object representing the first view is given a BorderPane object.", rootOfElements);

        BorderPane setUp = null;
        try {
            setUp = BorderPane.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Scene objecy should have a BorderPane object.");
        }

        assertNotNull("Scene objecy should have a BorderPane object.", setUp);

        // getLineChart keskellä
        assertNotNull("There should be a VBox object on the top sectio  of the BorderPane", setUp.getTop());

        VBox topOfUi = null;
        try {
            topOfUi = VBox.class.cast(setUp.getTop());
        } catch (Throwable t) {
            fail("There should be a VBox object on the top of the BorderPane");
        }

        assertNotNull("There should be a VBox object on the top of the BorderPane", topOfUi);

        assertTrue("The VBox object at the top section of the BorderPane should contain two BorderPane objects", topOfUi.getChildren().size() == 2);

        for (Node node : topOfUi.getChildren()) {
            BorderPane pane = null;
            try {
                pane = BorderPane.class.cast(node);
            } catch (Throwable t) {
                fail("The VBox object at the top section of the BorderPane should contain two BorderPane objects");
            }

            assertNotNull("The VBox object at the top section of the BorderPane should contain two BorderPane objects", pane);
        }

        BorderPane savings = (BorderPane) topOfUi.getChildren().get(0);

        assertNotNull("The topmost BorderPane on the top section of the UI should have a label on the left.", savings.getLeft());
        assertNotNull("The topmost BorderPane on the top section of the UI  should have a label on the right.", savings.getRight());

        assertTrue("The topmost BorderPane on the top section of the UI should have a label on the left.", savings.getLeft().getClass().isAssignableFrom(Label.class));
        assertTrue("The topmost BorderPane on the top section of the UI  should have a label on the right.", savings.getRight().getClass().isAssignableFrom(Label.class));

        assertNotNull("The topmost BorderPane on the top section of the UI  should have a slider in the middle.", savings.getCenter());
        assertTrue("The topmost BorderPane on the top section of the UI  should have a slider in the middle.", savings.getCenter().getClass().isAssignableFrom(Slider.class));

        assertEquals("The topmost BorderPane on the top section of the UI  should have the text \"Monthly savings\".", "Monthly savings", ((Label) savings.getLeft()).getText());

        BorderPane interestRate = (BorderPane) topOfUi.getChildren().get(1);
        assertNotNull("The bottom BorderPane on the top section of the UI should have a label on the left.", interestRate.getLeft());
        assertNotNull("The bottom BorderPane on the top section of the UI  should have a label on the right.", interestRate.getRight());

        assertTrue("The bottom BorderPane on the top section of the UI should have a label on the left.", interestRate.getLeft().getClass().isAssignableFrom(Label.class));
        assertTrue("The bottom BorderPane on the top section of the UI  should have a label on the right.", interestRate.getRight().getClass().isAssignableFrom(Label.class));

        assertNotNull("The bottom BorderPane on the top section of the UI  should have a slider in the middle.", interestRate.getCenter());
        assertTrue("The bottom BorderPane on the top section of the UI  should have a slider in the middle.", interestRate.getCenter().getClass().isAssignableFrom(Slider.class));

        assertEquals("The bottom BorderPane on the top section of the UI  should have the text \"Yearly interest rate\".", "Yearly interest rate",((Label)  interestRate.getLeft()).getText());

        Slider savingsSlider = (Slider) savings.getCenter();
        assertEquals("Set the minimum of the savings slider to   25. Now it was " + savingsSlider.getMin(), 25, savingsSlider.getMin(), 0.001);
        assertEquals("Set the maximum of the savings slider to   250. Now it was " + savingsSlider.getMax(), 250, savingsSlider.getMax(), 0.001);

        Slider interestRateSlider = (Slider) interestRate.getCenter();
        assertEquals("Set the minimum of the interest slider to  0. Now it was " + interestRateSlider.getMin(), 0, interestRateSlider.getMin(), 0.001);
        assertEquals("Set the maximum of the interest slider to  10. Now it was " + interestRateSlider.getMax(), 10, interestRateSlider.getMax(), 0.001);
    }

    @Test
    @Points("14-03.2")
    public void calculatingSavingsWithMinimumValues() {
        Slider savingsSlider = getSliders().get(0);

        moveToMin(savingsSlider, 25);

        sleep(100);

        examineLineChartLines("The sum of the savings is not calculated correctly when the monthly savings is 25.", minSavings());
    }

    @Test
    @Points("14-03.2")
    public void calculatingSavingsWithMaximumValues() {
        Slider savingsSlider = getSliders().get(0);

        moveToMax(savingsSlider, 250);

        sleep(100);

        examineLineChartLines("The sum of the savings is not calculated correctly when the monthly savings is 250.", maxSavings());
    }

    @Test
    @Points("14-03.3")
    public void interestrateWithMinSavingsAndMaxInterestRate() {
        Slider savingsSlider = getSliders().get(0);

        moveToMin(savingsSlider, 25);

        Slider interestRateSlider = getSliders().get(1);

        moveToMax(interestRateSlider, 10);

        examineLineChartLines("The sum of the savings and the interest rate is not calculated correctly  with the minimum savings sum and maximum interest rate", minSavingsMaxInterestRate());
    }

    @Test
    @Points("14-03.3")
    public void interestrateWithMaxSavingsAndMaxInterestRate() {
        Slider savingsSlider = getSliders().get(0);

        moveToMax(savingsSlider, 250);

        Slider interestRateSlider = getSliders().get(1);

        moveToMax(interestRateSlider, 10);

        examineLineChartLines("The sum of the savings and the interest rate is not calculated correctly with the maximum savings sum and maximum interest rate.", maxSavingsMaxInterestRate());
    }

    private void moveToMax(Slider slider, int max) {

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.RIGHT, 4);

        Bounds b = slider.getBoundsInLocal();
        b = slider.localToScreen(b);

        moveTo(new Point2D(b.getMinX(), (b.getMinY() + b.getMaxY()) / 2));
        press(MouseButton.PRIMARY);
        drag(b.getMaxX(), (b.getMinY() + b.getMaxY()) / 2, MouseButton.PRIMARY);
        release(MouseButton.PRIMARY);

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.RIGHT, 4);

        while (slider.getValue() < max) {
            drag(slider.getChildrenUnmodifiable().get(2)).dropBy(5, 0);
        }

    }

    private void moveToMin(Slider slider, int min) {

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.LEFT, 4);

        Bounds b = slider.getBoundsInLocal();
        b = slider.localToScreen(b);

        moveTo(new Point2D(b.getMaxX(), (b.getMinY() + b.getMaxY()) / 2));
        press(MouseButton.PRIMARY);
        drag(b.getMinX(), (b.getMinY() + b.getMaxY()) / 2, MouseButton.PRIMARY);
        release(MouseButton.PRIMARY);

        clickOn(slider.getChildrenUnmodifiable().get(2)).type(KeyCode.LEFT, 4);

        while (slider.getValue() > min) {
            drag(slider.getChildrenUnmodifiable().get(2)).dropBy(-5, 0);
        }

    }

    private void examineLineChartLines(String message, String data) {

        LineChart lineChart = getLineChart();

        List<Map<Integer, Double>> valuesOfChartLines = getLineChartValues(lineChart);

        boolean allFound = true;

        for (Map<Integer, Double> map : valuesOfChartLines) {
            allFound = true;

            for (String line : data.split("\n")) {
                String[] parts = line.split(", ");

                int x = Integer.parseInt(parts[0]);
                double y = Double.parseDouble(parts[1]);

                if (map.containsKey(x) && map.get(x) != null && Math.abs(map.get(x) - y) < 0.001) {
                    continue;
                }

                allFound = false;
                break;
            }

            if (allFound) {
                break;
            }
        }

        assertTrue(message, allFound);
    }

    private List<Map<Integer, Double>> getLineChartValues(LineChart lineChart) {
        List<Map<Integer, Double>> valuesOfChartLines = new ArrayList<>();

        for (int i = 0; i < lineChart.getData().size(); i++) {

            XYChart.Series data = (XYChart.Series) lineChart.getData().get(i);
            List<XYChart.Data> dataPoints = new ArrayList<>();
            data.getData().stream().forEach(d -> dataPoints.add(XYChart.Data.class.cast(d)));

            Map<Integer, Double> lineValues = new HashMap<>();
            for (XYChart.Data point : dataPoints) {
                int x = (int) point.getXValue();
                double y = 0;

                try {
                    y = (double) point.getYValue();
                } catch (Throwable t) {
                    try {
                        y = (int) point.getYValue();
                    } catch (Throwable t2) {
                    }
                }

                lineValues.put(x, y);
            }

            valuesOfChartLines.add(lineValues);
        }

        return valuesOfChartLines;
    }

    private List<Slider> getSliders() {
        uiSlidersTest();

        Scene scene = stage.getScene();
        Parent rootOfElements = scene.getRoot();

        BorderPane setUp = null;
        try {
            setUp = BorderPane.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Scene objecy should have a BorderPane object.");
        }

        VBox topOfUi = null;
        try {
            topOfUi = VBox.class.cast(setUp.getTop());
        } catch (Throwable t) {
            fail("There should be a VBox object on the top of the BorderPane");
        }

        BorderPane savings = (BorderPane) topOfUi.getChildren().get(0);
        BorderPane interestRate = (BorderPane) topOfUi.getChildren().get(1);

        List<Slider> sliders = new ArrayList<>();

        Slider savingsSlider = (Slider) savings.getCenter();
        Slider interestRateSlider = (Slider) interestRate.getCenter();

        sliders.add(savingsSlider);
        sliders.add(interestRateSlider);

        return sliders;
    }

    private LineChart getLineChart() {
        Scene scene = stage.getScene();
        Parent rootOfElements = scene.getRoot();

        BorderPane setUp = null;
        try {
            setUp = BorderPane.class.cast(rootOfElements);
        } catch (Throwable t) {
            fail("Scene objecy should have a BorderPane object.");
        }

        assertNotNull("Scene objecy should have a BorderPane object.", setUp);

        // getLineChart keskellä
        assertNotNull("There should be a LineChart in the middle of the BorderPane", setUp.getCenter());

        LineChart lineChart = null;
        try {
            lineChart = LineChart.class.cast(setUp.getCenter());
        } catch (Throwable t) {
            fail("Use a LineChart object for the line chart");
        }

        return lineChart;
    }

    private String minSavings() {
        return "0, 0\n"
                + "1, 300.0\n"
                + "2, 600.0\n"
                + "3, 900.0\n"
                + "4, 1200.0\n"
                + "5, 1500.0\n"
                + "6, 1800.0\n"
                + "7, 2100.0\n"
                + "8, 2400.0\n"
                + "9, 2700.0\n"
                + "10, 3000.0\n"
                + "11, 3300.0\n"
                + "12, 3600.0\n"
                + "13, 3900.0\n"
                + "14, 4200.0\n"
                + "15, 4500.0\n"
                + "16, 4800.0\n"
                + "17, 5100.0\n"
                + "18, 5400.0\n"
                + "19, 5700.0\n"
                + "20, 6000.0\n"
                + "21, 6300.0\n"
                + "22, 6600.0\n"
                + "23, 6900.0\n"
                + "24, 7200.0\n"
                + "25, 7500.0\n"
                + "26, 7800.0\n"
                + "27, 8100.0\n"
                + "28, 8400.0\n"
                + "29, 8700.0\n"
                + "30, 9000.0";
    }

    private String maxSavings() {
        return "0, 0\n"
                + "1, 3000.0\n"
                + "2, 6000.0\n"
                + "3, 9000.0\n"
                + "4, 12000.0\n"
                + "5, 15000.0\n"
                + "6, 18000.0\n"
                + "7, 21000.0\n"
                + "8, 24000.0\n"
                + "9, 27000.0\n"
                + "10, 30000.0\n"
                + "11, 33000.0\n"
                + "12, 36000.0\n"
                + "13, 39000.0\n"
                + "14, 42000.0\n"
                + "15, 45000.0\n"
                + "16, 48000.0\n"
                + "17, 51000.0\n"
                + "18, 54000.0\n"
                + "19, 57000.0\n"
                + "20, 60000.0\n"
                + "21, 63000.0\n"
                + "22, 66000.0\n"
                + "23, 69000.0\n"
                + "24, 72000.0\n"
                + "25, 75000.0\n"
                + "26, 78000.0\n"
                + "27, 81000.0\n"
                + "28, 84000.0\n"
                + "29, 87000.0\n"
                + "30, 90000.0";
    }

    private String minSavingsMaxInterestRate() {
        return "0, 0.0\n"
                + "1, 330.0\n"
                + "2, 693.0\n"
                + "3, 1092.3000000000002\n"
                + "4, 1531.5300000000004\n"
                + "5, 2014.6830000000007\n"
                + "6, 2546.1513000000014\n"
                + "7, 3130.766430000002\n"
                + "8, 3773.8430730000023\n"
                + "9, 4481.227380300003\n"
                + "10, 5259.350118330003\n"
                + "11, 6115.285130163004\n"
                + "12, 7056.8136431793055\n"
                + "13, 8092.495007497237\n"
                + "14, 9231.74450824696\n"
                + "15, 10484.918959071658\n"
                + "16, 11863.410854978825\n"
                + "17, 13379.751940476708\n"
                + "18, 15047.72713452438\n"
                + "19, 16882.499847976822\n"
                + "20, 18900.749832774505\n"
                + "21, 21120.824816051958\n"
                + "22, 23562.907297657155\n"
                + "23, 26249.198027422874\n"
                + "24, 29204.117830165163\n"
                + "25, 32454.529613181683\n"
                + "26, 36029.98257449985\n"
                + "27, 39962.98083194984\n"
                + "28, 44289.27891514483\n"
                + "29, 49048.20680665932\n"
                + "30, 54283.02748732526";
    }

    private String maxSavingsMaxInterestRate() {
        return "0, 0.0\n"
                + "1, 3300.0000000000005\n"
                + "2, 6930.000000000001\n"
                + "3, 10923.0\n"
                + "4, 15315.300000000001\n"
                + "5, 20146.830000000005\n"
                + "6, 25461.513000000006\n"
                + "7, 31307.664300000008\n"
                + "8, 37738.43073000001\n"
                + "9, 44812.27380300001\n"
                + "10, 52593.50118330002\n"
                + "11, 61152.85130163002\n"
                + "12, 70568.13643179303\n"
                + "13, 80924.95007497234\n"
                + "14, 92317.44508246958\n"
                + "15, 104849.18959071655\n"
                + "16, 118634.10854978822\n"
                + "17, 133797.51940476705\n"
                + "18, 150477.27134524376\n"
                + "19, 168824.99847976814\n"
                + "20, 189007.49832774498\n"
                + "21, 211208.2481605195\n"
                + "22, 235629.07297657145\n"
                + "23, 262491.9802742286\n"
                + "24, 292041.1783016515\n"
                + "25, 324545.2961318167\n"
                + "26, 360299.8257449984\n"
                + "27, 399629.80831949826\n"
                + "28, 442892.7891514481\n"
                + "29, 490482.06806659297\n"
                + "30, 542830.2748732523";
    }
}
