package common.dto_model;

import common.enums.PcColourEnum;

public class KillShotDTO implements DTO {

    private PcColourEnum colour;
    private boolean skulled;
    private boolean overkilled;

    public PcColourEnum getColour() {
        return colour;
    }

    public void setColour(PcColourEnum colour) {
        this.colour = colour;
    }

    public boolean isSkulled() {
        return skulled;
    }

    public void setSkulled(boolean skulled) {
        this.skulled = skulled;
    }

    public boolean isOverkilled() {
        return overkilled;
    }

    public void setOverkilled(boolean overkilled) {
        this.overkilled = overkilled;
    }
}
