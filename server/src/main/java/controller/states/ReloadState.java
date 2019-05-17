package controller.states;

import controller.Controller;

public class ReloadState extends State {
    ReloadState(Controller controller) {
        super(controller);
    }

    @Override
    public State nextState() {
        return null;
    }
}
