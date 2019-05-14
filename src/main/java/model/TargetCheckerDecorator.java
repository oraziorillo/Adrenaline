package model;

import model.enumerations.CardinalDirectionEnum;
import org.json.simple.JSONObject;
import java.util.HashSet;
import java.util.Optional;

abstract class TargetCheckerDecorator extends TargetChecker {

    TargetChecker base;

    TargetCheckerDecorator(TargetChecker decorated){
        this.base = decorated;
    }
    abstract HashSet<Square> validTiles(Square referenceSquare);
}


class VisibleDecorator extends TargetCheckerDecorator {

    VisibleDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
        HashSet<Square> visibleSquares, resultSquares;
        visibleSquares = referenceSquare.getVisibles();
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.retainAll(visibleSquares);
        return resultSquares;
    }
}


class BlindnessDecorator extends TargetCheckerDecorator {

    BlindnessDecorator(TargetChecker decorated){
        super(decorated);
    }
    public HashSet<Square> validTiles(Square referenceSquare) {
        HashSet<Square> visibleSquares, resultSquares;
        visibleSquares = referenceSquare.getVisibles();
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.removeAll(visibleSquares);
        return resultSquares;
    }
}


class SimpleStraightLineDecorator extends TargetCheckerDecorator{

    private CardinalDirectionEnum direction;

    SimpleStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
        Square tempRefSquare;
        Optional<Square> temp;
        HashSet<Square> tilesInDirections = new HashSet<>();
        HashSet<Square> resultSquares;
        if (direction == null) {
            for (CardinalDirectionEnum d : CardinalDirectionEnum.values()) {
                tempRefSquare = referenceSquare;
                do {
                    temp = tempRefSquare.onDirection(d);
                    if (temp.isPresent()) {
                        tilesInDirections.add(temp.get());
                        tempRefSquare = temp.get();
                    }
                } while (temp.isPresent());
            }
        } else {
            tempRefSquare = referenceSquare;
            do {
                temp = tempRefSquare.onDirection(direction);
                if (temp.isPresent()) {
                    tilesInDirections.add(temp.get());
                    tempRefSquare = temp.get();
                }
            } while (temp.isPresent());
        }
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.retainAll(tilesInDirections);
        return resultSquares;
    }
}


class BeyondWallsStraightLineDecorator extends TargetCheckerDecorator {
    private CardinalDirectionEnum direction;

    BeyondWallsStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
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
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}


class SameRoomDecorator extends TargetCheckerDecorator {

    SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
        HashSet<Square> selectedSquares = new HashSet<>();
        HashSet<Square> resultSquares;
        for(Square t : referenceSquare.getVisibles()){
            if(t.getTileColour() == referenceSquare.getTileColour()){
                selectedSquares.add(t);
            }
        }
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}


class DifferentRoomDecorator extends TargetCheckerDecorator {

    DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
        EmptyChecker checker = new EmptyChecker();
        HashSet<Square> resultSquares, allSquares;
        allSquares = checker.validTiles(referenceSquare);
        for(Square t : allSquares){
            if(t.getTileColour() == referenceSquare.getTileColour()){
                allSquares.remove(t);
            }
        }
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.retainAll(allSquares);
        return resultSquares;
    }
}


class MinDistanceDecorator extends TargetCheckerDecorator {

    private int minDistance;

    MinDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker){
        super(decorated);
        this.minDistance = (int)jsonTargetChecker.get("minDistance");
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
        HashSet<Square> resultSquares;
        resultSquares = base.validTiles(referenceSquare);
        for (int tempMinDistance = 0; tempMinDistance < minDistance; tempMinDistance++) {
            resultSquares.removeAll(referenceSquare.atDistance(tempMinDistance));
        }
        return resultSquares;
    }
}


class MaxDistanceDecorator extends TargetCheckerDecorator {

    private int maxDistance;

    MaxDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker) {
        super(decorated);
        this.maxDistance = (int) jsonTargetChecker.get("maxDistance");
    }

    public HashSet<Square> validTiles(Square referenceSquare) {
        HashSet<Square> resultSquares;
        HashSet<Square> selectedSquares = new HashSet<>();
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            selectedSquares.addAll(referenceSquare.atDistance(tempMaxDistance));
        }
        resultSquares = base.validTiles(referenceSquare);
        resultSquares.retainAll(selectedSquares);
        return resultSquares;
    }
}




