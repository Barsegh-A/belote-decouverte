package belote;

import game.*;

import java.util.*;

public class BeloteDemo {

    public static void main(String[] args) {
        BeloteState beloteState = deal();
        Action a = beloteState.getApplicableActions().iterator().next();

        while(a != null){
            beloteState = (BeloteState) beloteState.getActionResult(a);
            a = beloteState.getApplicableActions().iterator().next();
        }


//        TerminalTest terminalTest = new BeloteTerminalTest();
//        Search search = new MinimaxSearch(false);
//        Search alphaBeta = new AlphaBetaSearch(false);
//        Map<State, Action> strategy = search.findStrategy(beloteState, terminalTest);
//
//        System.out.println(alphaBeta.getNumberOfStates());

    }


    public static BeloteState deal() {
        Deck deck = new Deck();
        deck.shuffle();

        Trick trick = new Trick(Player.MAX, 1);
        Set<Card> unknownCards = new HashSet<>();
        List<CardStack> maxHand = new ArrayList<>();
        List<CardStack> minHand = new ArrayList<>();


        for (int i = 0; i < 8; i += 4) {
            Card bottomCard = deck.deck.get(i);
            Card topCard = deck.deck.get(i + 1);
            unknownCards.add(bottomCard);
            CardStack cardStack = new CardStack(topCard, bottomCard);
            maxHand.add(cardStack);
            bottomCard = deck.deck.get(i + 2);
            topCard = deck.deck.get(i + 3);
            unknownCards.add(bottomCard);
            cardStack = new CardStack(topCard, bottomCard);
            minHand.add(cardStack);
        }

        BeloteState beloteState = new BeloteState(Player.MAX, maxHand, minHand, unknownCards, trick, 0);

        return beloteState;
    }
}
