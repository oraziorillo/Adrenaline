package model.target_checker;

import model.Character;

public class EmptyChecker extends TargetChecker {
    @Override
    public boolean isValid(Character targetChecker) {
        return true;
    }
}

