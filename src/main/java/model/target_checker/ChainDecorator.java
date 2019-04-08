package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class ChainDecorator extends TargetConditionDecorator{

    public ChainDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&inChain(characters,startingTile);
    }

    private boolean inChain(ArrayList<Character> c,Tile startingTile){
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
