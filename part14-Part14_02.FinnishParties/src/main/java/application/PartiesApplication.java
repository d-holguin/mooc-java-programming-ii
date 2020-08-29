
/*
 * Copyright (C) 2020 Dantes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package application;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class PartiesApplication extends Application {
    private HashMap<String, HashMap<Integer, Double>> values;

    public PartiesApplication() {
        this.values = readVoterFile("partiesdata.tsv");
    }

    @Override
    public void start(Stage stage) {
        // create the x and y axes that the chart is going to use
        NumberAxis xAxis = new NumberAxis(1968, 2008, 4);
        NumberAxis yAxis = new NumberAxis(0, 30, 5);

        // create the line chart. The values of the chart are given as numbers
        // and it uses the axes we created earlier
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Relative support of the parties");

        // go through the parties and add them to the chart
        values.keySet().stream().forEach(party -> {
            // a different data set for each party
            XYChart.Series data = new XYChart.Series();
            data.setName(party);

            // add the party's support numbers to the data set
            values.get(party).entrySet().stream().forEach(pair -> {
                data.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
            });

            // and add the data to the chart
            lineChart.getData().add(data);
        });

        // display the line chart
        Scene view = new Scene(lineChart, 400, 300);
        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch(PartiesApplication.class);
        System.out.println("Hello world!");
    }

    private HashMap<String, HashMap<Integer, Double>> readVoterFile(String filename) {
        HashMap<String, HashMap<Integer, Double>> values1 = new HashMap<>();
        ArrayList<Integer> yearList = new ArrayList<>();

        try(Scanner data = new Scanner(Paths.get(filename))) {
            String row = data.nextLine();
            String[] stringArr = row.split("\t");
            for (int i = 1; i < stringArr.length; i++) { // skip first
                yearList.add(Integer.parseInt(stringArr[i]));
            }

            while (data.hasNext()) {
                String next = data.nextLine();
                stringArr = next.split("\t");
                String party = stringArr[0];
                HashMap<Integer, Double> map = new HashMap<>();
                for (int i = 1; i < stringArr.length; i++) {
                    if (!stringArr[i].equals("-")) {
                        int year = yearList.get(i-1);
                        String dat = stringArr[i];
                        double number = Double.parseDouble(dat);
                        map.put(year, number);
                    }

                }
                values1.put(party, map);
            }

        } catch(IOException e) {
            System.err.println("Error: " + e.toString());
        }

        return values1;
    }

}