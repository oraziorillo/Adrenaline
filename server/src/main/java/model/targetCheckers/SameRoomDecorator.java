package model.targetCheckers;

import model.squares.Square;
import java.util.HashSet;

public class SameRoomDecorator extends TargetCheckerDecorator {

    public SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        HashSet<Square> selectedSquares = new HashSet<>();
        HashSet<Square> resultSquares;
        for(Square t : referenceSquare.getVisibles()){
            if(t.getColour() == referenceSquare.getColour()){
                selectedSquares.add(t);
            }
        }
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}
