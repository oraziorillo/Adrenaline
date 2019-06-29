package common.events.killshottrack_events;

import common.dto_model.KillShotTrackDTO;
import common.events.ModelEvent;

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
    public KillShotTrackDTO getNewValue() {
        return killShotTrack;
    }


    @Override
    public String getPropertyName() {
        return KILL_SHOT_TRACK_SET;
    }
}
