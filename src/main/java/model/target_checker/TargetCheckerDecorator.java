package model.target_checker;

import model.Pc;

public abstract class TargetCheckerDecorator extends TargetChecker {
    TargetChecker base;

    public TargetCheckerDecorator(TargetChecker decorated) {
        this.base = decorated;
    }

    abstract boolean isValid(Pc possibleTarget);
}
