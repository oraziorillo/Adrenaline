package common.dto_model;

import java.util.Arrays;

public class KillShotTrackDTO implements DTO {

    private KillShotDTO[] killShotTrack;
    private KillShotDTO[] finalFrenzyKillShotTrack;

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

    @Override
    public String toString() {
        if (killShotTrack[0].isSkulled()) {
            return Arrays.toString(killShotTrack);
        } else
            return "\n" + Arrays.toString(killShotTrack) + "\n" + Arrays.toString(finalFrenzyKillShotTrack);
    }
}
