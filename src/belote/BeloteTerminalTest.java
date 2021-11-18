package belote;

import game.State;
import game.TerminalTest;

public class BeloteTerminalTest extends TerminalTest {
    @Override
    public boolean isTerminal(State state) {
        BeloteState beloteState = (BeloteState) state;
        boolean isTerminal = beloteState.getApplicableActions().isEmpty();
        if(isTerminal){
            utilities.put(state, beloteState.getScore());
        }

        return isTerminal;
    }
}
