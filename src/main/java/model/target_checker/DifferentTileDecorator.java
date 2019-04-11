package model.target_checker;

import model.Game;
import model.Tile;
import model.Character;

import java.util.HashSet;

public class DifferentTileDecorator extends  TargetCheckerDecorator{

    public DifferentTileDecorator(TargetChecker decorated){
        this.base = decorated;
    }

    public boolean isValid(Character possibleTargetCharacter) {
        game = new Game();
        boolean valid = false;
        Tile actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile();
        if(possibleTargetCharacter.getCurrentTile().equals(actionTile)) {
            valid == false;
        }
        else valid == true;
        return base.isValid(possibleTargetCharacter) && valid;
    }


}
