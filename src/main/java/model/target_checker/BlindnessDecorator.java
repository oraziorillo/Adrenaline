package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.ArrayList;
import java.util.Collection;

public class BlindnessDecorator extends TargetCheckerDecorator {

    public BlindnessDecorator(TargetChecker decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(ArrayList<Pc> pcs, Tile startingTile) {
        return base.isValid(pcs,startingTile)&&notInSight(pcs,startingTile);
    }

    boolean notInSight(ArrayList<Pc> c, Tile startingTile){
        boolean noOneInSight=true;
        Collection visibles=startingTile.getVisibles();
        for(Pc pc :c){
            if(visibles.contains(pc.getCurrentTile())){
                noOneInSight=false;
                break;
            }
        }
        return noOneInSight;
    }
}
