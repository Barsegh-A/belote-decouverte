package belote;

import game.State;
import game.TerminalTest;

import static belote.BeloteDemo.NUMBER_OF_SUITS;

public class BeloteTerminalTest extends TerminalTest {

    private boolean winLose;

    public BeloteTerminalTest(){}

    public BeloteTerminalTest(boolean winLose){
        this.winLose = winLose;
    }

    @Override
    public boolean isTerminal(State state) {
        BeloteState beloteState = (BeloteState) state;
        boolean isTerminal = beloteState.getApplicableActions().isEmpty();
        if(isTerminal){
            if(winLose){
                int threshold = 20 + NUMBER_OF_SUITS * 15;
                int result = beloteState.getScore() >= threshold ? 1 : 0;
                utilities.put(state, result);
            } else {
                utilities.put(state, beloteState.getScore());
            }
        }

        return isTerminal;
    }
}
