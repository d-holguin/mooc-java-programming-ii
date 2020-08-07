
import java.util.ArrayList;
import java.util.Random;

public class LotteryRow {

    private ArrayList<Integer> numbers;
    private Random random = new Random();

    public LotteryRow() {
        // Draw the numbers when the LotteryRow is created
        this.randomizeNumbers();
    }

    public ArrayList<Integer> numbers() {
        return this.numbers;
    }

    public void randomizeNumbers() {
        // Initialize the list for numbers
        this.numbers = new ArrayList<>();
        //int randomNum = (int) this.random.nextInt(40);

        while (this.numbers.size() < 7) {
            int randomNum = (int) this.random.nextInt(40) + 1;
            if (!this.numbers.contains(randomNum)) {

                this.numbers.add(randomNum);

            }

        }

        // Implement the random number generation here
        // the method containsNumber is probably useful
    }

    public boolean containsNumber(int number) {
        boolean isValid = false;
        for (int e : numbers) {
            if (e == number) {
                isValid = true;
            }
        }
        return isValid;

    }

}
