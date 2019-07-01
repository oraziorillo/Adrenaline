package common.events.pc_events;

import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;

import static common.Constants.POWER_UP_DISCARDED;

public class PowerUpDiscardedEvent extends PcEvent {

    private int eventID = POWER_UP_DISCARDED;
    private PowerUpCardDTO powerUp;


    public PowerUpDiscardedEvent(PcDTO pc, PowerUpCardDTO powerUp){
        super(pc);
        this.powerUp = powerUp;
    }


    private PowerUpDiscardedEvent(PcDTO pc, PowerUpCardDTO powerUp, boolean censored){
        super(pc, censored);
        this.powerUp = powerUp;
    }


    @Override
    public String toString() {
        return (censored
                ? pc.getName()
                : "You")
                    + " discarded a " + powerUp.getColour() + " " + powerUp.getName();
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new PowerUpDiscardedEvent(getCensoredDTO(), powerUp, true);
    }
}
