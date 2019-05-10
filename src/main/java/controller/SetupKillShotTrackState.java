package controller;

public class SetupKillShotTrackState extends State{

    @Override
    public boolean setNumberOfSkulls(int n) {
        controller.getGame().setKillShotTrack(n);
    }

    public void nextState(){
        controller.setNextState(controller.firstTurn);
    }
}
