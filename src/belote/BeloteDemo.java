package belote;

import game.*;

import java.util.*;

import static belote.Constants.*;

public class BeloteDemo {

    public static void main(String[] args) {

        BeloteState beloteState = deal(NUMBER_OF_SUITS);


        Search minimaxSearch = new MinimaxSearch();
        Search alphaBetaSearch = new AlphaBetaSearch();
        BelotePrinting printing = new BelotePrinting();

        System.out.println(beloteState);

        TerminalTest terminalTest1 = new BeloteTerminalTest(WIN_LOSE, USE_FORWARD_PRUNING);
        Map<State, Action> minimaxStrategy = solve(minimaxSearch, terminalTest1, beloteState);

        System.out.println();
        System.out.println("#### Minimax Search on " + NUMBER_OF_SUITS + " suit deck.");
        printStats(minimaxSearch, minimaxStrategy);
        System.out.println("Minimax Value for the initial state: " + minimaxSearch.getValue(beloteState));
        System.out.println("Average Branching Factor: " + minimaxSearch.getAverageBranchingFactor());

        TerminalTest terminalTest2 = new BeloteTerminalTest(WIN_LOSE, USE_FORWARD_PRUNING);
        Map<State, Action> alphaBetaStrategy = solve(alphaBetaSearch, terminalTest2, beloteState);

        System.out.println();
        System.out.println("#### Alpha-Beta Search on " + NUMBER_OF_SUITS + " suit deck.");
        printStats(alphaBetaSearch, alphaBetaStrategy);
        System.out.println("Minimax Value for the initial state: " + alphaBetaSearch.getValue(beloteState));
        System.out.println("Average Branching Factor: " + alphaBetaSearch.getAverageBranchingFactor());

        System.out.println();
        System.out.println("A sequence of actions for a game when MIN and MAX play optimally:");
        printing.printOptimalPlay(beloteState, terminalTest2, alphaBetaStrategy);


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
        List<CardStack> maxHand = new ArrayList<>();
        List<CardStack> minHand = new ArrayList<>();


        for (int i = 0; i < 8*numberOfSuits; i += 4) {
            Card bottomCard = deck.getDeck().get(i);
            Card topCard = deck.getDeck().get(i + 1);
            CardStack cardStack = new CardStack(topCard, bottomCard);
            maxHand.add(cardStack);
            bottomCard = deck.getDeck().get(i + 2);
            topCard = deck.getDeck().get(i + 3);
            cardStack = new CardStack(topCard, bottomCard);
            minHand.add(cardStack);
        }

        BeloteState beloteState = new BeloteState(Player.MAX, maxHand, minHand, trick, 0, 0);

        return beloteState;
    }
}
