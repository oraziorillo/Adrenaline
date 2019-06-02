package server.model.target_checkers;

import server.model.squares.Square;
import java.util.HashSet;
import java.util.Set;

public class MaxDistanceFromVIsiblesDecorator extends TargetCheckerDecorator {

    private int maxDistance;

    public MaxDistanceFromVIsiblesDecorator(TargetChecker decorated, int maxDistance) {
        super(decorated);
        this.maxDistance = maxDistance;
    }

    @Override
    public Set<Square> validSquares(Square referenceSquare) {
        HashSet<Square> validSquares = new HashSet<>();
        referenceSquare.getVisibles().forEach(s -> validSquares.addAll(s.atDistance(maxDistance)));
        Set<Square> resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(validSquares);
        return resultSquares;
    }
}
