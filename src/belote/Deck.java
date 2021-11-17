package belote;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    // TODO make private
    public ArrayList<Card> deck = new ArrayList<>();

    public Deck(){
        for(Suit s: Suit.values()){
            for(CardType t: CardType.values()){
                Card card = new Card(s, t);
                deck.add(card);
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

}
