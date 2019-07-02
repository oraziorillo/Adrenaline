package server.model.target_checkers;

import com.google.gson.annotations.Expose;
import server.model.squares.Square;
import java.util.HashSet;
import java.util.Set;

public class MaxDistanceFromVIsiblesDecorator extends TargetCheckerDecorator {

    @Expose private String type = "maxDistanceFromVisible";
    @Expose private int maxDistance;


    public MaxDistanceFromVIsiblesDecorator(TargetChecker decorated, int maxDistance) {
        super(decorated);
        this.maxDistance = maxDistance;
    }


    @Override
    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> validSquares = new HashSet<>();
        referenceSquare.getVisibles().forEach(s -> validSquares.addAll(s.atDistance(maxDistance)));
        Set<Square> resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(validSquares);
        return resultSquares;
    }


    @Override
    public int propertyValue() {
        return maxDistance;
    }


    @Override
    public String propertyName() {
        return "maxDistance";
    }


    @Override
    public String type() {
        return type;
    }
}
