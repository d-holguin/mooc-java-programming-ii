
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // test your code here

        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card(3, Suit.SPADE));
        cards.add(new Card(2, Suit.DIAMOND));
        cards.add(new Card(14, Suit.SPADE));
        cards.add(new Card(12, Suit.HEART));
        cards.add(new Card(2, Suit.SPADE));

        //SortBySuit sortBySuitSorter = new SortBySuit();
        //Collections.sort(cards, sortBySuitSorter);

        cards.stream().forEach(c -> System.out.println(c));

    }
}

//        Card first = new Card(2, Suit.DIAMOND);
//        Card second = new Card(14, Suit.SPADE);
//        Card third = new Card(12, Suit.HEART);
//
//        System.out.println(first);
//        System.out.println(second);
//        System.out.println(third);
//    }

