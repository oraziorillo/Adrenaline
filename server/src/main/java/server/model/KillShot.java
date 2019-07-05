package server.model;

import com.google.gson.annotations.Expose;
import common.dto_model.KillShotDTO;
import common.enums.PcColourEnum;

/**
 * A single kill marker
 */
public class KillShot {
    @Expose private PcColourEnum colour;
    @Expose private boolean skulled;
    @Expose private boolean overkilled;

    KillShot(){
        colour = null;
        skulled = true;
        overkilled = false;
    }

    boolean isSkulled() {
        return skulled;
    }

    boolean isOverkilled() {
        return overkilled;
    }

    PcColourEnum getColour() {
        return colour;
    }
    
    /**
     * Marks a kill
     * @param killerColour colour of the killer
     * @param overkill see the game anual
     */
    void killOccurred(PcColourEnum killerColour, boolean overkill) {
        if (skulled) {
            this.colour = killerColour;
            this.overkilled = overkill;
            this.skulled = false;
        } else {
            throw new IllegalStateException("Can't mark two times the same killshot tab");
        }
    }

    public KillShotDTO convertToDTO(){
        KillShotDTO killShotDTO = new KillShotDTO();
        killShotDTO.setColour(colour);
        killShotDTO.setOverkilled(overkilled);
        killShotDTO.setSkulled(skulled);
        return killShotDTO;
    }
}
