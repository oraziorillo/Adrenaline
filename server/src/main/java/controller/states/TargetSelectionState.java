package controller.states;

import controller.Controller;

public class TargetSelectionState extends State{

    
    TargetSelectionState(Controller controller) {
        super(controller);
    }


    @Override
    public State nextState() {
        return null;
    }
}
