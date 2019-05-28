package model.target_checkers;

import model.squares.Square;

import java.util.Set;

public abstract class TargetCheckerDecorator implements TargetChecker {

    TargetChecker base;


    TargetCheckerDecorator(TargetChecker decorated){
        this.base = decorated;
    }


    public abstract Set<Square> validSquares(Square referenceSquare);
}
