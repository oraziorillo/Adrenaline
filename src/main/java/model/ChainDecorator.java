package model;
//TODO: entire class
public class ChainDecorator extends TargetConditionDecorator{

    @Override
    public boolean isValid(Character[] c, Tile startingTile) {
        return false;
    }
}
