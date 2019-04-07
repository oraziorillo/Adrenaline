package model;

//TODO: check if something is missing
public abstract class TargetConditionDecorator implements TargetCondition{
    private TargetCondition base;

    public abstract boolean isValid(Character[] c, Tile startingTile);
}
