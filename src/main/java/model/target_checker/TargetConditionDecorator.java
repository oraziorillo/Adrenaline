package model.target_checker;

public abstract class TargetCheckerDecorator extends TargetChecker{

    protected Character thisCharacter;

    boolean isValid(Character character);

    //public TargetConditionDecorator(TargetChecker decorated){
    //    this.base=decorated;
    //}
}
