package server.model.target_checkers;

import server.model.squares.Square;
import java.util.Set;

public class BlindnessDecorator extends TargetCheckerDecorator {

    public BlindnessDecorator(TargetChecker decorated){
        super(decorated);
    }
    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> visibleSquares, resultSquares;
        visibleSquares = referenceSquare.getVisibles();
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.removeAll(visibleSquares);
        return resultSquares;
    }
}
