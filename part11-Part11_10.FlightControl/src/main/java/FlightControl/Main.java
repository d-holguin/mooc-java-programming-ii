package FlightControl;

import flightControl.logic.FlightControl;
import flightControl.ui.TextUI;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        // Write the main program here. It is useful to create some classes of your own.
        
        
        FlightControl testFC = new FlightControl();
        Scanner sc = new Scanner(System.in);
                
                TextUI ui = new TextUI(testFC, sc);
                
                ui.start();
    }
}