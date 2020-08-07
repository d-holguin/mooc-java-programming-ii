
import java.util.Scanner;

public class Cubes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter ino");
            String userInput = scanner.nextLine();

            if (userInput.equals("end")) {

                break;

            }
            int result = 0;

            int convertedInput = Integer.valueOf(userInput);

            result = (convertedInput * convertedInput) * convertedInput;

            System.out.println(result);

        }

    }
}
