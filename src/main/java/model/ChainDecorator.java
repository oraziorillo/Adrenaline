package model;
public class ChainDecorator extends TargetConditionDecorator{

    public ChainDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
    public boolean isValid(Character[] c, Tile startingTile) {
        return base.isValid(c,startingTile)&&inChain(c,startingTile);
    }

    private boolean inChain(Character[] c,Tile startingTile){
        boolean inChain=true;
        for (int i=0;i<c.length-1;i++){
            if(!(c[i].getPosition().getVisibles().contains(c[i+1].getPosition()))){
                inChain=false;
                break;
            }
        }
        return inChain;
    }
}
