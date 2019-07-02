package server.model.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import common.enums.CardinalDirectionEnum;
import server.model.Pc;
import server.model.squares.Square;
import server.model.target_checkers.*;

import java.util.HashSet;
import java.util.Set;


public abstract class Action {

    @Expose private boolean optional;
    @Expose private boolean necessaryForNextAction;
    @Expose private boolean parameterized;
    @Expose private boolean needsOldSquare;    //per il rocket laucher
    private boolean movement;           //PER IL DEBUG
    @Expose int maxNumberOfTargets;
    @Expose TargetChecker targetChecker;
    Set<Pc> targets;
    Square targetSquare;


    Action (JsonObject jsonAction){
        this.optional = jsonAction.get("optional").getAsBoolean();
        this.necessaryForNextAction = jsonAction.get("necessaryForNextAction").getAsBoolean();
        this.parameterized = jsonAction.get("parameterized").getAsBoolean();
        this.needsOldSquare = jsonAction.get("needsOldSquare").getAsBoolean();
        this.maxNumberOfTargets = jsonAction.get("maxNumberOfTargets").getAsInt();
        this.targetChecker = new EmptyChecker();
        this.targets = new HashSet<>();

        JsonArray json = jsonAction.get("targetChecker").getAsJsonArray();
        for (JsonElement checker : json) {
            JsonObject jsonChecker = checker.getAsJsonObject();
            switch (jsonChecker.get("type").getAsString()) {
                case "visibility":
                    this.targetChecker = new VisibilityDecorator(targetChecker);
                    break;
                case "blindness":
                    this.targetChecker = new BlindnessDecorator(targetChecker);
                    break;
                case "minDistance":
                    this.targetChecker = new MinDistanceDecorator(targetChecker, jsonChecker.get("minDistance").getAsInt());
                    break;
                case "maxDistance":
                    this.targetChecker = new MaxDistanceDecorator(targetChecker, jsonChecker.get("maxDistance").getAsInt());
                    break;
                case "maxDistanceFromVisible":
                    this.targetChecker = new MaxDistanceFromVIsiblesDecorator(targetChecker, jsonChecker.getAsJsonObject().get("maxDistance").getAsInt());
                    break;
                case "straightLine":
                    this.targetChecker = new SimpleStraightLineDecorator(targetChecker, null);
                    break;
                case "beyondWallsStraightLine":
                    this.targetChecker = new BeyondWallsStraightLineDecorator(targetChecker, null);
                    break;
                case "sameRoom":
                    this.targetChecker = new SameRoomDecorator(targetChecker);
                    break;
                case "differentRoom":
                    this.targetChecker = new DifferentRoomDecorator(targetChecker);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean isMovement(){ return false; }

    public boolean isOptional() {
        return optional;
    }

    public boolean isNecessaryForNextAction() {
        return necessaryForNextAction;
    }

    public boolean isParameterized() {
        return parameterized;
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


    public boolean needsOldSquare() { return needsOldSquare; }


    public void setTargetSquare(Square s){
        this.targetSquare = s;
    }


    public void setOrientedTargetChecker(CardinalDirectionEnum direction, boolean isBeyondWalls) {}


    public abstract Set<Square> validSquares(Square shooterSquare);


    public abstract boolean isComplete();


    public abstract boolean isSelfMovement();


    public abstract void selectPc(Pc targetPc);


    public abstract void selectSquare(Square targetSquare);


    public abstract void resetAction();


    public abstract Set<Pc> apply(Pc shooter);

}

