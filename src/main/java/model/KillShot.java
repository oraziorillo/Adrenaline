package model;

import model.enumerations.PcColourEnum;

public class KillShot {
    private PcColourEnum colour;
    private boolean skulled;
    private boolean overkilled;

    public KillShot(){
        colour = null;
        skulled = true;
        overkilled = false;
    }

    public void kill(PcColourEnum killer, boolean overkill) {
        if (skulled) {
            this.colour = killer;
            this.overkilled = overkill;
            this.skulled = false;
        } else {
            throw new IllegalStateException("Can't mark two times the same killshot tab");
        }
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
}
