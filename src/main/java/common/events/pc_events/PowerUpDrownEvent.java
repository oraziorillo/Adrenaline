package common.events.pc_events;

import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import static common.Constants.POWER_UP_DROWN;

public class PowerUpDrownEvent extends PcEvent {

    private int eventID = POWER_UP_DROWN;
    private PowerUpCardDTO powerUp;


    public PowerUpDrownEvent(PcDTO pc, PowerUpCardDTO powerUp){
        super(pc);
        this.powerUp = powerUp;
    }


    private PowerUpDrownEvent(PcDTO pc, PowerUpCardDTO powerUp, boolean isPrivate){
        super(pc, isPrivate);
        this.powerUp = powerUp;
    }


    @Override
    public String getDynamicMessage() {
        return isUncensored
                ? "You draw a " + powerUp.getColour() + " " + powerUp.getName()
                : "";
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new PowerUpDrownEvent(getCensoredDTO(), powerUp, true);
    }
}
