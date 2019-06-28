package common.events.pc_events;

import common.dto_model.PcDTO;
import common.dto_model.SquareDTO;

import static common.Constants.MOVEMENT;

//TODO: aspetta push di orazio
public class MovementEvent extends PcEvent {

    private String pcName;
    private SquareDTO from;
    private SquareDTO to;


    public MovementEvent(String pcName, SquareDTO from, SquareDTO to){
        this.pcName = pcName;
        this.from = from;
        this.to = to;
    }


    @Override
    public String toString() {
        return pcName + " moved from " + from + " to " + to;
    }


    @Override
    public PcDTO getNewValue() {
        return null;
    }


    @Override
    public String getPropertyName() {
        return MOVEMENT;
    }
}
