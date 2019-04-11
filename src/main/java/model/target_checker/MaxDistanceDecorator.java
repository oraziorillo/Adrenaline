package model.target_checker;

import model.Pc;
import model.Game;
import model.Tile;

public class MaxDistanceDecorator extends TargetCheckerDecorator{
    boolean valid
}



public class DifferentTileDecorator extends  TargetCheckerDecorator{

    public DifferentTileDecorator(Pc pc){
        this.thisCharacter = pc;
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
        return thisCharacter.is;
    }


}
