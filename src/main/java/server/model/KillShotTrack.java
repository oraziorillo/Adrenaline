package server.model;

import common.enums.PcColourEnum;

public class KillShotTrack {

    private KillShot[] killShotTrack;
    private int currentKillShotTrackIndex;
    private KillShot [] finalFrenzyKillShotTrack;
    private int finalFrenzyCurrentKillShotTrackIndex;


    /**
     * Inits the kill shot track with the given number of skulls
     * @param numberOfSkulls the desired number of skulls
     */
    public KillShotTrack(int numberOfSkulls){
        this.killShotTrack = new KillShot[numberOfSkulls];
        this.currentKillShotTrackIndex = numberOfSkulls - 1;
        for(int i = 0; i < numberOfSkulls; i++)
            killShotTrack[i] = new KillShot();
        this.finalFrenzyKillShotTrack = new KillShot[5];
        this.finalFrenzyCurrentKillShotTrackIndex = 0;
        for (int i = 0; i < 5; i++)
            finalFrenzyKillShotTrack[i] = new KillShot();
        System.out.println("killshottrack built");
    }

    public KillShot[] getFinalFrenzyKillShotTrack() {
        return finalFrenzyKillShotTrack;
    }


    public KillShot[] getKillShotTrack() {
        return killShotTrack;
    }

    /**
     * Updates the KillshotTrack with the occurred kill
     * @param killerColour the colour of the killer
     * @param overkilled true if the player was overkilled, see the manual
     * @return True if the game turns into or already is in Final Frenzy mode
     */
    public boolean killOccured(PcColourEnum killerColour, Boolean overkilled){
        if (currentKillShotTrackIndex >= 0) {
            killShotTrack[currentKillShotTrackIndex].killOccurred(killerColour, overkilled);
            currentKillShotTrackIndex--;
            if (currentKillShotTrackIndex == -1)
                return true;
        } else {
            finalFrenzyKillShotTrack[finalFrenzyCurrentKillShotTrackIndex].killOccurred(killerColour, overkilled);
            finalFrenzyCurrentKillShotTrackIndex++;
            return true;
        }
        return false;
    }
}
