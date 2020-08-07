
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AverageOfNumbers {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // Write your program here

        List<String> inputs = new ArrayList<>();
        
        

        while (true) {
            String row = scanner.nextLine();

            if (row.equals("end")) {
                
                double average = inputs.stream()
                .mapToInt(s -> Integer.valueOf(s))
                .average()
                .getAsDouble();
                
                System.out.println("average of numbersl" + average);
                break;
            }
            
            inputs.add(row);
            
              
            
            
            

            

        }
        
        
      

    }
}
