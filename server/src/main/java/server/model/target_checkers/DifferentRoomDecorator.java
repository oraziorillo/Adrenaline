package server.model.target_checkers;

import com.google.gson.annotations.Expose;
import server.model.squares.Square;
import java.util.Set;
import java.util.stream.Collectors;

public class DifferentRoomDecorator extends TargetCheckerDecorator {

    @Expose
    private String type = "differentRoom";


    public DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }


    public Set<Square> validSquares(Square referenceSquare) {
        Set<Square> resultSquares;
        Set<Square> validSquares = referenceSquare.allSquares()
                .stream()
                .filter(s -> s.getColour() != referenceSquare.getColour())
                .collect(Collectors.toSet());
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(validSquares);
        return resultSquares;
    }


    @Override
    public String type() {
        return type;
    }
}
