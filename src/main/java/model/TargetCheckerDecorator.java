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
    abstract HashSet<Tile> validTiles(Tile referenceSquare);
}


class VisibleDecorator extends TargetCheckerDecorator {

    VisibleDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        HashSet<Tile> visibleTiles, resultTiles;
        visibleTiles = referenceSquare.getVisibles();
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.retainAll(visibleTiles);
        return resultTiles;
    }
}


class BlindnessDecorator extends TargetCheckerDecorator {

    BlindnessDecorator(TargetChecker decorated){
        super(decorated);
    }
    public HashSet<Tile> validTiles(Tile referenceSquare) {
        HashSet<Tile> visibleTiles, resultTiles;
        visibleTiles = referenceSquare.getVisibles();
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.removeAll(visibleTiles);
        return resultTiles;
    }
}


class SimpleStraightLineDecorator extends TargetCheckerDecorator{

    private CardinalDirectionEnum direction;

    SimpleStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        Tile tempRefSquare;
        Optional<Tile> temp;
        HashSet<Tile> tilesInDirections = new HashSet<>();
        HashSet<Tile> resultTiles;
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
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.retainAll(tilesInDirections);
        return resultTiles;
    }
}


class BeyondWallsStraightLineDecorator extends TargetCheckerDecorator {
    private CardinalDirectionEnum direction;

    BeyondWallsStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        HashSet<Tile> selectedTiles = new HashSet<>();
        HashSet<Tile> resultTiles;
        if (direction == null) {
            for (int i = 0; i < game.map.length; i++) {
                if (game.map[referenceSquare.getX()][i] != null) {
                    selectedTiles.add(game.map[referenceSquare.getX()][i]);
                }
            }
            for (int i = 0; i < game.map[0].length; i++) {
                if (game.map[i][referenceSquare.getY()] != null) {
                    selectedTiles.add(game.map[i][referenceSquare.getY()]);
                }
            }
        }
        else {
            switch (direction) {
                case NORTH:
                    for (int i = referenceSquare.getY(); i >= 0; i--) {
                        if (game.map[referenceSquare.getX()][i] != null) {
                            selectedTiles.add(game.map[referenceSquare.getX()][i]);
                        }
                    }
                    break;
                case EAST:
                    for (int i = referenceSquare.getX(); i < game.map[0].length; i++) {
                        if (game.map[i][referenceSquare.getY()] != null) {
                            selectedTiles.add(game.map[i][referenceSquare.getY()]);
                        }
                    }
                    break;
                case SOUTH:
                    for (int i = referenceSquare.getY(); i < game.map.length; i++) {
                        if (game.map[referenceSquare.getX()][i] != null) {
                            selectedTiles.add(game.map[referenceSquare.getX()][i]);
                        }
                    }
                    break;
                case WEST:
                    for (int i = referenceSquare.getX(); i >= 0; i--) {
                        if (game.map[i][referenceSquare.getY()] != null) {
                            selectedTiles.add(game.map[i][referenceSquare.getY()]);
                        }
                    }
                    break;
            }
        }
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.retainAll(selectedTiles);
        return resultTiles;
    }
}


class SameRoomDecorator extends TargetCheckerDecorator {

    SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        HashSet<Tile> selectedTiles = new HashSet<>();
        HashSet<Tile> resultTiles;
        for(Tile t : referenceSquare.getVisibles()){
            if(t.getTileColour() == referenceSquare.getTileColour()){
                selectedTiles.add(t);
            }
        }
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.retainAll(selectedTiles);
        return resultTiles;
    }
}


class DifferentRoomDecorator extends TargetCheckerDecorator {

    DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        EmptyChecker checker = new EmptyChecker();
        HashSet<Tile> resultTiles, allTiles;
        allTiles = checker.validTiles(referenceSquare);
        for(Tile t : allTiles){
            if(t.getTileColour() == referenceSquare.getTileColour()){
                allTiles.remove(t);
            }
        }
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.retainAll(allTiles);
        return resultTiles;
    }
}


class MinDistanceDecorator extends TargetCheckerDecorator {

    private int minDistance;

    MinDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker){
        super(decorated);
        this.minDistance = (int)jsonTargetChecker.get("minDistance");
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        HashSet<Tile> resultTiles;
        resultTiles = base.validTiles(referenceSquare);
        for (int tempMinDistance = 0; tempMinDistance < minDistance; tempMinDistance++) {
            resultTiles.removeAll(referenceSquare.atDistance(tempMinDistance));
        }
        return resultTiles;
    }
}


class MaxDistanceDecorator extends TargetCheckerDecorator {

    private int maxDistance;

    MaxDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker) {
        super(decorated);
        this.maxDistance = (int) jsonTargetChecker.get("maxDistance");
    }

    public HashSet<Tile> validTiles(Tile referenceSquare) {
        HashSet<Tile> resultTiles;
        HashSet<Tile> selectedTiles = new HashSet<>();
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            selectedTiles.addAll(referenceSquare.atDistance(tempMaxDistance));
        }
        resultTiles = base.validTiles(referenceSquare);
        resultTiles.retainAll(selectedTiles);
        return resultTiles;
    }
}




