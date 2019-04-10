package model.target_checker;

public abstract class TargetCheckerDecorator extends TargetChecker{
    TargetChecker base;

    public TargetConditionDecorator(TargetChecker decorated){
        this.base=decorated;
    }

    boolean isValid(Character character);
}
