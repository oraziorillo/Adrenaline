package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.ADRENALINE_UP;

public class AdrenalineUpEvent extends PcEvent {

    private int eventID = ADRENALINE_UP;


    public AdrenalineUpEvent(PcDTO pc) {
        super(pc);
    }


    private AdrenalineUpEvent(PcDTO censored, boolean isPrivate) {
        super(censored, isPrivate);
    }


    @Override
    public String getDynamicMessage() {
        return isUncensored
                ? "You got adrenaline level " + pc.getAdrenaline()
                : "";
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new AdrenalineUpEvent(getCensoredDTO(), true);
    }
}
