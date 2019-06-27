package common.events;

import common.dto_model.SquareDTO;

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
    Object getNewValue() {
        return square;
    }
}

