package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.HashSet;

public class MaxDistanceDecorator extends TargetCheckerDecorator{

    private int maxDistance;

    public MaxDistanceDecorator(TargetChecker decorated, int maxDistanceAllowed){
        super(decorated);
        this.maxDistance = maxDistanceAllowed;
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = false;
        HashSet<Tile> temp;
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            temp = game.getCurrentPc().getCurrentTile().atDistance(tempMaxDistance);
            if (temp.contains(possibleTarget.getCurrentTile())) {
                valid = true;
                break;
            }
        }
        return base.isValid(possibleTarget) && valid;
    }
}
