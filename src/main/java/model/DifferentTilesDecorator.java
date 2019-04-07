package model;
public class DifferentTilesDecorator extends TargetConditionDecorator{

    public DifferentTilesDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(Character[] c, Tile startingTile) {
        return base.isValid(c,startingTile)&&onDifferentTiles(c,startingTile);
    }

    private boolean onDifferentTiles(Character[] c, Tile startingTile){
        boolean different=true;
        for(int i=0;i<c.length;i++){
            for(int j=i;j<c.length;j++){
                if(c[i].getPosition().equals(c[j].getPosition())){
                    different=false;
                    break;
                }
            }
        }
        return different;
    }
}
