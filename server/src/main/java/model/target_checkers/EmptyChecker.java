package model.target_checkers;

import model.squares.Square;
import java.util.Set;

public class EmptyChecker implements TargetChecker {

    public Set<Square> validSquares(Square referenceSquare){
        return referenceSquare.allSquares();
    }
}
