package model.actions;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import enums.CardinalDirectionEnum;
import model.*;
import model.squares.Square;
import model.target_checkers.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public abstract class Action {

    @Expose private boolean optional;
    @Expose int maxNumberOfTargets;
    TargetChecker targetChecker;
    TargetChecker orientedTargetChecker;
    LinkedList<Pc> targets;
    Square targetSquare;


    Action (JsonObject jsonAction){
        this.optional = jsonAction.get("optional").getAsBoolean();
        this.maxNumberOfTargets = jsonAction.get("maxNumberOfTargets").getAsInt();
        this.targetChecker = new EmptyChecker();
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


    //methods for tests
    public short getDamage() {
        return 0;
    }


    public short getMarks() {
        return 0;
    }


    public int getMaxNumberOfTargets() {
        return 0;
    }


    public List<Pc> getTargets(){
        return targets;
    }
    /// end methods for tests


    public void setTargetSquare(Square s){
        this.targetSquare = s;
    }


    public void setOrientedTargetChecker(CardinalDirectionEnum direction, boolean isBeyondWalls){
         orientedTargetChecker = isBeyondWalls ? new BeyondWallsStraightLineDecorator(targetChecker, direction)
                                               : new SimpleStraightLineDecorator(targetChecker, direction);
    }


    public Set<Square> validSquares(Square shooterSquare) {
        return (orientedTargetChecker == null) ? targetChecker.validSquares(shooterSquare)
                                               : orientedTargetChecker.validSquares(shooterSquare);
    }


    public Set<Square> validDestinations(Square targetSquare) {
        return null;
    }


    public abstract boolean isComplete();


    public abstract void selectPc(Pc targetPc);


    public abstract void selectSquare(Square targetSquare);


    public abstract void resetAction();


    public abstract void apply(Pc shooter);


}

/*
class TargetCheckerDeserializer implements JsonDeserializer<TargetChecker> {

    @Override
    public TargetChecker deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        switch (json.getAsJsonObject().get("type").getAsString()) {
            case "visibility":
                return new VisibilityDecorator(targetChecker);
            case "blindness":
                return new BlindnessDecorator(targetChecker);
            case "minDistance":
                return new MinDistanceDecorator(targetChecker, json.getAsJsonObject().get("minDistance").getAsInt());
            case "maxDistance":
                return new MaxDistanceDecorator(targetChecker, json.getAsJsonObject().get("maxDistance").getAsInt());
            case "maxDistanceFromVisible":
                return new MaxDistanceFromVIsiblesDecorator(targetChecker, json.getAsJsonObject().get("maxDistance").getAsInt());
            case "straightLine":
                return new SimpleStraightLineDecorator(targetChecker, null);
            case "beyondWallsStraightLine":
                return new BeyondWallsStraightLineDecorator(targetChecker, null);
            case "sameRoom":
                return new SameRoomDecorator(targetChecker);
            case "differentRoom":
                return new DifferentRoomDecorator(targetChecker);
            default:
                return new EmptyChecker();
        }
    }
}
 */