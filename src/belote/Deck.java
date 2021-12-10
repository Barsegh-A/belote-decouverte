package belote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static belote.Constants.TRUMP;


public class Deck {
    private final int SEED = 32;

    private int numberOfSuites;
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(){
        this(4);
    }

    public Deck(int numberOfSuites){
        if(numberOfSuites < 1 || numberOfSuites > 4)
            throw new IllegalArgumentException("Number of suits must be between 1 and 4.");

        this.numberOfSuites = numberOfSuites;

        List<Suit> suits = List.of(Suit.values()); //[DIAMONDS, CLUBS, HEARTS, SPADES]

        for(int i = 0; i < numberOfSuites; i++){
            Suit suit = suits.get(i);
            boolean isTrump = suit == TRUMP;
            for(CardType t: CardType.values()){
                Card card = new Card(suit, t, isTrump);
                deck.add(card);
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck, new Random(SEED));
    }


    public ArrayList<Card> getDeck(){
        return deck;
    }
}
