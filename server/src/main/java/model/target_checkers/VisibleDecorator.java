package model.target_checkers;

import model.squares.Square;
import java.util.Set;

public class VisibleDecorator extends TargetCheckerDecorator {

    public VisibleDecorator(TargetChecker decorated){
        super(decorated);
    }

    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> visibleSquares, resultSquares;
        visibleSquares = referenceSquare.getVisibles();
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(visibleSquares);
        return resultSquares;
    }
}
