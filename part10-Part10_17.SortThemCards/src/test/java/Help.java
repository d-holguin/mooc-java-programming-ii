

public class Help {

    public static Suit m(int m) {
        if (m == 0) {
            return Suit.CLUB;
        }

        if (m == 1) {
            return Suit.DIAMOND;
        }

        if (m == 2) {
            return Suit.HEART;
        }

        return Suit.SPADE;

    }

}
