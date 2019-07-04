package common.events.pc_events;

import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;

import static common.Constants.POWER_UP_DROWN;

public class PowerUpDrownEvent extends PcEvent {

    private int eventID = POWER_UP_DROWN;
    private PowerUpCardDTO powerUp1;
    private PowerUpCardDTO powerUp2;


    public PowerUpDrownEvent(PcDTO pc, PowerUpCardDTO powerUp1, PowerUpCardDTO powerUp2){
        super(pc);
        this.powerUp1 = powerUp1;
        this.powerUp2 = powerUp2;

    }


    private PowerUpDrownEvent(PcDTO pc, PowerUpCardDTO powerUp1, PowerUpCardDTO powerUp2, boolean censored){
        super(pc, censored);
        this.powerUp1 = powerUp1;
        this.powerUp2 = powerUp2;
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (!censored) {
            string.append("\nYou drew a ").append(powerUp1.getColour()).append(" ").append(powerUp1.getName()).append((powerUp2 != null)
                    ? " and a " + powerUp2.getColour() + " " + powerUp1.getName()
                    : "")
                    .append(pc.powerUpsToString());
        }
        return string.toString();
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new PowerUpDrownEvent(pc.getCensoredDTO(), powerUp1, powerUp2, true);
    }
}
