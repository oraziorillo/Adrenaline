package model;

import model.Enumerations.CardinalDirectionEnum;
import org.json.simple.JSONObject;
import java.util.HashSet;
import java.util.Optional;

abstract class TargetCheckerDecorator extends TargetChecker {
    TargetChecker base;
    TargetCheckerDecorator(TargetChecker decorated){
        this.base = decorated;
    }
    abstract HashSet<Tile> validTiles();
}

class VisibleDecorator extends TargetCheckerDecorator {
    private Pc referencePc;
    VisibleDecorator(TargetChecker decorated, Pc selectedReferencePc){
        super(decorated);
        this.referencePc = selectedReferencePc;
    }
    public HashSet<Tile> validTiles() {
        HashSet<Tile> visibleTiles, resultTiles;
        if(referencePc == null)
            referencePc = game.getCurrentPc();
        visibleTiles = referencePc.getCurrTile().getVisibles();
        resultTiles = base.validTiles();
        resultTiles.retainAll(visibleTiles);
        return resultTiles;
    }
}


class BlindnessDecorator extends TargetCheckerDecorator {
    private Pc referencePc;
    BlindnessDecorator(TargetChecker decorated, Pc selectedReferencePc){
        super(decorated);
        this.referencePc = selectedReferencePc;
    }
    public HashSet<Tile> validTiles() {
        HashSet<Tile> visibleTiles, resultTiles;
        visibleTiles = referencePc.getCurrTile().getVisibles();
        resultTiles = base.validTiles();
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
    public HashSet<Tile> validTiles() {
        Tile attackerTile, referenceTile;
        Optional<Tile> temp;
        HashSet<Tile> tilesInDirections = new HashSet<>();
        HashSet<Tile> resultTiles;
        attackerTile = game.getCurrentPc().getCurrTile();
        if (direction == null) {
            for (CardinalDirectionEnum d : CardinalDirectionEnum.values()) {
                referenceTile = attackerTile;
                do {
                    temp = referenceTile.onDirection(d);
                    if (temp.isPresent()) {
                        tilesInDirections.add(temp.get());
                        referenceTile = temp.get();
                    }
                } while (temp.isPresent());
            }
        } else {
            referenceTile = attackerTile;
            do {
                temp = referenceTile.onDirection(direction);
                if (temp.isPresent()) {
                    tilesInDirections.add(temp.get());
                    referenceTile = temp.get();
                }
            } while (temp.isPresent());
        }
        resultTiles = base.validTiles();
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

    public HashSet<Tile> validTiles() {
        Tile attackerTile;
        HashSet<Tile> selectedTiles = new HashSet<>();
        HashSet<Tile> resultTiles;
        attackerTile = game.getCurrentPc().getCurrTile();
        if (direction == null) {
            for (int i = 0; i < game.map.length; i++) {
                if (game.map[attackerTile.getX()][i] != null) {
                    selectedTiles.add(game.map[attackerTile.getX()][i]);
                }
            }
            for (int i = 0; i < game.map[0].length; i++) {
                if (game.map[i][attackerTile.getY()] != null) {
                    selectedTiles.add(game.map[i][attackerTile.getY()]);
                }
            }
        }
        else {
            switch (direction) {
                case NORTH:
                    for (int i = attackerTile.getY(); i >= 0; i--) {
                        if (game.map[attackerTile.getX()][i] != null) {
                            selectedTiles.add(game.map[attackerTile.getX()][i]);
                        }
                    }
                    break;
                case EAST:
                    for (int i = attackerTile.getX(); i < game.map[0].length; i++) {
                        if (game.map[i][attackerTile.getY()] != null) {
                            selectedTiles.add(game.map[i][attackerTile.getY()]);
                        }
                    }
                    break;
                case SOUTH:
                    for (int i = attackerTile.getY(); i < game.map.length; i++) {
                        if (game.map[attackerTile.getX()][i] != null) {
                            selectedTiles.add(game.map[attackerTile.getX()][i]);
                        }
                    }
                    break;
                case WEST:
                    for (int i = attackerTile.getX(); i >= 0; i--) {
                        if (game.map[i][attackerTile.getY()] != null) {
                            selectedTiles.add(game.map[i][attackerTile.getY()]);
                        }
                    }
                    break;
            }
        }
        resultTiles = base.validTiles();
        resultTiles.retainAll(selectedTiles);
        return resultTiles;
    }
}


class SameRoomDecorator extends TargetCheckerDecorator {
    SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }
    public HashSet<Tile> validTiles() {
        Tile attackingTile;
        HashSet<Tile> selectedTiles = new HashSet<>();
        HashSet<Tile> resultTiles;
        attackingTile = game.getCurrentPc().getCurrTile();
        for(Tile t : attackingTile.getVisibles()){
            if(t.getTileColour() == attackingTile.getTileColour()){
                selectedTiles.add(t);
            }
        }
        resultTiles = base.validTiles();
        resultTiles.retainAll(selectedTiles);
        return resultTiles;
    }
}


class DifferentRoomDecorator extends TargetCheckerDecorator {
    DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }
    public HashSet<Tile> validTiles() {
        Tile attackingTile;
        EmptyChecker checker = new EmptyChecker();
        HashSet<Tile> resultTiles, allTiles;
        attackingTile = game.getCurrentPc().getCurrTile();
        allTiles = checker.validTiles();
        for(Tile t : allTiles){
            if(t.getTileColour() == attackingTile.getTileColour()){
                allTiles.remove(t);
            }
        }
        resultTiles = base.validTiles();
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
    public HashSet<Tile> validTiles() {
        HashSet<Tile> resultTiles;
        resultTiles = base.validTiles();
        Tile referenceTile = game.getCurrentPc().getCurrTile();
        for (int tempMinDistance = 0; tempMinDistance < minDistance; tempMinDistance++) {
            resultTiles.removeAll(referenceTile.atDistance(tempMinDistance));
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

    public HashSet<Tile> validTiles() {
        HashSet<Tile> resultTiles;
        HashSet<Tile> selectedTiles = new HashSet<>();
        Tile referenceTile = game.getCurrentPc().getCurrTile();
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            selectedTiles.addAll(referenceTile.atDistance(tempMaxDistance));
        }
        resultTiles = base.validTiles();
        resultTiles.retainAll(selectedTiles);
        return resultTiles;
    }
}




