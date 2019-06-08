package server.model.target_checkers;

import server.model.squares.Square;
import java.util.HashSet;
import java.util.Set;

public class MaxDistanceDecorator extends TargetCheckerDecorator {

    private int maxDistance;

    public MaxDistanceDecorator(TargetChecker decorated, int maxDistance) {
        super(decorated);
        this.maxDistance = maxDistance;
    }

    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> resultSquares;
        Set<Square> selectedSquares = new HashSet<>(referenceSquare.atDistance(maxDistance));
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}
