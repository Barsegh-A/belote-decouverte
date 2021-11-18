package belote;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

    // First card is the lead card
    @Override
    public int compare(Card c1, Card c2) {
        // Both are trumps
        if(c1.isTrump() && c2.isTrump()){
            return c1.getType().trumpOrder - c2.getType().trumpOrder;
        }

        // only the lead is a trump
        if(c1.isTrump()){
            return 1;
        }

        //only the second card is a trump
        if(c2.isTrump()){
            return -1;
        }

        //both are not trumps and have different suits
        if(c1.getSuit() != c1.getSuit()) {
            return 1;
        }

        // both are not trumps and have the same suits
        return c1.getType().noTrumpOrder - c2.getType().noTrumpOrder;
    }
}
