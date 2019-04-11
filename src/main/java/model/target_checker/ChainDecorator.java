package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.ArrayList;

public class ChainDecorator extends TargetCheckerDecorator {
    private Pc chainPc;
    public ChainDecorator(TargetChecker decorated, Pc selectedChainPc) {
        super(decorated);
        this.chainPc = selectedChainPc;
    }
    //TODO da rivedere l'utilità di questo decorator a seconda di come utilizziamo visibleDecorator

    public boolean isValid(Pc possibleTarget) {
        chainTile = chainPc.getCurrentTile();


    @Override
    public boolean isValid(ArrayList<Pc> pcs, Tile startingTile) {

        return base.isValid(pcs,startingTile)&&inChain(pcs,startingTile);
    }

    private boolean inChain(ArrayList<Pc> c, Tile startingTile){
        boolean inChain=true;
        for (int i=0;i<c.size()-1;i++){
            if(!(c.get(i).getCurrentTile().getVisibles().contains(c.get(i+1).getCurrentTile()))){
                inChain=false;
                break;
            }
        }
        return inChain;
    }
}
