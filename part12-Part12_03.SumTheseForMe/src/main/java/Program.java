

public class Program {

    public static void main(String[] args) {

        int[] numbers = {3, -1, 8, 4};

        System.out.println(sum(numbers, 0, 0, 0, 0));
        System.out.println(sum(numbers, 0, 0, 0, 10));
        System.out.println(sum(numbers, 0, 1, 0, 10));
        System.out.println(sum(numbers, 0, 1, -10, 10));
        System.out.println(sum(numbers, -1, 999, -10, 10));

    }

    public static int sum(int[] array, int fromWhere, int toWhere, int smallest, int largest) {

        int sum = 0;

        int startIndex = fromWhere;
        int endIndex = toWhere;

        if (fromWhere < 0) {
            startIndex = 0;
        }
        if (toWhere > array.length ) {
            endIndex = array.length ;
        }

        while (startIndex < endIndex) {
            if (array[startIndex] <= largest && array[startIndex] >= smallest) {

                sum += array[startIndex];
            }

            startIndex++;
        }

        return sum;
    }

}
