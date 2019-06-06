package server.controller.states;

import server.controller.Controller;

public class InactiveState extends State {

    static final int PC_SELECTION_STATE = 0;
    static final int FIRST_TURN_STATE = 1;
    static final int START_TURN_STATE = 2;

    private int nextState;


    InactiveState(Controller controller, int nextState) {
        super(controller);
        this.nextState = nextState;
    }

    @Override
    public boolean isInactive(){
        return true;
    }


    @Override
    public State nextState() {
        switch (nextState){
            case PC_SELECTION_STATE:
                return new PcSelectionState(controller);
            case FIRST_TURN_STATE:
                return new FirstTurnState(controller);
            case START_TURN_STATE:
                return new StartTurnState(controller);
            default:
                return this;
        }
    }
}
