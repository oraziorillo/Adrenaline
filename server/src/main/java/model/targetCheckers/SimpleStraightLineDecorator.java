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
        Optional<Square> temp;
        HashSet<Square> tilesInDirections = new HashSet<>();
        HashSet<Square> resultSquares;
        if (direction == null) {
            for (CardinalDirectionEnum d : CardinalDirectionEnum.values()) {
                tempRefSquare = referenceSquare;
                do {
                    temp = tempRefSquare.onDirection(d);
                    if (temp.isPresent()) {
                        tilesInDirections.add(temp.get());
                        tempRefSquare = temp.get();
                    }
                } while (temp.isPresent());
            }
        } else {
            tempRefSquare = referenceSquare;
            do {
                temp = tempRefSquare.onDirection(direction);
                if (temp.isPresent()) {
                    tilesInDirections.add(temp.get());
                    tempRefSquare = temp.get();
                }
            } while (temp.isPresent());
        }
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(tilesInDirections);
        return resultSquares;
    }
}
