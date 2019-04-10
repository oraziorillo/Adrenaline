package model.target_checker;


import model.Character;
import model.Tile;

public class ConcreteTargetChecker implements TargetChecker {
    private int minDistance;
    private int maxDistance;
    private boolean needVisibility;

    @Override
    public boolean isValid(Character characters) {
        boolean valid=true;
        for(Character character: characters){
            boolean minDistOK=Tile.distance(character.getCurrentTile(),startingTile)>=minDistance;
            boolean maxDistOK=Tile.distance(character.getCurrentTile(),startingTile)<=maxDistance;
            boolean visibilityOK=(!needVisibility)||(startingTile.getVisibles().contains(character.getCurrentTile()));
            if(!(minDistOK&&maxDistOK&&visibilityOK)){
                valid = false;
                break;
            }
        }
        return valid;
    }

    public ConcreteTargetChecker(int minDistance, int maxDistance, boolean needVisibility) {
        if(maxDistance<minDistance){
            throw new IllegalArgumentException("maxDistance must be greater or equal to minDistance");
        }
        this.needVisibility=needVisibility;
        this.minDistance=minDistance;
        this.maxDistance=maxDistance;
    }

}
