package common.events.pc_events;

import common.dto_model.PcDTO;

public class PcColourChosenEvent extends PcEvent{


    public PcColourChosenEvent(PcDTO pc) {
        super(pc);
    }


    private PcColourChosenEvent(PcDTO pc, boolean isUncensored) {
        super(pc, isUncensored);
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new PcColourChosenEvent(pc, true);
    }


    @Override
    public String getDynamicMessage() {
        return pc.getName() + " chosen";
    }
}
