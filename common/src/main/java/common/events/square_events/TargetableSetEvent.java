package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;

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
        StringBuilder squareString = new StringBuilder();
        if (!isPrivate && square.isTargetable())
            squareString.append("> ").append(square.toString());
        return squareString.toString();
    }


    @Override
    public SquareEvent censor() {
        return new TargetableSetEvent(square, true);
    }
}

