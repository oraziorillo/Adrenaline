package model;

import java.util.Collection;

public class BlindnessDecorator extends TargetConditionDecorator {

    public BlindnessDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(Character[] c,Tile startingTile) {
        return base.isValid(c,startingTile)&&notInSight(c,startingTile);
    }

    boolean notInSight(Character[] c, Tile startingTile){
        boolean noOneInSight=true;
        Collection visibles=startingTile.getVisibles();
        for(Character character:c){
            if(visibles.contains(character.getPosition())){
                noOneInSight=false;
                break;
            }
        }
        return noOneInSight;
    }
}
