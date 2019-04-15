package model;
package model;

import model.Enumerations.CardinalDirectionEnum;
import model.Enumerations.TileColourEnum;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public abstract class TargetCheckerDecorator extends TargetChecker {
    TargetChecker base;
    public TargetCheckerDecorator(TargetChecker decorated){
        this.base = decorated;
    }
    abstract HashSet<Tile> validTiles();
}

class VisibleDecorator extends TargetCheckerDecorator {
    private Pc referencePc;
    public VisibleDecorator(TargetChecker decorated, Pc selectedReferencePc){
        super(decorated);
        this.referencePc = selectedReferencePc;
    }
    public HashSet<Tile> validTiles() {
        HashSet<Tile> temp;
        temp = referencePc.getCurrentTile().getVisibles();
        return retainAll()
        actionTile = (HashSet<TileColourEnum>) game.getCurrentPc().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTarget.getCurrentTile().getTileColour())) {
            valid = true;
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class BlindnessDecorator extends TargetCheckerDecorator {
    public BlindnessDecorator(TargetChecker decorated){
        super(decorated);
    }
    public boolean isValid(Pc possibleTarget) {
        boolean valid = true;
        HashSet<TileColourEnum> actionTile;
        actionTile = (HashSet<TileColourEnum>) game.getCurrentPc().getCurrentTile().getVisibles();
        if (actionTile.contains(possibleTarget.getCurrentTile())) {
            valid = false;
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class SimpleStraightLineDecorator extends TargetCheckerDecorator{
    private CardinalDirectionEnum direction;
    public SimpleStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }
    public boolean isValid(Tile t) {
        boolean valid = false;
        Tile attackerTile, possibleTargetTile;
        Optional<Tile> temp;
        attackerTile = game.getCurrentPc().getCurrTile();
        possibleTargetTile = possibleTarget.getCurrTile();
        if(attackerTile.equals(possibleTargetTile)){
            valid = true;
        }
        else {
            do {
                temp = attackerTile.onDirection(direction);
                if (temp.isEmpty()) break;
                valid = temp.equals(possibleTargetTile);
            } while (temp.isPresent() && !valid);
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class BeyondWallsStraightLineDecorator extends TargetCheckerDecorator {
    private CardinalDirectionEnum direction;
    public BeyondWallsStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public boolean isValid(Tile t) {
        boolean valid = false;
        Tile attackerTile, possibleTargetTile;
        attackerTile = game.getCurrentPc().getCurrTile();
        possibleTargetTile = possibleTarget.getCurrTile();
        switch (direction) {
            case NORTH:
                if (attackerTile.getX() == possibleTargetTile.getX() && minDistance <= (possibleTargetTile.getY() - attackerTile.getY()) && (possibleTargetTile.getY() - attackerTile.getY()) <= maxDistance) {
                    valid = true;
                }
                break;
            case EAST:
                if (attackerTile.getY() == possibleTargetTile.getY() && minDistance <= (possibleTargetTile.getX() - attackerTile.getX()) && (possibleTargetTile.getX() - attackerTile.getX()) <= maxDistance) {
                    valid = true;
                }
                break;
            case SOUTH:
                if (attackerTile.getX() == possibleTargetTile.getX() && minDistance <= (attackerTile.getY() - possibleTargetTile.getY()) && (attackerTile.getY() - possibleTargetTile.getY()) <= maxDistance) {
                    valid = true;
                }
                break;
            case WEST:
                if (attackerTile.getY() == possibleTargetTile.getY() && minDistance <= (attackerTile.getX() - possibleTargetTile.getX()) && (attackerTile.getX() - possibleTargetTile.getX()) <= maxDistance) {
                    valid = true;
                }
                break;
        attackerTile = game.getCurrentPc().getCurrentTile();
        possibleTargetTile = possibleTarget.getCurrentTile();
        if(direction == null){
            if(attackerTile.getX() == possibleTargetTile.getX() || attackerTile.getY() == possibleTargetTile.getY()){
                valid = true;
            }
        }
        else {
            switch (direction) {
                case NORTH:
                    if (attackerTile.getX() == possibleTargetTile.getX() && (possibleTargetTile.getY() - attackerTile.getY()) >= 0) {
                        valid = true;
                    }
                    break;
                case EAST:
                    if (attackerTile.getY() == possibleTargetTile.getY() && (possibleTargetTile.getX() - attackerTile.getX()) >= 0) {
                        valid = true;
                    }
                    break;
                case SOUTH:
                    if (attackerTile.getX() == possibleTargetTile.getX() && (attackerTile.getY() - possibleTargetTile.getY()) >= 0) {
                        valid = true;
                    }
                    break;
                case WEST:
                    if (attackerTile.getY() == possibleTargetTile.getY() && (attackerTile.getX() - possibleTargetTile.getX()) >= 0) {
                        valid = true;
                    }
                    break;
            }
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class SameRoomDecorator extends TargetCheckerDecorator {
    public SameRoomDecorator(TargetChecker decorated){
        super(decorated);
    }
    public boolean isValid(Tile t) {
        boolean valid = false;
        if (possibleTarget.getCurrTile().getTileColour()==game.getCurrentPc().getCurrTile().getTileColour()){
            valid = true;
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class DifferentRoomDecorator extends TargetCheckerDecorator {
    public DifferentRoomDecorator(TargetChecker decorated){
        super(decorated);
    }
    public boolean isValid(Tile t) {
        boolean valid = false;
        if (possibleTarget.getCurrTile().getTileColour() != game.getCurrentPc().getCurrTile().getTileColour()){
            valid = true;
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class MinDistanceDecorator extends TargetCheckerDecorator {
    private int minDistance;
    public MinDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker){
        super(decorated);
        this.minDistance = (int)jsonTargetChecker.get("minDistance");
    }
    public boolean isValid(Tile t) {
        boolean valid = true;
        HashSet<Tile> temp;
        for (int tempMinDistance = 0; tempMinDistance < minDistance; tempMinDistance++) {
            temp = game.getCurrentPc().getCurrTile().atDistance(tempMinDistance);
            if (temp.contains(possibleTarget.getCurrTile())) {
                valid = false;
                break;
            }
        }
        return base.isValid(possibleTarget) && valid;
    }
}


class MaxDistanceDecorator extends TargetCheckerDecorator {
    private int maxDistance;
    public MaxDistanceDecorator(TargetChecker decorated, JSONObject jsonTargetChecker){
        super(decorated);
        this.maxDistance = (int)jsonTargetChecker.get("maxDistance");
    }
    }
    public boolean isValid(Tile t) {
        boolean valid = false;
        HashSet<Tile> temp;
        for (int tempMaxDistance = 0; tempMaxDistance <= maxDistance; tempMaxDistance++) {
            temp = game.getCurrentPc().getCurrTile().atDistance(tempMaxDistance);
            if (temp.contains(possibleTarget.getCurrTile())) {
                valid = true;
                break;
            }
        }
        return base.isValid(possibleTarget) && valid;
    }
}

/*
class ChainDecorator extends TargetCheckerDecorator {
    //TODO da rivedere l'utilità di questo decorator a seconda di come utilizziamo VisibleDecorator
    private Pc chainPc;

    public ChainDecorator(TargetChecker decorated, Pc selectedChainPc) {
        super(decorated);
        this.chainPc = selectedChainPc;
    }
//    public boolean isValid(Pc possibleTarget) {
//        chainTile = chainPc.getCurrTile();
//    }
*/




