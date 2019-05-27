package controller.states;

import controller.Controller;

public class UsingPowerUpState extends State {

    UsingPowerUpState(Controller controller) {
        super(controller);
    }

    @Override
    public State nextState() {
        return null;
    }
}
