package model;

public class BlindnessDecorator extends TargetConditionDecorator {
    //TODO: entire class

    @Override
    public boolean isValid(Character[] c, Tile startingTile) {
        return false;
    }
}
