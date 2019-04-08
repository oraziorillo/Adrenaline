package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;
import java.util.Collection;

public class BlindnessDecorator extends TargetConditionDecorator {

    public BlindnessDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&notInSight(characters,startingTile);
    }

    boolean notInSight(ArrayList<Character> c, Tile startingTile){
        boolean noOneInSight=true;
        Collection visibles=startingTile.getVisibles();
        for(Character character:c){
            if(visibles.contains(character.getCurrentTile())){
                noOneInSight=false;
                break;
            }
        }
        return noOneInSight;
    }
}
