package common.events.kill_shot_track_events;

import common.dto_model.KillShotTrackDTO;

import static common.Constants.FINAL_FRENZY;

public class FinalFrenzyEvent extends KillShotTrackEvent {

    private int eventID = FINAL_FRENZY;


    public FinalFrenzyEvent(KillShotTrackDTO killShotTrack){
        super(killShotTrack);
    }


    public String toString() {
        return "FINAL FRENZY!";
    }
}
