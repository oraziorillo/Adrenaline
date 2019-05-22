package model.targetCheckers;

import exceptions.HoleInMapException;
import model.squares.Square;

import java.util.HashSet;

public class EmptyChecker extends TargetChecker {

    public HashSet<Square> validSquares(Square referenceSquare){
        HashSet<Square> temp;
        temp = new HashSet<>();
        for(int i = 0; i < game.map.length; i++){
            for(int j = 0; j < game.map[0].length; j++){
                try {
                    temp.add(game.getSquare(i, j));
                } catch (HoleInMapException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }
}
