
import java.util.Scanner;

public class AverageOfPositiveNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int sum = 0;
        int count = 0;

        while (true) {

            System.out.println("Enter input");
            int userInput = Integer.valueOf(scanner.nextLine());

            if (userInput == 0) {

                if (sum > 0) {
                    System.out.println(average(sum, count));

                } else {
                    System.out.println("Cannot calculate the average");
                }

                break;
            }

            if (userInput > 0) {
                count++;
                sum += userInput;

            }

        }

    }

    public static double average(int sum, int count) {

        double result = 0;

        result = 1.0 * sum / count;

        return result;

    }
}
