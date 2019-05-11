package controller;

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
    public void nextState() {
        controller.setCurrState(controller.setupKillShotTrackState);
    }


}
