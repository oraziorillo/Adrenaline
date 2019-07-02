package server.model.target_checkers;

import com.google.gson.annotations.Expose;
import server.model.squares.Square;
import java.util.Set;

public class EmptyChecker implements TargetChecker {

    @Expose
    private String type = "";


    public Set<Square> validSquares(Square referenceSquare){
        return referenceSquare.allSquares();
    }


    @Override
    public TargetChecker getbase() {
        return null;
    }


    @Override
    public String type() {
        return type;
    }


    @Override
    public int propertyValue() {
        return -1;
    }


    @Override
    public String propertyName() {
        return null;
    }
}
