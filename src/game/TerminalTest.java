package game;

import belote.BeloteState;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class TerminalTest {
	protected final Map<State, Integer> utilities;

	public TerminalTest() {
		utilities = new LinkedHashMap<>();
	}
	public int utility(State state) {
		((BeloteState) state).removeEmptyStacks();
		return utilities.get(state);
	}
	public abstract boolean isTerminal(State state);
}
