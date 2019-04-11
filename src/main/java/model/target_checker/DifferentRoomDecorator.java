package model.target_checker;

import model.Pc;
import model.RoomColourEnum;

public class DifferentRoomDecorator extends TargetCheckerDecorator {
    public DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = false;
        RoomColourEnum colour;
        colour = game.getCurrentCharacter().getCurrentTile().getRoomColour();
        if (possibleTarget.getCurrentTile().getRoomColour() != colour){
            valid = true;
        }
        return base.isValid(possibleTarget) && valid;
    }
}
