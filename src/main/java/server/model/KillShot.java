package server.model;

import server.enums.PcColourEnum;

/**
 * A single kill marker
 */
public class KillShot {
    private PcColourEnum colour;
    private boolean skulled;
    private boolean overkilled;

    public KillShot(){
        colour = null;
        skulled = true;
        overkilled = false;
    }

    public boolean isSkulled() {
        return skulled;
    }

    public boolean isOverkilled() {
        return overkilled;
    }

    public PcColourEnum getColour() {
        return colour;
    }
    
    /**
     * Marks a kill
     * @param killerColour colour of the killer
     * @param overkill see the game anual
     */
    public void killOccured(PcColourEnum killerColour, boolean overkill) {
        if (skulled) {
            this.colour = killerColour;
            this.overkilled = overkill;
            this.skulled = false;
        } else {
            throw new IllegalStateException("Can't mark two times the same killshot tab");
        }
    }
}
