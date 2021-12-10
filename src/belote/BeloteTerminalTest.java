package belote;

import game.State;
import game.TerminalTest;

import static belote.Constants.NUMBER_OF_SUITS;


public class BeloteTerminalTest extends TerminalTest {

    private boolean winLose;

    private boolean applyForwardPruning = true;

    public BeloteTerminalTest(){}

    public BeloteTerminalTest(boolean winLose){
        this.winLose = winLose;
    }

    @Override
    public boolean isTerminal(State state) {
        BeloteState beloteState = (BeloteState) state;
        boolean isTerminal = beloteState.getApplicableActions().isEmpty();
        if(isTerminal){
            int threshold = 20 + NUMBER_OF_SUITS * 15;
            boolean madeContract =  beloteState.getScore() >= threshold;
            if(winLose){
                int result = madeContract ? 1 : 0;
                utilities.put(state, result);
            } else {
                int result = madeContract ? beloteState.getScore() : 0;
                utilities.put(state, result);
            }
        }

        if(applyForwardPruning){ //forward pruning
			int threshold = 20 + NUMBER_OF_SUITS * 15;
			boolean contractNotMade = beloteState.getMinPoints() > threshold + 2;
			if(contractNotMade){
				isTerminal = true;
				utilities.put(state, 0);
			}

			if(winLose){
				if(beloteState.getScore() >= threshold){
					utilities.put(state, 1);
					isTerminal = true;
				}
			}
        }

        return isTerminal;
    }
}
