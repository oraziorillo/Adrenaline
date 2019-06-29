package common.dto_model;

import server.model.KillShot;

public class KillShotTrackDTO implements DTO {

    private KillShot[] killShotTrack;
    private KillShot [] finalFrenzyKillShotTrack;

    public KillShot[] getKillShotTrack() {
        return killShotTrack;
    }

    public void setKillShotTrack(KillShot[] killShotTrack) {
        this.killShotTrack = killShotTrack;
    }

    public KillShot[] getFinalFrenzyKillShotTrack() {
        return finalFrenzyKillShotTrack;
    }

    public void setFinalFrenzyKillShotTrack(KillShot[] finalFrenzyKillShotTrack) {
        this.finalFrenzyKillShotTrack = finalFrenzyKillShotTrack;
    }
}
