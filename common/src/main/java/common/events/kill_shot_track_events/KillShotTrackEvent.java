package common.events.kill_shot_track_events;

import common.dto_model.KillShotTrackDTO;
import common.events.ModelEvent;

public abstract class KillShotTrackEvent implements ModelEvent {

    KillShotTrackDTO killShotTrack;


    KillShotTrackEvent(KillShotTrackDTO killShotTrack){
        this.killShotTrack = killShotTrack;
    }


    @Override
    public KillShotTrackDTO getDTO(){
        return killShotTrack;
    }
}
