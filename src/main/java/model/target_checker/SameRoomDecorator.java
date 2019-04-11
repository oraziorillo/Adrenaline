package model.target_checker;

import model.Pc;
import model.RoomColourEnum;
import model.Tile;

import java.util.HashSet;

public class SameRoomDecorator extends TargetCheckerDecorator {

    public SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = false;
        RoomColourEnum colour;
        HashSet<Tile> actionTile;
        colour = game.getCurrentCharacter().getCurrentTile().getRoomColour();
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (possibleTarget.getCurrentTile().getRoomColour()==colour && actionTile.contains(possibleTarget.getCurrentTile())){
            valid = true;
        }
        return base.isValid(possibleTarget) && valid;
    }
}
