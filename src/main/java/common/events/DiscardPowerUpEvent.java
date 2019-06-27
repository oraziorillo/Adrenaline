package common.events;

import common.dto_model.PowerUpCardDTO;

public class DiscardPowerUpEvent extends ModelEvent {

    private PowerUpCardDTO powerUp;
    private String pc;


    public DiscardPowerUpEvent(PowerUpCardDTO powerUp, String pc){
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
}
