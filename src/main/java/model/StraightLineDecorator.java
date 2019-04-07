package model;

public class StraightLineDecorator extends TargetConditionDecorator {

    public StraightLineDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(Character[] c, Tile startingTile) {
        return base.isValid(c,startingTile)&&(sameX(c,startingTile)||sameY(c,startingTile));
    }

    private boolean sameX(Character[] c, Tile startingTile){
        boolean sameX=true;
        for(Character character:c){
            if(character.getPosition().getX()!=startingTile.getX()){
                sameX=false;
                break;
            }
        }
        return sameX;
    }

    private boolean sameY(Character[] c, Tile startingTile){
        boolean sameY=true;
        for(Character character:c){
            if(character.getPosition().getY()!=startingTile.getY()){
                sameY=false;
                break;
            }
        }
        return sameY;
    }
}
