package model.targetCheckers;

import model.squares.Square;

import java.util.HashSet;

public abstract class TargetCheckerDecorator extends TargetChecker {

    protected TargetChecker base;

    protected TargetCheckerDecorator(TargetChecker decorated){
        this.base = decorated;
    }
    public abstract HashSet<Square> validSquares(Square referenceSquare);
}
