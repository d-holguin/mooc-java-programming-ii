
import java.util.Scanner;



public class MainProgram {

    public static void main(String[] args) {
        // you can create test code here. Call the methods that you'll implement
        // during the course of this exercise
         Scanner scanner = new Scanner(System.in);
         
        Checker testChecker = new Checker();
         
        
        System.out.println("Enter a String:");
        
        String userInput = scanner.nextLine();
        if(testChecker.allVowels(userInput)){
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }
        
        
        
        
    }
}
