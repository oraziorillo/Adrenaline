package model.targetCheckers;

import model.squares.Square;

import java.util.HashSet;

public class VisibleDecorator extends TargetCheckerDecorator {

    public VisibleDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        HashSet<Square> visibleSquares, resultSquares;
        visibleSquares = referenceSquare.getVisibles();
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(visibleSquares);
        return resultSquares;
    }
}
