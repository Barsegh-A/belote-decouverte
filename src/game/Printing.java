package game;

import java.util.Map;

public abstract class Printing {
	public void printStrategy(Map<State, Action> strategy) {
		for (Map.Entry<State, Action> e : strategy.entrySet()) {
			print(e.getKey());
			System.out.println();
			print(e.getValue());
			System.out.println();
			System.out.println();
		}
	}
	public abstract void print(Action action);
	public abstract void print(State state);

	public void printOptimalPlay(State state, TerminalTest terminalTest, Map<State, Action> strategy){
		while (!terminalTest.isTerminal(state)){
			Action a = strategy.get(state);
			System.out.println(state.getPlayer() + ": " + a);
			state = state.getActionResult(a);
		}
	}
}
