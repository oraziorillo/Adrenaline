package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.DEATH;

public class DeathEvent extends PcEvent {

    private PcDTO deadPc;


    public DeathEvent(PcDTO deadPc){
        this.deadPc = deadPc;
    }


    @Override
    public String toString() {
        return deadPc.getName() + " dead";
    }


    @Override
    public PcDTO getNewValue() {
        return deadPc;
    }


    @Override
    public String getPropertyName() {
        return DEATH;
    }


}
