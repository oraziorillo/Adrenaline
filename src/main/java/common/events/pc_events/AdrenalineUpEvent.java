package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.ADRENALINE_UP;

public class AdrenalineUpEvent extends PcEvent {

    private PcDTO pc;


    public AdrenalineUpEvent(PcDTO pc){
        this.pc = pc;
    }


    @Override
    public String toString() {
        return pc.getName() + "'s adrenaline level increased to " + pc.getAdrenaline() + "!";
    }


    @Override
    public PcDTO getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return ADRENALINE_UP;
    }
}
