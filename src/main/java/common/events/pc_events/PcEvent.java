package common.events.pc_events;

import common.dto_model.PcDTO;
import common.events.ModelEvent;

public abstract class PcEvent implements ModelEvent {

    PcDTO pc;
    boolean isUncensored;


    PcEvent(PcDTO pc) {
        this.pc = pc;
    }


    PcEvent(PcDTO pc, boolean isUncensored) {
        this.pc = pc;
        this.isUncensored = isUncensored;
    }


    @Override
    public PcDTO getDTO() {
        return pc;
    }


    PcDTO getCensoredDTO() {
        PcDTO censored = new PcDTO();
        censored.setColour(pc.getColour());
        censored.setCurrSquare(pc.getCurrSquare());
        censored.setAdrenaline(pc.getAdrenaline());
        censored.setPcBoard(pc.getPcBoard());
        return censored;
    }


    public abstract PcEvent hideSensibleContent();
}
