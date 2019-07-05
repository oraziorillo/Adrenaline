package common.dto_model;

public class KillShotTrackDTO implements DTO {

    private KillShotDTO[] killShotTrack;
    private KillShotDTO [] finalFrenzyKillShotTrack;

    public KillShotDTO[] getKillShotTrack() {
        return killShotTrack;
    }

    public void setKillShotTrack(KillShotDTO[] killShotTrack) {
        this.killShotTrack = killShotTrack;
    }

    public KillShotDTO[] getFinalFrenzyKillShotTrack() {
        return finalFrenzyKillShotTrack;
    }

    public void setFinalFrenzyKillShotTrack(KillShotDTO[] finalFrenzyKillShotTrack) {
        this.finalFrenzyKillShotTrack = finalFrenzyKillShotTrack;
    }
}
