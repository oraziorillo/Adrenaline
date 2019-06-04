package server.model.target_checkers;

import server.model.squares.Square;
import java.util.Set;

public class MinDistanceDecorator extends TargetCheckerDecorator {

    private int minDistance;

    public MinDistanceDecorator(TargetChecker decorated, int minDistance){
        super(decorated);
        this.minDistance = minDistance;
    }

    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> resultSquares;
        resultSquares = base.validSquares(referenceSquare);
        if (minDistance > 0)
            resultSquares.removeAll(referenceSquare.atDistance(minDistance - 1));
        return resultSquares;
    }
}
