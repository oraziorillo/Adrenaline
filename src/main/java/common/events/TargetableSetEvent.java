package common.events;

import common.dto_model.SquareDTO;

import static common.Constants.TARGETABLE_SET;

public class TargetableSetEvent extends ModelEvent {

    private SquareDTO square;


    public TargetableSetEvent(SquareDTO square){
        this.square = square;
    }


    @Override
    public String toString() {
        return square.toString();
    }


    @Override
    public Object getNewValue() {
        return square;
    }


    @Override
    public String getPropertyName() {
        return TARGETABLE_SET;
    }


}

