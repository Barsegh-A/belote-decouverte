package belote;

import game.*;

import java.util.*;

public class BeloteDemo {

    public static final Suit TRUMP = Suit.DIAMONDS;
    public static final int NUMBER_OF_SUITS = 1;

    public static void main(String[] args) {

        BeloteState beloteState = deal(NUMBER_OF_SUITS);
        TerminalTest terminalTest = new BeloteTerminalTest(true);

        System.out.println(beloteState);

        Search minimaxSearch = new MinimaxSearch(true);
        Map<State, Action> minimaxStrategy = solve(minimaxSearch, terminalTest, beloteState);

        System.out.println("#### Minimax Search on " + NUMBER_OF_SUITS + " suit deck.");
        printStats(minimaxSearch, minimaxStrategy);
        System.out.println("Minimax Value for the initial state: " + minimaxSearch.getValue(beloteState));


        Search alphaBetaSearch = new AlphaBetaSearch(true);
        Map<State, Action> alphaBetaStrategy = solve(alphaBetaSearch, terminalTest, beloteState);

        System.out.println("#### Alpha-Beta Search on " + NUMBER_OF_SUITS + " suit deck.");
        printStats(alphaBetaSearch, alphaBetaStrategy);
        System.out.println("Minimax Value for the initial state: " + alphaBetaSearch.getValue(beloteState));

        /*
        while (!terminalTest.isTerminal(beloteState)){
            Action a = alphaBetaStrategy.get(beloteState);
            System.out.println(beloteState.getPlayer() + ": " + a);
            beloteState = (BeloteState) beloteState.getActionResult(a);
        }

        System.out.println(beloteState.getScore());
        */

    }

    private static Map<State, Action> solve(Search search, TerminalTest terminalTest, BeloteState initialState){
        return search.findStrategy(initialState, terminalTest);
    }

    private static void printStats(Search search, Map<State, Action> strategy){
        System.out.println("Number of states generated during execution: " + search.getNumberOfStates());
        System.out.println("Number of states in strategy: " + strategy.keySet().size());
    }

    private static BeloteState deal(int numberOfSuits) {
        Deck deck = new Deck(numberOfSuits);
        deck.shuffle();

        Trick trick = new Trick(Player.MAX, 1);
        Set<Card> unknownCards = new HashSet<>();
        List<CardStack> maxHand = new ArrayList<>();
        List<CardStack> minHand = new ArrayList<>();


        for (int i = 0; i < 8*numberOfSuits; i += 4) {
            Card bottomCard = deck.getDeck().get(i);
            Card topCard = deck.getDeck().get(i + 1);
            unknownCards.add(bottomCard);
            CardStack cardStack = new CardStack(topCard, bottomCard);
            maxHand.add(cardStack);
            bottomCard = deck.getDeck().get(i + 2);
            topCard = deck.getDeck().get(i + 3);
            unknownCards.add(bottomCard);
            cardStack = new CardStack(topCard, bottomCard);
            minHand.add(cardStack);
        }

        BeloteState beloteState = new BeloteState(Player.MAX, maxHand, minHand, unknownCards, trick, 0);

        return beloteState;
    }
}
