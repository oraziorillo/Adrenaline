package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.ArrayList;

public class StraightLineDecorator extends TargetCheckerDecorator {

    public StraightLineDecorator(TargetChecker decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(ArrayList<Pc> pcs, Tile startingTile) {
        return base.isValid(pcs,startingTile)&&(sameX(pcs,startingTile)||sameY(pcs,startingTile));
    }

    private boolean sameX(ArrayList<Pc> c, Tile startingTile){
        boolean sameX=true;
        for(Pc pc :c){
            if(pc.getCurrentTile().getX()!=startingTile.getX()){
                sameX=false;
                break;
            }
        }
        return sameX;
    }

    private boolean sameY(ArrayList<Pc> c, Tile startingTile){
        boolean sameY=true;
        for(Pc pc :c){
            if(pc.getCurrentTile().getY()!=startingTile.getY()){
                sameY=false;
                break;
            }
        }
        return sameY;
    }
}
