package model.targetCheckers;

import model.squares.Square;
import org.json.simple.JSONObject;

import java.util.HashSet;

public class MinDistanceDecorator extends TargetCheckerDecorator {

    private int minDistance;

    public MinDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker){
        super(decorated);
        this.minDistance = (int)jsonTargetChecker.get("minDistance");
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        HashSet<Square> resultSquares;
        resultSquares = base.validSquares(referenceSquare);
        for (int tempMinDistance = 0; tempMinDistance < minDistance; tempMinDistance++) {
            resultSquares.removeAll(referenceSquare.atDistance(tempMinDistance));
        }
        return resultSquares;
    }
}
