package common.events;

import common.dto_model.PcDTO;

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
}
