package model.actions;

import enums.CardinalDirectionEnum;
import model.*;
import model.squares.Square;
import model.targetCheckers.BeyondWallsStraightLineDecorator;
import model.targetCheckers.EmptyChecker;
import model.targetCheckers.SimpleStraightLineDecorator;
import model.targetCheckers.TargetChecker;
import org.json.simple.JSONObject;

import java.util.Set;


public abstract class Action {

    boolean optional;
    TargetChecker basicTargetChecker, orientedTargetChecker;
    Square targetSquare;



    Action (JSONObject jsonAction){
        this.optional = (boolean) jsonAction.get("optional");
        this.basicTargetChecker = new EmptyChecker();
    }


    public boolean isOptional() {
        return optional;
    }


    public boolean isExplosive() {
        return false;
    }


    public boolean isSquareExplosive() {
        return false;
    }


    public boolean isRoomExplosive() {
        return false;
    }


    public boolean isAdditionalDamage(){
        return false;
    }


    public boolean isExclusiveForOldTargets() {
        return false;
    }


    public abstract void selectPc(Pc targetPc);


    public abstract void selectSquare(Square targetSquare);


    public abstract void apply(Pc shooter);


    public void setTargetSquare(Square s){
        this.targetSquare = s;
    }


    public void setOrientedTargetChecker(CardinalDirectionEnum direction, boolean isBeyondWalls){
         orientedTargetChecker = isBeyondWalls ? new BeyondWallsStraightLineDecorator(basicTargetChecker, direction)
                                               : new SimpleStraightLineDecorator(basicTargetChecker, direction);
    }


    public Set<Square> validSquares(Square shooterSquare) {
        return (orientedTargetChecker == null) ? basicTargetChecker.validSquares(shooterSquare)
                                               : orientedTargetChecker.validSquares(shooterSquare);
    }

    public abstract boolean isComplete();
}


