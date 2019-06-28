package common.events.square_events;

import common.dto_model.DTO;
import common.dto_model.SquareDTO;
import common.events.ModelEvent;

import static common.Constants.TARGETABLE_SET;

public class TargetableSetEvent extends SquareEvent {

    private SquareDTO square;


    public TargetableSetEvent(SquareDTO square){
        this.square = square;
    }


    @Override
    public String toString() {
        return square.toString();
    }


    @Override
    public SquareDTO getNewValue() {
        return square;
    }


    @Override
    public String getPropertyName() {
        return TARGETABLE_SET;
    }


}

