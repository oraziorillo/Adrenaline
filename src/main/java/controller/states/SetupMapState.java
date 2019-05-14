package controller.states;

import controller.Controller;

public class SetupMapState extends State{

    SetupMapState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean initializeMap(int n) {
        controller.getGame().initMap(n);
        return true;
    }

    @Override
    public State nextState() {
        return new SetupKillShotTrackState(controller);
    }


}
