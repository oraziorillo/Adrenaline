package server.model.target_checkers;

import com.google.gson.annotations.Expose;
import server.model.squares.Square;
import java.util.Set;

public class VisibilityDecorator extends TargetCheckerDecorator {

    @Expose
    private String type = "visibility";


    public VisibilityDecorator(TargetChecker decorated){
        super(decorated);
    }


    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> visibleSquares, resultSquares;
        visibleSquares = referenceSquare.getVisibles();
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(visibleSquares);
        return resultSquares;
    }


    @Override
    public String type() {
        return type;
    }
}
