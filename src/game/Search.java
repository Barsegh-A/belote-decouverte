package game;

import java.util.Map;

public interface Search {
    Map<State, Action> findStrategy(State initialState, TerminalTest terminalTest);
    int getNumberOfStates();
}
