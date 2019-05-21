package model.targetCheckers;

import enums.CardinalDirectionEnum;
import model.squares.Square;

import java.util.HashSet;

public class BeyondWallsStraightLineDecorator extends TargetCheckerDecorator {
    private CardinalDirectionEnum direction;

    public BeyondWallsStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public HashSet<Square> validSquares(Square referenceSquare) {
        HashSet<Square> selectedSquares = new HashSet<>();
        HashSet<Square> resultSquares;
        if (direction == null) {
            for (int i = 0; i < game.map.length; i++) {
                if (game.map[referenceSquare.getX()][i] != null) {
                    selectedSquares.add(game.map[referenceSquare.getX()][i]);
                }
            }
            for (int i = 0; i < game.map[0].length; i++) {
                if (game.map[i][referenceSquare.getY()] != null) {
                    selectedSquares.add(game.map[i][referenceSquare.getY()]);
                }
            }
        }
        else {
            switch (direction) {
                case NORTH:
                    for (int i = referenceSquare.getY(); i >= 0; i--) {
                        if (game.map[referenceSquare.getX()][i] != null) {
                            selectedSquares.add(game.map[referenceSquare.getX()][i]);
                        }
                    }
                    break;
                case EAST:
                    for (int i = referenceSquare.getX(); i < game.map[0].length; i++) {
                        if (game.map[i][referenceSquare.getY()] != null) {
                            selectedSquares.add(game.map[i][referenceSquare.getY()]);
                        }
                    }
                    break;
                case SOUTH:
                    for (int i = referenceSquare.getY(); i < game.map.length; i++) {
                        if (game.map[referenceSquare.getX()][i] != null) {
                            selectedSquares.add(game.map[referenceSquare.getX()][i]);
                        }
                    }
                    break;
                case WEST:
                    for (int i = referenceSquare.getX(); i >= 0; i--) {
                        if (game.map[i][referenceSquare.getY()] != null) {
                            selectedSquares.add(game.map[i][referenceSquare.getY()]);
                        }
                    }
                    break;
            }
        }
        resultSquares = base.validSquares(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}
