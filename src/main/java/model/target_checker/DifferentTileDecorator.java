package model.target_checker;

import model.Game;
import model.Tile;
import model.Pc;

public class DifferentTileDecorator extends  TargetCheckerDecorator{

    public DifferentTileDecorator(TargetChecker decorated){
        this.base = decorated;
    }

    public boolean isValid(Pc possibleTarget) {
        game = new Game();
        boolean valid = false;
        Tile actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile();
        if(possibleTarget.getCurrentTile().equals(actionTile)) {
            valid == false;
        }
        else valid == true;
        return base.isValid(possibleTarget) && valid;
    }


}
