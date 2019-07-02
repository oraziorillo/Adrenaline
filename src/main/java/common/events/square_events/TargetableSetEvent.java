package common.events.square_events;

import common.dto_model.SquareDTO;

import static common.Constants.TARGETABLE_SET;

public class TargetableSetEvent extends SquareEvent {

    private int eventID = TARGETABLE_SET;


    public TargetableSetEvent(SquareDTO square){
        super(square);
    }


    private TargetableSetEvent(SquareDTO square, boolean isPrivate){
        super(square, isPrivate);
    }


    @Override
    public String toString() {
        return censored
               ? ""
               : (square.isTargetable()
                 ? square.toString() + " is targettable"
                 : "");
    }


    @Override
    public SquareEvent censor() {
        return new TargetableSetEvent(square, true);
    }
}

