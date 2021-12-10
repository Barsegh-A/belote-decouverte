package belote;

import game.Action;
import game.Printing;
import game.State;

public class BelotePrinting extends Printing {

    @Override
    public void print(Action action) {
        System.out.println(action);
    }

    @Override
    public void print(State state) {
        System.out.println(state);
    }
}
