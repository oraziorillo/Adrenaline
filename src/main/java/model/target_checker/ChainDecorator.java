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
    }
}
