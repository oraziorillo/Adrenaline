package model.target_checker;


import model.Pc;
import model.Game;
import model.Tile;

import java.util.HashSet;

public class VisibleDecorator extends TargetCheckerDecorator {
    //private int minDistance;
    //private int maxDistance;
    //private boolean needVisibility;
    public VisibleDecorator(TargetChecker decorated) {
        this.base = decorated;
    }

    @Override  //forse non è un'override
    public boolean isValid(Pc possibleTarget) {
        game = new Game();
        boolean valid = false;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTarget.getCurrentTile())) {
            valid = true;
        }

        return base.isValid(possibleTarget) && valid;
    }
}
