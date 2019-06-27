package common.events;

import common.dto_model.KillShotTrackDTO;

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
}
