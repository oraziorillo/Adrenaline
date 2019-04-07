package model;

public abstract class TargetConditionDecorator implements TargetCondition{
    TargetCondition base;

    public TargetConditionDecorator(TargetCondition decorated){
        this.base=decorated;
    }
}
