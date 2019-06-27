package common.events;

import common.dto_model.KillShotTrackDTO;

import static common.Constants.KILL_SHOT_TRACK_SET;

public class KillShotTrackSetEvent extends ModelEvent {

    private KillShotTrackDTO killShotTrack;


    public KillShotTrackSetEvent(KillShotTrackDTO killShotTrack){
        this.killShotTrack = killShotTrack;
    }


    @Override
    public String toString() {
        return killShotTrack.getKillShotTrack().length + " kills to win";
    }


    @Override
    public Object getNewValue() {
        return killShotTrack;
    }


    @Override
    public String getPropertyName() {
        return KILL_SHOT_TRACK_SET;
    }
}
