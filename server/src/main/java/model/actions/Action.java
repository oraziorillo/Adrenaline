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
    @Expose TargetChecker targetChecker;
    TargetChecker orientedTargetChecker;
    LinkedList<Pc> targets;
    Square targetSquare;


    Action(){
        this.maxNumberOfTargets = 1;
        targets = new LinkedList<>();
    }


    Action (JsonObject jsonAction){
        this.optional = jsonAction.get("optional").getAsBoolean();
        this.maxNumberOfTargets = jsonAction.get("maxNumberOfTargets").getAsInt();
        this.targetChecker = new EmptyChecker();

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
                    return new MinDistanceDecorator(targetChecker);
                case "maxDistance":
                    return new MaxDistanceDecorator(targetChecker);
                case "maxDistanceFromVisible":
                    return new MaxDistanceFromVIsiblesDecorator(targetChecker);
                case "straightLine":
                    return new SimpleStraightLineDecorator(targetChecker);
                case "beyondWallsStraightLine":
                    return new BeyondWallsStraightLineDecorator(targetChecker);
                case "sameRoom":
                    return new SameRoomDecorator(targetChecker);
                case "differentRoom":
                    return new DifferentRoomDecorator(targetChecker);
                default:
                    return null;
            }
        }
    }
     */


}

