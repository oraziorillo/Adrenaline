package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.COLLECT_EVENT;

public class CollectEvent extends PcEvent {

    private int eventID = COLLECT_EVENT;


    public CollectEvent(PcDTO pc){
        super(pc);
    }


    private CollectEvent(PcDTO pc, boolean isPrivate) {
        super(pc, isPrivate);
    }


    @Override
    public String toString() {
        return "";
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new CollectEvent(pc.getCensoredDTO(),true);
    }
}
