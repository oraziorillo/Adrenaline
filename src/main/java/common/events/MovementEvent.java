package common.events;

import common.dto_model.SquareDTO;

import static common.Constants.MOVEMENT;

public class MovementEvent extends ModelEvent {

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
    public Object getNewValue() {
        return to;
    }


    @Override
    public String getPropertyName() {
        return MOVEMENT;
    }
}
