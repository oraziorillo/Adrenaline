package server.controller.states;

import server.controller.Controller;

public class InactiveState extends State {


    private boolean isGameStarted;


    InactiveState(Controller controller, boolean isGameStarted) {
        super(controller);
        this.isGameStarted = isGameStarted;
    }


    public boolean isInactive(){
        return true;
    }


    @Override
    public State nextState() {
        if (isGameStarted)
            return new StartTurnState(controller);
        else
            return new PcSelectionState(controller);
    }
}
