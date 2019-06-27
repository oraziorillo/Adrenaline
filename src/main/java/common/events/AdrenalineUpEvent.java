package common.events;

import common.dto_model.PcDTO;

import static common.Constants.ADRENALINE_UP;

public class AdrenalineUpEvent extends ModelEvent {

    private PcDTO pc;


    public AdrenalineUpEvent(PcDTO pc){
        this.pc = pc;
    }


    @Override
    public String toString() {
        return pc.getName() + "'s adrenaline level increased to " + pc.getAdrenaline() + "!";
    }


    @Override
    public Object getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return ADRENALINE_UP;
    }
}
