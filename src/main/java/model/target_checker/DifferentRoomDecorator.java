package model.target_checker;

import model.Character;
import model.RoomColourEnum;
import model.Tile;

public class DifferentRoomDecorator extends TargetCheckerDecorator {
    public DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public boolean isValid(Character possibleTargetCharacter) {
        boolean valid = false;
        RoomColourEnum colour;
        colour = game.getCurrentCharacter().getCurrentTile().getRoomColour();
        if (possibleTargetCharacter.getCurrentTile().getRoomColour() != colour){
            valid = true;
        }
        return base.isValid(possibleTargetCharacter) && valid;
    }
}
