package server.model.target_checkers;

import common.enums.CardinalDirectionEnum;
import server.model.squares.Square;
import java.util.Set;

public class BeyondWallsStraightLineDecorator extends TargetCheckerDecorator {

    private CardinalDirectionEnum direction;


    public BeyondWallsStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }


    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> validSquares = referenceSquare.allSquaresOnDirection(direction);
        Set<Square> resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(validSquares);
        return resultSquares;
    }
}
