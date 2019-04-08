package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class StraightLineDecorator extends TargetConditionDecorator {

    public StraightLineDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&(sameX(characters,startingTile)||sameY(characters,startingTile));
    }

    private boolean sameX(ArrayList<Character> c, Tile startingTile){
        boolean sameX=true;
        for(Character character:c){
            if(character.getCurrentTile().getX()!=startingTile.getX()){
                sameX=false;
                break;
            }
        }
        return sameX;
    }

    private boolean sameY(ArrayList<Character> c, Tile startingTile){
        boolean sameY=true;
        for(Character character:c){
            if(character.getCurrentTile().getY()!=startingTile.getY()){
                sameY=false;
                break;
            }
        }
        return sameY;
    }
}
