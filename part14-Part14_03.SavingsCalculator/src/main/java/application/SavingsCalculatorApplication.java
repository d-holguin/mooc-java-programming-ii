package application;
//SavingsCalculatorApplication

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SavingsCalculatorApplication extends Application {

    @Override
    public void start(Stage window) {
        BorderPane borderLayout = new BorderPane();

        NumberAxis yAxis = new NumberAxis(0, 110, 10);

        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        //
        Label monthSliderText = new Label("25");
        monthSliderText.setFont(new Font("sans-serif", 20));

        Label interestSliderText = new Label("0");
        interestSliderText.setFont(new Font("sans-serif", 20));

        // set the titles for the axes
        xAxis.setLabel("Years");
        yAxis.setLabel("Amount");

        // create the line chart. The values of the chart are given as numbers
        // and it uses the axes we created earlier
        
        VBox topVBox = new VBox();
        topVBox.setPadding(new Insets(15, 15, 15, 15));
        BorderPane monthlyBPane = new BorderPane();
        BorderPane interestBPane = new BorderPane();

        //LAbels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Label monthLabel = new Label("Monthly savings");

        Label interestLabel = new Label("Yearly interest rate");

        monthLabel.setFont(new Font("Arial", 20));
        interestLabel.setFont(new Font("Arial", 20));
        interestBPane.setLeft(interestLabel);
        monthlyBPane.setLeft(monthLabel);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //Monthly Saving slider~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Slider savingsSlider = new Slider(25, 250, 25);
        monthlyBPane.setCenter(savingsSlider);
        monthlyBPane.setRight(monthSliderText);
        savingsSlider.setMinorTickCount(5);
        savingsSlider.setMajorTickUnit(25);
        savingsSlider.setShowTickMarks(true);
        savingsSlider.setShowTickLabels(true);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("University of Helsinki, Shanghai ranking");
        
        XYChart.Series lineOneChart = new XYChart.Series();
        
        savingsSlider.valueProperty().addListener((observable, oldvalue, newvalue)
                -> {
            int i = newvalue.intValue();
            monthSliderText.setText(Integer.toString(i));

            
            


        });

//~~~~~~~~~~~~~~~intesrt slider~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Slider interestSlider = new Slider(0, 10, 0);
        interestBPane.setCenter(interestSlider);
        interestBPane.setRight(interestSliderText);
        interestSlider.setMinorTickCount(0);
        interestSlider.setMajorTickUnit(2);
        interestSlider.setShowTickMarks(true);
        interestSlider.setShowTickLabels(true);
        interestSlider.valueProperty().addListener((observable, oldvalue, newvalue)
                -> {
            int i = newvalue.intValue();
            interestSliderText.setText(Integer.toString(i));
        });

        topVBox.getChildren().add(monthlyBPane);
        topVBox.getChildren().add(interestBPane);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        borderLayout.setTop(topVBox);

        borderLayout.setCenter(lineChart);

        Scene view = new Scene(borderLayout, 700, 800);

        window.setScene(view);
        window.show();

    }
    
    
    

    public static void main(String[] args) {
        launch(SavingsCalculatorApplication.class);
    }

}
