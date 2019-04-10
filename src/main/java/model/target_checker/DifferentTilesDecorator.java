package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class DifferentTilesDecorator extends TargetCheckerDecorator {

    public DifferentTilesDecorator(TargetChecker decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&onDifferentTiles(characters,startingTile);
    }

    private boolean onDifferentTiles(ArrayList<Character> c, Tile startingTile){
        boolean different=true;
        for(int i=0;i<c.size();i++){
            for(int j=i;j<c.size();j++){
                if(c.get(i).getCurrentTile().equals(c.get(j).getCurrentTile())){
                    different=false;
                    break;
                }
            }
        }
        return different;
    }
}
