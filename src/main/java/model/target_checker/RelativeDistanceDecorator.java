package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.ArrayList;

public class RelativeDistanceDecorator extends TargetCheckerDecorator {
    public RelativeDistanceDecorator(TargetChecker decorated, int relativeDistancec) {
        super(decorated);
        this.relativeDistance=relativeDistancec;
    }

    private int relativeDistance;

    @Override
    public boolean isValid(ArrayList<Pc> pcs, Tile startingTile) {
        return base.isValid(pcs,startingTile)&&relativeDistanceOK(pcs,startingTile);
    }

    private boolean relativeDistanceOK(ArrayList<Pc> c, Tile startingTile){
        boolean ok=true;
        for(int i=0; i<c.size();i++){
            for(int j=i;j<c.size();j++){
                if(Tile.distance(c.get(i).getCurrentTile(),c.get(j).getCurrentTile())!=relativeDistance){
                    ok=false;
                    break;
                }
            }
        }
        return ok;
    }
}
