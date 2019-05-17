package controller.states;

import controller.Controller;

public class SetupKillShotTrackState extends State{

    SetupKillShotTrackState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean setNumberOfSkulls(int n) {
        controller.getGame().initKillShotTrack(n);
        return true;
    }

    public State nextState(){
        return new PcChoiceState(controller);
    }
}
