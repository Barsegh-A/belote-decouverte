package game;

import java.util.LinkedHashMap;
import java.util.Map;

public class MinimaxSearch implements Search{
	private int numberOfStates = 0;
	private Map<State, Integer> minimaxValues;
	private boolean isOptimized;

	public MinimaxSearch(){
		this(true);
	}

	public MinimaxSearch(boolean isOptimized){
		this.isOptimized = isOptimized;
	}

	public Map<State, Action> findStrategy(State initialState, TerminalTest terminalTest) {
		Map<State, Action> strategy = new LinkedHashMap<State, Action>();
		minimaxValues = new LinkedHashMap<>();
		maxValue(initialState, terminalTest, strategy);
		return strategy;
	}

	public int maxValue(State state, TerminalTest terminalTest, Map<State, Action> strategy){
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
				v2 = maxValue(nextState, terminalTest, strategy);
			}else {
				v2 = minValue(nextState, terminalTest, strategy);
			}

			if(v2 > v){
				v = v2;
				move = a;
			}
		}

		strategy.put(state, move);

		if(isOptimized){
			minimaxValues.put(state, v);
		}

		return v;
	}

	public int minValue(State state, TerminalTest terminalTest, Map<State, Action> strategy){
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
				v2 = maxValue(nextState, terminalTest, strategy);
			}else {
				v2 = minValue(nextState, terminalTest, strategy);
			}

			if(v2 < v){
				v = v2;
				move = a;
			}
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
