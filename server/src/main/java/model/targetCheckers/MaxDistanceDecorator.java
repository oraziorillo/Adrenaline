package model.targetCheckers;

import model.squares.Square;
import org.json.simple.JSONObject;

import java.util.HashSet;

public class MaxDistanceDecorator extends TargetCheckerDecorator {

    private int maxDistance;

    public MaxDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker) {
        super(decorated);
        this.maxDistance = (int) jsonTargetChecker.get("maxDistance");
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        HashSet<Square> resultSquares;
        HashSet<Square> selectedSquares = new HashSet<>();
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            selectedSquares.addAll(referenceSquare.atDistance(tempMaxDistance));
        }
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}
