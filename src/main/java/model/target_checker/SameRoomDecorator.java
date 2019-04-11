package model.target_checker;

import model.Character;
import model.TileColourEnum;
import model.Tile;

import java.util.HashSet;

public class SameRoomDecorator extends TargetCheckerDecorator {

    public SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public boolean isValid(Character possibleTargetCharacter) {
        boolean valid = false;
        TileColourEnum colour;
        HashSet<Tile> actionTile;
        colour = game.getCurrentCharacter().getCurrentTile().getRoomColour();
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (possibleTargetCharacter.getCurrentTile().getRoomColour()==colour && actionTile.contains(possibleTargetCharacter.getCurrentTile())){
            valid = true;
        }
        return base.isValid(possibleTargetCharacter) && valid;
    }
}
