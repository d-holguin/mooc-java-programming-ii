//PartiesApplication 
package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PartiesApplication extends Application {

    static Parties partyList = new Parties();
    static ArrayList<Integer> years = new ArrayList<>();
//year
    static int year1;
    static int year2;
    static int year3;
    static int year4;
    static int year5;
    static int year6;
    static int year7;
    static int year8;
    static int year9;
    static int year10;
    static int year11;

    public void start(Stage stage) {
        // create the x and y axes that the chart is going to use

        NumberAxis yAxis = new NumberAxis(0, 30, 5);

        NumberAxis xAxis = new NumberAxis(1968, 2008, 4);

        // set the titles for the axes
//        xAxis.setLabel("Year");
//        yAxis.setLabel("Ranking");
        // create the line chart. The values of the chart are given as numbers
        // and it uses the axes we created earlier
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realative support of the parties");

        // create the data set that is going to be added to the line chart
        //XYChart.Series UoHData = new XYChart.Series();
        // UoHData.setName("Year");
        // and single points into the data set
       // XYChart.Series data1 = new XYChart.Series();

        //data1.getData().add(new XYChart.Data(2000, 20.5));

        for (int i = 0; i < partyList.getSize(); i++) {

            //String dataName = partyList.getParty(i).getName();
            XYChart.Series data = new XYChart.Series();

            data.setName(partyList.getParty(i).getName());

            //lineChart.getData().add(data1);
            for (int j = 0; j < years.size(); j++) {
                if (!partyList.getParty(i).getValue(j).trim().equals("-")) {

                    //System.out.println(years.get(j) + " : " + Double.valueOf(partyList.getParty(i).getValue(j)) );
                    data.getData().add(new XYChart.Data(years.get(j), Double.valueOf(partyList.getParty(i).getValue(j))));

                    System.out.println(years.get(j) + " : " + Double.valueOf(partyList.getParty(i).getValue(j)));

                }

            }

            lineChart.getData().add(data);

        }

        // add the data set to the line chart
        // lineChart.getData().add(UoHData);
        // create another data set that's going to be added to the chart
        // display the line chart
        Scene view = new Scene(lineChart, 640, 480);

        // System.out.println(lineChart.getData().size()) ;
        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        try (Scanner fileScanner = new Scanner(Paths.get("partiesdata.tsv"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                ArrayList<String> values = new ArrayList<>();

                String[] splitString = line.split("\t");

                String partyName = splitString[0];

                if (partyName.equals("Party")) {
                    year1 = Integer.valueOf(splitString[1]);
                    year2 = Integer.valueOf(splitString[2]);
                    year3 = Integer.valueOf(splitString[3]);
                    year4 = Integer.valueOf(splitString[4]);
                    year5 = Integer.valueOf(splitString[5]);
                    year6 = Integer.valueOf(splitString[6]);
                    year7 = Integer.valueOf(splitString[7]);
                    year8 = Integer.valueOf(splitString[8]);
                    year9 = Integer.valueOf(splitString[9]);
                    year10 = Integer.valueOf(splitString[10]);
                    year11 = Integer.valueOf(splitString[11]);
                } else {

                    //System.out.println("Testing");
                    //values.add(splitString[0]);
                    values.add(splitString[1]);
                    values.add(splitString[2]);
                    values.add(splitString[3]);
                    values.add(splitString[4]);
                    values.add(splitString[5]);
                    values.add(splitString[6]);
                    values.add(splitString[7]);
                    values.add(splitString[8]);
                    values.add(splitString[9]);
                    values.add(splitString[10]);
                    values.add(splitString[11]);

                    partyList.addParty(new Party(splitString[0], values));

                }

            }
            years.add(year1);
            years.add(year2);
            years.add(year3);
            years.add(year4);
            years.add(year5);
            years.add(year6);
            years.add(year7);
            years.add(year8);
            years.add(year9);
            years.add(year10);
            years.add(year11);

            launch(PartiesApplication.class);
        } catch (FileNotFoundException e) {

            System.out.println("Error reading file myError123 " + e.getMessage());
        }

    }

}
