package game;

import java.util.LinkedHashMap;
import java.util.Map;

public class AlphaBetaSearch implements Search{
	private int numberOfStates;
	private Map<State, Integer> minimaxValues;
	private boolean isOptimized;

	public AlphaBetaSearch(){
		this(true);
	}

	public AlphaBetaSearch(boolean isOptimized){
		this.isOptimized = isOptimized;
	}

	public Map<State, Action> findStrategy(State initialState, TerminalTest terminalTest) {
		numberOfStates = 0;
		Map<State, Action> strategy = new LinkedHashMap<State, Action>();
		minimaxValues = new LinkedHashMap<>();
		maxValue(initialState, terminalTest, strategy, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return strategy;
	}

	public int maxValue(State state, TerminalTest terminalTest, Map<State, Action> strategy, int alpha, int beta){
		if(isOptimized && minimaxValues.containsKey(state)) {
			return minimaxValues.get(state);
		}

		numberOfStates++;

		if(terminalTest.isTerminal(state)){
			int utility = terminalTest.utility(state);
			minimaxValues.put(state, utility);
			return utility;
		}

		int v = Integer.MIN_VALUE;
		Action move = null;

		for(Action a: state.getApplicableActions()){

			State nextState = state.getActionResult(a);
			Player nextPlayer = nextState.getPlayer();
			int v2;

			if(nextPlayer == Player.MAX){
				v2 = maxValue(nextState, terminalTest, strategy, alpha, beta);
			}else {
				v2 = minValue(nextState, terminalTest, strategy, alpha, beta);
			}

			if(v2 > v){
				v = v2;
				move = a;
				alpha = Math.max(alpha, v);
			}
			if(v >= beta) {
				break;
			}
		}

		strategy.put(state, move);
		if(isOptimized){
			minimaxValues.put(state, v);
		}

		return v;
	}

	public int minValue(State state, TerminalTest terminalTest, Map<State, Action> strategy, int alpha, int beta){
		if(isOptimized && minimaxValues.containsKey(state)) {
			return minimaxValues.get(state);
		}

		numberOfStates++;

		if(terminalTest.isTerminal(state)) {
			int utility = terminalTest.utility(state);
			minimaxValues.put(state, utility);
			return utility;
		}

		int v = Integer.MAX_VALUE;
		Action move = null;

		for(Action a: state.getApplicableActions()){
			State nextState = state.getActionResult(a);
			Player nextPlayer = nextState.getPlayer();
			int v2;

			if(nextPlayer == Player.MAX){
				v2 = maxValue(nextState, terminalTest, strategy, alpha, beta);
			}else {
				v2 = minValue(nextState, terminalTest, strategy, alpha, beta);
			}

			if(v2 < v){
				v = v2;
				move = a;
				beta = Math.min(beta, v);
			}
			if(v <= alpha) break;
		}

		strategy.put(state, move);
		if(isOptimized){
			minimaxValues.put(state, v);
		}

		return v;
	}

	public int getNumberOfStates(){
		return numberOfStates;
	}

	public int getValue(State state){
		return minimaxValues.get(state);
	}
}
