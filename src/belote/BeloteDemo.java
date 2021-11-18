package belote;

import dots_and_boxes.Board;
import dots_and_boxes.BoardTerminalTest;
import game.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeloteDemo {

    public static void main(String[] args) {
        BeloteState beloteState = deal();

        TerminalTest terminalTest = new BeloteTerminalTest();
        Search search = new MinimaxSearch(false);
        Map<State, Action> strategy = search.findStrategy(beloteState, terminalTest);
        System.out.println(strategy);
//        Set<Action> cards = beloteState.getApplicableActions();
//        Iterator<Action> iterator = cards.iterator();
//        iterator.next();
//        iterator.next();
//        Card card = (Card) iterator.next();
//        System.out.println(card);
//        beloteState = (BeloteState) beloteState.getActionResult(card);

//        System.out.println(beloteState);
//        System.out.println(beloteState.getApplicableActions());
    }


    public static BeloteState deal(){
        Deck deck = new Deck();
        deck.shuffle();

        Trick trick = new Trick(Player.MAX);
        Set<Card> unknownCards = new HashSet<>();
        Set<CardStack> maxHand = new HashSet<>();
        Set<CardStack> minHand = new HashSet<>();


        for(int i = 0; i < 32; i+=4){
            Card bottomCard = deck.deck.get(i);
            Card topCard = deck.deck.get(i+1);
            unknownCards.add(bottomCard);
            CardStack cardStack = new CardStack(topCard, bottomCard);
            maxHand.add(cardStack);
            bottomCard = deck.deck.get(i+2);
            topCard = deck.deck.get(i+3);
            unknownCards.add(bottomCard);
            cardStack = new CardStack(topCard, bottomCard);
            minHand.add(cardStack);
        }

        BeloteState beloteState = new BeloteState(Player.MAX, maxHand, minHand, unknownCards, trick, 0);
//        System.out.println(beloteState);

        return beloteState;
    }
}
