package model.target_checker;


import model.Character;
import model.Tile;

import java.util.HashSet;

public class VisibleDecorator extends TargetCheckerDecorator {
    public VisibleDecorator(TargetChecker decorated) {
        super(decorated);
    }

    public boolean isValid(Character possibleTargetCharacter) {
        boolean valid = false;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTargetCharacter.getCurrentTile())) {
            valid = true;
        }
        return base.isValid(possibleTargetCharacter) && valid;
    }
}
