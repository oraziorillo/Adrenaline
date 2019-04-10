package model.target_checker;


import model.Character;
import model.Game;
import model.Tile;

import java.util.ArrayList;
import java.util.HashSet;

public class VisibleDecorator extends TargetCheckerDecorator {
    //private int minDistance;
    //private int maxDistance;
    //private boolean needVisibility;
    public VisibleDecorator(Character character) {
        this.thisCharacter = character;
    }

    @Override
    public boolean isValid(Character PossibleTargetCharacter) {
        game = new Game();
        boolean valid = false;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile().getVisibles();
        if (actionTile.contains(PossibleTargetCharacter.getCurrentTile())) {
            valid = true;
        }

        return isValid(thisCharacter) && valid;
    }
}


        for(Character character: characters){
            boolean minDistOK=Tile.distance(character.getCurrentTile(),startingTile)>=minDistance;
            boolean maxDistOK=Tile.distance(character.getCurrentTile(),startingTile)<=maxDistance;
            boolean visibilityOK=(!needVisibility)||(startingTile.getVisibles().contains(character.getCurrentTile()));
            if(!(minDistOK&&maxDistOK&&visibilityOK)){
                valid = false;
                break;
            }
        }
        return valid;
    }

    public ConcreteTargetChecker(int minDistance, int maxDistance, boolean needVisibility) {
        if(maxDistance<minDistance){
            throw new IllegalArgumentException("maxDistance must be greater or equal to minDistance");
        }
        this.needVisibility=needVisibility;
        this.minDistance=minDistance;
        this.maxDistance=maxDistance;
    }

}
