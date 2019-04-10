package model.target_checker;

import model.Character;
import model.Tile;

import java.util.HashSet;

public class MaxDistanceDecorator extends TargetCheckerDecorator{

    private int maxDistance;
    public MaxDistanceDecorator(TargetChecker decorated, int maxDistanceAllowed){
        super(decorated);
        this.maxDistance = maxDistanceAllowed;
    }

    public boolean isValid(Character possibleTargetCharacter) {
        boolean valid = false;
        HashSet<Tile> temp;
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            temp = game.getCurrentCharacter().getCurrentTile().distanceOf(tempMaxDistance);
            if (temp.contains(possibleTargetCharacter.getCurrentTile())) {
                valid = true;
                break;
            }
        }
        return base.isValid(possibleTargetCharacter) && valid;
    }
}
