package model.target_checker;

public abstract class TargetCheckerDecorator implements TargetChecker {
    TargetChecker base;

    public TargetCheckerDecorator(TargetChecker decorated) {
        this.base=decorated;
    }
}
