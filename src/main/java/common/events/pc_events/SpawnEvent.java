package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.SPAWN;

public class SpawnEvent extends PcEvent {

    private int eventID = SPAWN;


    public SpawnEvent(PcDTO pc){
        super(pc);
    }


    private SpawnEvent(PcDTO pc, boolean censored) {
        super(pc, censored);
    }


    @Override
    public String toString() {
        return (censored
                ? pc.getName()
                : "You")
                    + " spawned on " + pc.squareToString();
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new SpawnEvent(getCensoredDTO(), true);
    }
}
