package controller.states;

import controller.Controller;

public class SetupKillShotTrackState extends State{

    private int killShotTrackIndex;

    SetupKillShotTrackState(Controller controller) {
        super(controller);
        this.killShotTrackIndex = -1;
    }

    @Override
    public void selectNumberOfSkulls(int n) {
        if (n > 4 && n < 9) {
            this.killShotTrackIndex = n;
        }
    }

    @Override
    public boolean ok() {
        if (killShotTrackIndex >= 0){
            controller.getGame().initKillShotTrack(killShotTrackIndex);
            return true;
        }
        return false;
    }

    @Override
    public State nextState(){
        return new PcSelectionState(controller);
    }
}
