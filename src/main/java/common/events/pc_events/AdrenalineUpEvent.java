package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.ADRENALINE_UP;

public class AdrenalineUpEvent extends PcEvent {

    private int eventID = ADRENALINE_UP;


    public AdrenalineUpEvent(PcDTO pc) {
        super(pc);
    }


    private AdrenalineUpEvent(PcDTO censoredPc, boolean censored) {
        super(censoredPc, censored);
    }


    @Override
    public String toString() {
        return censored
                ? ""
                : "You got adrenaline level " + pc.getAdrenaline();
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new AdrenalineUpEvent(pc.getCensoredDTO(), true);
    }
}
