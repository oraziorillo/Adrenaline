package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.SPAWN;

public class SpawnEvent extends PcEvent {

    private int eventID = SPAWN;


    public SpawnEvent(PcDTO pc){
        super(pc);
    }


    private SpawnEvent(PcDTO pc, boolean isPrivate) {
        super(pc, isPrivate);
    }


    @Override
    public String toString() {
        return isUncensored
                ? "You"
                : pc.getName()
                + " spawned on " + pc.getCurrSquare();
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new SpawnEvent(getCensoredDTO(), true);
    }
}
