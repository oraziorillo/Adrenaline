package server.model.target_checkers;

import server.model.squares.Square;
import java.util.Set;

public interface TargetChecker {

    Set<Square> validSquares(Square referenceSquare);

    TargetChecker getbase();

    int propertyValue();

    String propertyName();

    String type();

}



