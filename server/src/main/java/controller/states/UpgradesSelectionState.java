package controller.states;

import controller.Controller;

public class UpgradesSelectionState extends State {

    UpgradesSelectionState(Controller controller) {
        super(controller);
    }

    //TODO controllare costo

    @Override
    public State nextState() {
        return null;
    }
}
