package model.target_checkers;

import enums.CardinalDirectionEnum;
import model.squares.Square;
import java.util.HashSet;
import java.util.Set;

public class SimpleStraightLineDecorator extends TargetCheckerDecorator {

    private CardinalDirectionEnum direction;

    public SimpleStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }


    private void addSquareOnDirection(Set<Square> validSquares, Square referenceSquare, CardinalDirectionEnum directionSelected){
        if (referenceSquare != null) {
            validSquares.add(referenceSquare);
            addSquareOnDirection(validSquares, referenceSquare.onDirection(directionSelected), directionSelected);
        }
    }


    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> validSquares = new HashSet<>();
        if (direction == null) {
            for (CardinalDirectionEnum d : CardinalDirectionEnum.values()) {
                addSquareOnDirection(validSquares, referenceSquare, d);
            }
        } else {
            addSquareOnDirection(validSquares, referenceSquare, direction);
        }
        Set<Square> resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(validSquares);
        return resultSquares;
    }
}
