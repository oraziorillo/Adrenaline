package model.target_checkers;

import model.squares.Square;
import java.util.Set;
import java.util.stream.Collectors;

public class SameRoomDecorator extends TargetCheckerDecorator {

    public SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> resultSquares;
        Set<Square> validSquares = referenceSquare.allSquares()
                .stream()
                .filter(s -> s.getColour() == referenceSquare.getColour())
                .collect(Collectors.toSet());
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(validSquares);
        return resultSquares;
    }
}
