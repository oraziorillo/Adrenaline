package model.targetCheckers;

import enums.CardinalDirectionEnum;
import model.squares.Square;
import java.util.HashSet;
import java.util.Optional;

public class SimpleStraightLineDecorator extends TargetCheckerDecorator {

    private CardinalDirectionEnum direction;

    public SimpleStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        Square tempRefSquare;
        Square temp;
        HashSet<Square> tilesInDirections = new HashSet<>();
        HashSet<Square> resultSquares;
        if (direction == null) {
            for (CardinalDirectionEnum d : CardinalDirectionEnum.values()) {
                tempRefSquare = referenceSquare;
                do {
                    temp = tempRefSquare.onDirection(d);
                    if (temp != null) {
                        tilesInDirections.add(temp);
                        tempRefSquare = temp;
                    }
                } while (temp != null);
            }
        } else {
            tempRefSquare = referenceSquare;
            do {
                temp = tempRefSquare.onDirection(direction);
                if (temp != null) {
                    tilesInDirections.add(temp);
                    tempRefSquare = temp;
                }
            } while (temp != null);
        }
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(tilesInDirections);
        return resultSquares;
    }
}
