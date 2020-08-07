package title;

import java.util.Scanner;
import javafx.application.Application;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a tile");
        
        String userInput = scanner.nextLine();
        
        
    Application.launch(UserTitle.class,
            
            
            
            
            "--userTitle=" + userInput);

    }

}
