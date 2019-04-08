package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class RelativeDistanceDecorator extends TargetConditionDecorator{
    public RelativeDistanceDecorator(TargetCondition decorated, int relativeDistancec) {
        super(decorated);
        this.relativeDistance=relativeDistancec;
    }

    private int relativeDistance;

    @Override
    public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&relativeDistanceOK(characters,startingTile);
    }

    private boolean relativeDistanceOK(ArrayList<Character> c, Tile startingTile){
        boolean ok=true;
        for(int i=0; i<c.size();i++){
            for(int j=i;j<c.size();j++){
                if(Tile.distance(c.get(i).getPosition(),c.get(j).getPosition())!=relativeDistance){
                    ok=false;
                    break;
                }
            }
        }
        return ok;
    }
}
