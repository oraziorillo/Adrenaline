package model.target_checker;

import model.Pc;
import model.Game;
import model.Tile;

import java.util.HashSet;

public class VisibleDecorator extends TargetCheckerDecorator {
    public VisibleDecorator(TargetChecker decorated) {
        super(decorated);
    }

    @Override  //forse non è un'override
    public boolean isValid(Pc possibleTarget) {
        game = new Game();
        boolean valid = false;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentPc().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTarget.getCurrentTile())) {
            valid = true;
        }
        return base.isValid(possibleTarget) && valid;
}
