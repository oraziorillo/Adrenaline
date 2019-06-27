package common.events;

import common.dto_model.PowerUpCardDTO;

import static common.Constants.POWER_UP_DISCARDED;

public class PowerUpDiscardedEvent extends ModelEvent {

    private PowerUpCardDTO powerUp;
    private String pc;


    public PowerUpDiscardedEvent(PowerUpCardDTO powerUp, String pc){
        this.powerUp = powerUp;
        this.pc = pc;
    }


    @Override
    public String toString() {
        return pc + " discarded a " + powerUp.getColour() + powerUp.getName();
    }


    @Override
    public Object getNewValue() {
        return powerUp;
    }


    @Override
    public String getPropertyName() {
        return POWER_UP_DISCARDED;
    }
}
