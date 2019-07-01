package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.MOVEMENT;

public class MovementEvent extends PcEvent {

    private int eventID = MOVEMENT;
    private String from;
    private String to;


    public MovementEvent(PcDTO pc, String from, String to){
        super(pc);
        this.from = from;
        this.to = to;
    }


    private MovementEvent (PcDTO pc, String from, String to, boolean isPrivate) {
        super(pc, isPrivate);
        this.from = from;
        this.to = to;
    }


    @Override
    public String toString() {
        return (censored
                ? pc.getName()
                : "You")
                + " moved from " + from + " to " + to;
    }


    @Override
    public PcEvent hideSensibleContent() {
        return new MovementEvent(getCensoredDTO(), from, to, true);
    }
}
