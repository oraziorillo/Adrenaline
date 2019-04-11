package model.target_checker;

import model.Pc;
import model.Tile;
import java.util.HashSet;

public class BlindnessDecorator extends TargetCheckerDecorator {

    public BlindnessDecorator(TargetChecker decorated) {
        super(decorated);
    }

    /*
    boolean notInSight(ArrayList<Pc> c, Tile startingTile, Pc possibleTarget){
        boolean noOneInSight=true;
        Collection visibles=startingTile.getVisibles();
        for(Pc pc :c) {
            if (visibles.contains(pc.getCurrentTile())) {
                noOneInSight = false;
                break;
            }
        }
     */

    public boolean isValid(Pc possibleTarget) {
        boolean valid = true;
        HashSet<Tile> actionTile;
        actionTile = game.getCurrentPc().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTarget.getCurrentTile())) {
            valid = false;
        }
        return base.isValid(possibleTarget) && valid;
    }
}
