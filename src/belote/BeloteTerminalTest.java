package belote;

import game.State;
import game.TerminalTest;

import static belote.Constants.THRESHOLD;


public class BeloteTerminalTest extends TerminalTest {

    private boolean winLose;
    private boolean applyForwardPruning;

    public BeloteTerminalTest(){}

    public BeloteTerminalTest(boolean winLose, boolean applyForwardPruning){
        this.winLose = winLose;
        this.applyForwardPruning = applyForwardPruning;
    }

    @Override
    public boolean isTerminal(State state) {
        BeloteState beloteState = (BeloteState) state;
        boolean isTerminal = beloteState.getApplicableActions().isEmpty();
        if(isTerminal){
            boolean madeContract =  beloteState.getScore() >= THRESHOLD;
            if(winLose){
                int result = madeContract ? 1 : 0;
                utilities.put(state, result);
            } else {
                int result = madeContract ? beloteState.getScore() : 0;
                utilities.put(state, result);
            }
        }

        if(applyForwardPruning){
			boolean contractNotMade = beloteState.getMinPoints() > THRESHOLD + 2;
			// if Max does not make the contract, Min gets all point in both cases of utilities
			if(contractNotMade){
				isTerminal = true;
				utilities.put(state, 0);
			}

			// moreover, in case of Win/Lose, pruning is done when Max has already won more points than the threshold
			if(winLose){
				if(beloteState.getScore() >= THRESHOLD){
					utilities.put(state, 1);
					isTerminal = true;
				}
			}
        }

        return isTerminal;
    }
}
