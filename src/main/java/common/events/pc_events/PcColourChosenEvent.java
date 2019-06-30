package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.PC_COLOUR_CHOSEN;

public class PcColourChosenEvent extends PcEvent{

    private int eventID = PC_COLOUR_CHOSEN;

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
    public String toString() {
        return pc.getName() + " chosen";
    }
}
