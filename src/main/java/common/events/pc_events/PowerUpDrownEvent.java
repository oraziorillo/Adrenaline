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


    private PowerUpDrownEvent(PcDTO pc, PowerUpCardDTO powerUp, boolean censored){
        super(pc, censored);
        this.powerUp = powerUp;
    }


    @Override
    public String toString() {
        return (censored
                ? ""
                : "You draw a " + powerUp.getColour() + " " + powerUp.getName() + ". Now you have " +
                pc.getPowerUps().size() + " powerUps.");
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new PowerUpDrownEvent(getCensoredDTO(), powerUp, true);
    }
}
