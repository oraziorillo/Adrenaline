package model.target_checker;

import model.Character;
import model.Tile;
import java.util.HashSet;

public class BlindnessDecorator extends TargetCheckerDecorator {

    public BlindnessDecorator(TargetChecker decorated) {
        super(decorated);
    }

    public boolean isValid(Character possibleTargetCharacter) {
        boolean valid = true;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTargetCharacter.getCurrentTile())) {
            valid = false;
        }
        return base.isValid(possibleTargetCharacter) && valid;
    }
}
