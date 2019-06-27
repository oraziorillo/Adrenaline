package common.events;

import common.dto_model.PcDTO;

import static common.Constants.SPAWN;

public class SpawnEvent extends ModelEvent {

    private PcDTO pc;


    public SpawnEvent(PcDTO pc){
        this.pc = pc;
    }


    @Override
    public String toString() {
        return pc.getName() + " spawned on " + pc.getCurrSquare();
    }


    @Override
    public Object getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return SPAWN;
    }
}
