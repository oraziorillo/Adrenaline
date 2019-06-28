package common.events.pc_events;

import common.dto_model.DTO;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.events.ModelEvent;

import static common.Constants.POWER_UP_DISCARDED;

public class PowerUpDiscardedEvent extends PcEvent {

    private PcDTO pc;
    private PowerUpCardDTO powerUp;


    public PowerUpDiscardedEvent(PcDTO pc, PowerUpCardDTO powerUp){
        this.pc = pc;
        this.powerUp = powerUp;
    }


    @Override
    public String toString() {
        return pc.getName() + " discarded a " + powerUp.getColour() + powerUp.getName();
    }


    @Override
    public PcDTO getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return POWER_UP_DISCARDED;
    }
}
