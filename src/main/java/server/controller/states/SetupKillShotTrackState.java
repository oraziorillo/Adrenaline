package server.controller.states;

import server.controller.Controller;

import java.io.IOException;

import static common.Constants.MAX_KILL_SHOT_TRACK_SIZE;
import static common.Constants.MIN_KILL_SHOT_TRACK_SIZE;

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
        if (n >= MIN_KILL_SHOT_TRACK_SIZE && n <= MAX_KILL_SHOT_TRACK_SIZE) {
            this.killShotTrackIndex = n;
        }
    }


    /**
     * Inits the kill shot track with the pre-selected number of skulls, if this number is non-negative
     * @return false if a negative number of skulls was set, true else
     */
    @Override
    public boolean ok() {
        if (killShotTrackIndex >= MIN_KILL_SHOT_TRACK_SIZE && killShotTrackIndex <= MAX_KILL_SHOT_TRACK_SIZE){
            controller.getGame().initKillShotTrack(killShotTrackIndex);
            try {
                controller.getCurrPlayer().getView().ack("killShot set correctly");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
