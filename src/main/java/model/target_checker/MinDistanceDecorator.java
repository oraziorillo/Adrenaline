package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.HashSet;

public class MinDistanceDecorator extends TargetCheckerDecorator {
    private int minDistance;
    public MinDistanceDecorator(TargetChecker decorated, int minDistanceAllowed){
        super(decorated);
        this.minDistance = minDistanceAllowed;
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = true;
        HashSet<Tile> temp;
        for (int tempMinDistance = 0; tempMinDistance < minDistance; tempMinDistance++) {
            temp = game.getCurrentCharacter().getCurrentTile().distanceOf(tempMinDistance);
            if (temp.contains(possibleTarget.getCurrentTile())) {
                valid = false;
                break;
            }
        }
        return base.isValid(possibleTarget) && valid;
    }
}
