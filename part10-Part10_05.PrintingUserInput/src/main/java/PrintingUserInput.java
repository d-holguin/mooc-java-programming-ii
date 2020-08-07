
import java.util.ArrayList;
import java.util.Scanner;

public class PrintingUserInput {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> userInputList = new ArrayList<>();

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.isEmpty()) {  // i used isBlank() here but it failed the test

                userInputList.stream()
                        .forEach(word -> System.out.println(word));

                break;
            }

            userInputList.add(userInput);

        }

    }
}
