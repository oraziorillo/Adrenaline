package model.target_checkers;

import model.squares.Square;
import java.util.Set;

public interface TargetChecker {

    Set<Square> validSquares(Square referenceSquare);

}



