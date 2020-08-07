package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class UnfairAdvertisementApplication extends Application {

    @Override
    public void start(Stage window) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(77.0, 77.5, 0.1);
       
        yAxis.setTickLabelsVisible(true); ////set to true
        yAxis.setLabel("Faster and Better!");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Internet speed");
        barChart.setLegendVisible(false);

        XYChart.Series speeds = new XYChart.Series();
        speeds.getData().add(new XYChart.Data("NDA", 77.4));
        speeds.getData().add(new XYChart.Data("Fastie", 77.2));
        speeds.getData().add(new XYChart.Data("SuperNet", 77.1));
        speeds.getData().add(new XYChart.Data("Meganet", 77.1));

        barChart.getData().add(speeds);
        Scene view = new Scene(barChart, 400, 300);
        window.setScene(view);
        window.show();
    }

    public static void main(String[] args) {
        launch(UnfairAdvertisementApplication.class);
    }

}
