
import java.util.Random;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random randomNum = new Random();

        System.out.println("How many random number should be printed?");

        int userInput = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < userInput; i++) {

            System.out.println(randomNum.nextInt(11));

        }

    }

}
