package model.target_checkers;

import model.squares.Square;
import org.json.simple.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class MaxDistanceFromVIsiblesDecorator extends TargetCheckerDecorator {

    private int maxDistance;

    MaxDistanceFromVIsiblesDecorator(TargetChecker decorated, JSONObject jsonTargetChecker) {
        super(decorated);
        this.maxDistance = (int) jsonTargetChecker.get("maxDistance");
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
