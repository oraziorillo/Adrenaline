package model;

public class RelativeDistanceDecorator extends TargetConditionDecorator{
    public RelativeDistanceDecorator(TargetCondition decorated, int relativeDistancec) {
        super(decorated);
        this.relativeDistance=relativeDistancec;
    }

    private int relativeDistance;

    @Override
    public boolean isValid(Character[] c,Tile startingTile) {
        return base.isValid(c,startingTile)&&relativeDistanceOK(c,startingTile);
    }

    private boolean relativeDistanceOK(Character[] c, Tile startingTile){
        boolean ok=true;
        for(int i=0; i<c.length;i++){
            for(int j=i;j<c.length;j++){
                if(Tile.distance(c[i].getPosition(),c[j].getPosition())!=relativeDistance){
                    ok=false;
                    break;
                }
            }
        }
        return ok;
    }
}
