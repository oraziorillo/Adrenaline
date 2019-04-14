package model;

import model.Enumerations.PcColourEnum;

public class Killshot {
    private PcColourEnum colour;
    private boolean skulled = true;
    private boolean overkilled;

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
