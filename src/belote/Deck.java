package belote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private final int SEED = 1;

    // TODO make private
    public ArrayList<Card> deck = new ArrayList<>();

    public Deck(){
        for(Suit s: Suit.values()){
//            if(s != Suit.DIAMONDS) continue;
            for(CardType t: CardType.values()){
                Card card = new Card(s, t);
                deck.add(card);
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck, new Random(SEED));
    }

}
