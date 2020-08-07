package application;

import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CyclingStatisticsApplication extends Application {

    @Override
    public void start(Stage stage) {

        CyclingStatistics statistics = new CyclingStatistics("helsinki-cycling-statistics.csv");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(new Label("Choose the examined location"), 0, 0);

        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(statistics.locations());

        ListView<String> list = new ListView<>(data);
        gridPane.add(list, 0, 1);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year / Month");
        yAxis.setLabel("Cyclists");

        
        javafx.scene.chart.BarChart<String, Number> chart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);

        list.setOnMouseClicked((MouseEvent event) -> {
            String chosen = list.getSelectionModel().getSelectedItem();
            Map<String, Integer> values = statistics.monthlyCyclists(chosen);
            chart.getData().clear();
            XYChart.Series chartData = new XYChart.Series();

            values.keySet().stream().forEach(time -> {
                chartData.getData().add(new XYChart.Data(time, values.get(time)));
            });

            chart.getData().add(chartData);
        });

        gridPane.add(chart, 1, 0, 1, 2);

        Scene view = new Scene(gridPane);

        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch(CyclingStatisticsApplication.class);
    }

}
