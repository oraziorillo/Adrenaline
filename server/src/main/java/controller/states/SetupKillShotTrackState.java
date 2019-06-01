package controller.states;

import controller.Controller;

/**
 * State to Set up the KillshotTrack
 */
public class SetupKillShotTrackState extends State{

    private int killShotTrackIndex;
    
    SetupKillShotTrackState(Controller controller) {
        super(controller);
        this.killShotTrackIndex = -1;
    }
    
    /**
     * Prepares the number of skulls desired
     * @param n the number of skulls
     */
    @Override
    public void selectNumberOfSkulls(int n) {
        if (n > 4 && n < 9) {
            this.killShotTrackIndex = n;
        }
    }
    
    /**
     * Inits the killshottrack with the pre-selected number of skulls, if this number is non-negative
     * @return false if a negative number of skulls was setted, true else
     */
    @Override
    public boolean ok() {
        if (killShotTrackIndex >= 0){
            controller.getGame().initKillShotTrack(killShotTrackIndex);
            return true;
        }
        return false;
    }
    
    /**
     * Transition
     * @return PcSelectionState
     */
    @Override
    public State nextState(){
        return new PcSelectionState(controller);
    }
}
