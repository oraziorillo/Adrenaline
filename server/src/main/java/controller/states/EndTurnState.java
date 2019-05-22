package controller.states;

import controller.Controller;
import model.squares.Square;

public class EndTurnState extends State {

    private Boolean toReload;

    EndTurnState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean reload() {
        toReload = true;
        return true;
    }

    @Override
    public boolean pass() {
        controller.getSquaresToRefill().forEach(Square::refill);
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
