package model.target_checker;

import model.CardinalDirectionEnum;
import model.Pc;
import model.Tile;

public class BeyondWallsStraightLineDecorator extends TargetCheckerDecorator {
    private CardinalDirectionEnum direction;
    private int minDistance;
    private int maxDistance;
    public BeyondWallsStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection, int minDistanceAllowed, int maxDistanceAllowed){
        super(decorated);
        this.direction = selectedDirection;
        this.minDistance = minDistanceAllowed;
        this.maxDistance = maxDistanceAllowed;
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = false;
        Tile attackerTile, possibleTargetTile;
        attackerTile = game.getCurrentCharacter().getCurrentTile();
        possibleTargetTile = possibleTarget.getCurrentTile();
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
        }
        return base.isValid(possibleTarget) && valid;
    }
}
