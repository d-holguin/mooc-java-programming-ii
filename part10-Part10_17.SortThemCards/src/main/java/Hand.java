import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable<Hand> {
    private ArrayList<Card> hand;

    public Hand() {
        this.hand = new ArrayList<>();
    }
       
    public void add(Card card) {
        this.hand.add(card);
    }
    
    public void print() {
        for (Card card : this.hand) {
            System.out.println(card.toString());
        }
    }
    
    public void sort() {
        Collections.sort(this.hand);
    }
    
    public void sortBySuit() {
        Collections.sort(this.hand, new BySuitInValueOrder());
    }
    
    @Override
    public int compareTo(Hand otherHand) {
        int thisValue = this.hand.stream()           //these get the sums
                .map( card -> card.getValue())
                .reduce(0, (a, b) -> a + b);
        int otherValue = otherHand.hand.stream()
                .map( card -> card.getValue())
                .reduce(0, (a, b) -> a + b);
        
        return thisValue - otherValue;
    }
}