package model.actions;

import enums.CardinalDirectionEnum;
import model.*;
import model.squares.Square;
import model.target_checkers.BeyondWallsStraightLineDecorator;
import model.target_checkers.EmptyChecker;
import model.target_checkers.SimpleStraightLineDecorator;
import model.target_checkers.TargetChecker;
import org.json.simple.JSONObject;
import java.util.HashSet;


public abstract class Action {

    private boolean optional;
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



    public void setTargetSquare(Square s){
        this.targetSquare = s;
    }


    public void setOrientedTargetChecker(CardinalDirectionEnum direction, boolean isBeyondWalls){
         orientedTargetChecker = isBeyondWalls ? new BeyondWallsStraightLineDecorator(basicTargetChecker, direction)
                                               : new SimpleStraightLineDecorator(basicTargetChecker, direction);
    }


    public HashSet<Square> validSquares(Square shooterSquare) {
        return (orientedTargetChecker == null) ? basicTargetChecker.validSquares(shooterSquare)
                                               : orientedTargetChecker.validSquares(shooterSquare);
    }

    public HashSet<Square> validDestinations(Square targetSquare) {
        return null;
    }


    public abstract boolean isComplete();


    public abstract void selectPc(Pc targetPc);


    public abstract void selectSquare(Square targetSquare);


    public abstract void apply(Pc shooter);
}


