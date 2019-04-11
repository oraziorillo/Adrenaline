package model.target_checker;

import model.Pc;
import model.Tile;
import java.util.HashSet;

public class BlindnessDecorator extends TargetCheckerDecorator {

    public BlindnessDecorator(TargetChecker decorated) {
        super(decorated);
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = true;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentPc().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTarget.getCurrentTile())) {
            valid = false;
        }
        return base.isValid(possibleTarget) && valid;
    }
}
