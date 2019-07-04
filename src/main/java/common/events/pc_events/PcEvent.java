package common.events.pc_events;

import common.dto_model.PcDTO;
import common.events.ModelEvent;

public abstract class PcEvent implements ModelEvent {

    PcDTO pc;
    boolean censored;


    PcEvent(PcDTO pc) {
        this.pc = pc;
    }


    PcEvent(PcDTO pc, boolean censored) {
        this.pc = pc;
        this.censored = censored;
    }


    @Override
    public PcDTO getDTO() {
        return pc;
    }

    
    public boolean isCensored() {
        return censored;
    }


    public abstract PcEvent hideSensibleContent();
}
