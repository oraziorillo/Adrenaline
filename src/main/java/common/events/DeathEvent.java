package common.events;

import common.dto_model.PcDTO;

import static common.Constants.DEATH;

public class DeathEvent extends ModelEvent {

    private PcDTO deadPc;


    public DeathEvent(PcDTO deadPc){
        this.deadPc = deadPc;
    }


    @Override
    public String toString() {
        return deadPc.getName() + " dead";
    }


    @Override
    public Object getNewValue() {
        return deadPc;
    }


    @Override
    public String getPropertyName() {
        return DEATH;
    }


}
