package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ShanghaiApplication extends Application {

    public void start(Stage stage) {
        // create the x and y axes that the chart is going to use
      
        NumberAxis yAxis = new NumberAxis(0,100,10);

        NumberAxis xAxis = new NumberAxis(2007, 2017, 2);

        // set the titles for the axes
        xAxis.setLabel("Year");
        yAxis.setLabel("Ranking");

        // create the line chart. The values of the chart are given as numbers
        // and it uses the axes we created earlier
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("University of Helsinki, Shanghai ranking");

        // create the data set that is going to be added to the line chart
        XYChart.Series UoHData = new XYChart.Series();
        UoHData.setName("testing");
        // and single points into the data set
        UoHData.getData().add(new XYChart.Data(2007, 73));
        UoHData.getData().add(new XYChart.Data(2008, 68));
        UoHData.getData().add(new XYChart.Data(2009, 72));
        UoHData.getData().add(new XYChart.Data(2010, 72));
        UoHData.getData().add(new XYChart.Data(2011, 74));
        UoHData.getData().add(new XYChart.Data(2012, 73));
        UoHData.getData().add(new XYChart.Data(2013, 76));
        UoHData.getData().add(new XYChart.Data(2014, 73));
        UoHData.getData().add(new XYChart.Data(2015, 67));
        UoHData.getData().add(new XYChart.Data(2016, 56));
        UoHData.getData().add(new XYChart.Data(2017, 56));

        // add the data set to the line chart
        lineChart.getData().add(UoHData);

        // create another data set that's going to be added to the chart
      

        // display the line chart
        Scene view = new Scene(lineChart, 640, 480);
        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch(ShanghaiApplication.class);
    }

}
