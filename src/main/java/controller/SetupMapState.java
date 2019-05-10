package controller;

public class SetupMapState extends State{

    @Override
    public boolean initializeMap(int n) {
        controller.getGame().initMap(n);
        return true;
    }

    @Override
    public void nextState() {
        controller.setCurrState(controller.setupKillShotTrack);
    }


}
