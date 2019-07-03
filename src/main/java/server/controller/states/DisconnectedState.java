package server.controller.states;

import server.controller.Controller;

public class DisconnectedState extends State {



    DisconnectedState(Controller controller) {
        super(controller);
    }


    @Override
    public void selectPowerUp(int index) {

    }

    @Override
    public State nextState() {
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
