package game;

import java.util.Set;

public interface State {
	Player getPlayer();
	Set<Action> getApplicableActions();
	State getActionResult(Action action);
	boolean equals(Object that);
	int hashCode();
}
