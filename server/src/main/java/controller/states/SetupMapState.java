package controller.states;

import controller.Controller;

public class SetupMapState extends State{

    private int mapIndex;

    public SetupMapState(Controller controller) {
        super(controller);
        this.mapIndex = -1;
    }

    @Override
    public void selectMap(int n) {
        this.mapIndex = n;
    }


    @Override
    public boolean ok() {
        if (mapIndex >= 0) {
            controller.getGame().initMap(mapIndex);
            return true;
        }
        return false;
    }


    @Override
    public State nextState() {
        return new SetupKillShotTrackState(controller);
    }


}
