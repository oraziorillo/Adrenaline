package model.target_checker;


import model.Character;
import model.Game;
import model.Tile;

import java.util.HashSet;

public class VisibleDecorator extends TargetCheckerDecorator {
    //private int minDistance;
    //private int maxDistance;
    //private boolean needVisibility;
    public VisibleDecorator(Character character) {
        this.thisCharacter = character;
    }

    @Override  //forse non è un'override
    public boolean isValid(Character possibleTargetCharacter) {
        game = new Game();
        boolean valid = false;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTargetCharacter.getCurrentTile())) {
            valid = true;
        }

        return isValid(thisCharacter) && valid;
    }
}
