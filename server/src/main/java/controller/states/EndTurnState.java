package controller.states;

import controller.Controller;
import model.Square;

public class EndTurnState extends State {

    Boolean toReload;

    EndTurnState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean reload() {
        toReload = true;
        return true;
    }

    @Override
    public boolean ok() {
        controller.getSquaresToRefill().forEach(s -> s.refill());
        controller.resetSquaresToRefill();
        return true;
    }

    @Override
    public State nextState() {
        if(toReload)
            return new ReloadState(controller);
        return new StartTurnState(controller);
    }
}
