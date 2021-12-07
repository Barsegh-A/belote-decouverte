package game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MinimaxSearchVersionTwo implements Search {
    private int generatedStates = 0;
    private Map<State, Integer> minimaxValues = new HashMap<>();


    public Map<State, Action> findStrategy(State initialState, TerminalTest terminalTest) {
        minimaxValues.clear();
        generatedStates = 0;
        Map<State, Action> strategy = new LinkedHashMap<State, Action>();
        maxValue(initialState, terminalTest, strategy);
        return strategy;
    }

    private int maxValue(State state, TerminalTest terminalTest, Map<State, Action> strategy) {
        if (minimaxValues.containsKey(state)) {
            return minimaxValues.get(state);
        }
        generatedStates++;
        if (terminalTest.isTerminal(state)) {
            int utility = terminalTest.utility(state);
            minimaxValues.put(state, utility);
            return utility;
        }
        int v = Integer.MIN_VALUE;
        Action move = null;
        for (Action a: state.getApplicableActions()) {
            State nextState = state.getActionResult(a);
            Player nextPlayer = nextState.getPlayer();
            int v2;

            if(nextPlayer == Player.MAX){
                v2 = maxValue(nextState, terminalTest, strategy);
            }else {
                v2 = minValue(nextState, terminalTest, strategy);
            }
            if (v2 > v) {
                v = v2;
                move = a;
            }
        }
        minimaxValues.put(state, v);
        strategy.put(state, move);
        return v;
    }

    private int minValue(State state, TerminalTest terminalTest, Map<State, Action> strategy) {
        if (minimaxValues.containsKey(state)) {
            return minimaxValues.get(state);
        }
        generatedStates++;
        if (terminalTest.isTerminal(state)) {
            int utility = terminalTest.utility(state);
            minimaxValues.put(state, utility);
            return utility;
        }
        int v = Integer.MAX_VALUE;
        Action move = null;

        for (Action a: state.getApplicableActions()) {
            State nextState = state.getActionResult(a);
            Player nextPlayer = nextState.getPlayer();
            int v2;

            if(nextPlayer == Player.MAX){
                v2 = maxValue(nextState, terminalTest, strategy);
            }else {
                v2 = minValue(nextState, terminalTest, strategy);
            }

            if (v2 < v) {
                v = v2;
                move = a;
            }
        }
        minimaxValues.put(state, v);
        strategy.put(state, move);
        return v;
    }

    @Override
    public int getNumberOfStates() {
        return generatedStates;
    }
}
