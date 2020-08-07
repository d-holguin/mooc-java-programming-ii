
import java.util.Scanner;

public class LiquidContainers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int firstContainer = 0;
        int secondContainer = 0;

        boolean shouldLoop = true;

        try {  //catch indexout of bound when spliting user input i.e they don't stype a string split by space
            while (shouldLoop) {

                System.out.println("First: " + firstContainer + "/100\n"
                        + "Second: " + secondContainer + "/100");

                //switch statments seem much eaiser to read
                String input = scanner.nextLine();
                if (input.toLowerCase().equals("quit")) {

                    shouldLoop = false;
                    break;
                }
                String[] splitInput = input.split(" ");

                String command = splitInput[0];
                int valueInput = Integer.valueOf(splitInput[1]);

                switch (command.toLowerCase()) {
                    case "add":

                        if (valueInput <= 0) {
                            return;
                        }

                        if (firstContainer + valueInput <= 100) {

                            firstContainer += valueInput;
                        } else if (firstContainer + valueInput > 100) {
                            firstContainer = 100;
                        }

                        break;
                    case "remove":

                        if (valueInput <= 0) {
                            return;
                        }

                        if (valueInput > secondContainer) {
                            valueInput = 0;
                        } else {
                            secondContainer -= valueInput;
                        }
                        break;
                    case "move":

                        if (valueInput <= 0 || firstContainer == 0) {
                            return;
                        }

                        if (firstContainer - valueInput < 0) {
                            valueInput = firstContainer;
                        }

                        if (valueInput >= 100) {
                            firstContainer = 0;
                            secondContainer = 100;

                        } else if (secondContainer + valueInput <= 100) {
                            secondContainer += valueInput;
                            firstContainer -= valueInput;
                        } else if (valueInput + secondContainer > 100 && valueInput + firstContainer > 100) {

                            secondContainer = 100;

                            firstContainer = 0;
                        }

                        break;

                    default:
                        System.out.println("Unknown command");

                }

            }
        } catch (IndexOutOfBoundsException Error) {
            System.out.println("There was an Error reading input: " + Error.getMessage());

        }
    }
}
