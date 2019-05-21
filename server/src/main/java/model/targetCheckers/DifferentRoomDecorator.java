package model.targetCheckers;

import model.squares.Square;

import java.util.HashSet;

public class DifferentRoomDecorator extends TargetCheckerDecorator {

    public DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        EmptyChecker checker = new EmptyChecker();
        HashSet<Square> resultSquares, allSquares;
        allSquares = checker.validSquares(referenceSquare);
        for(Square t : allSquares){
            if(t.getColour() == referenceSquare.getColour()){
                allSquares.remove(t);
            }
        }
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(allSquares);
        return resultSquares;
    }
}
