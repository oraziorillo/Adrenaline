package model.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import model.*;
import model.squares.Square;
import model.target_checkers.*;
import java.util.Set;

public class MovementAction extends Action {

    @Expose private boolean selfMovement;
    @Expose private TargetChecker destinationChecker;


    public MovementAction(JsonObject jsonAction) {
        super(jsonAction);
        this.selfMovement = jsonAction.get("selfMovement").getAsBoolean();
        JsonArray jsonCheckers;
        JsonObject jsontargetChecker;

        jsonCheckers = jsonAction.get("targetCheckers").getAsJsonArray();
        for (JsonElement checker : jsonCheckers) {
            jsontargetChecker = checker.getAsJsonObject();
            switch (jsontargetChecker.get("type").getAsString()) {
                case "visibility":
                    this.targetChecker = new VisibilityDecorator(targetChecker);
                    break;
                case "blindness":
                    this.targetChecker = new BlindnessDecorator(targetChecker);
                    break;
                case "minDistance":
                    this.targetChecker = new MinDistanceDecorator(targetChecker, jsontargetChecker.get("minDistance").getAsInt());
                    break;
                case "maxDistance":
                    this.targetChecker = new MaxDistanceDecorator(targetChecker, jsontargetChecker.get("maxDistance").getAsInt());
                    break;
                case "maxDistanceFromVisible":
                    this.targetChecker = new MaxDistanceFromVIsiblesDecorator(targetChecker, jsontargetChecker.getAsJsonObject().get("maxDistance").getAsInt());
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

        jsonCheckers = jsonAction.get("destinationChecker").getAsJsonArray();
        for(JsonElement checker : jsonCheckers) {
            jsontargetChecker = checker.getAsJsonObject();
            switch (jsontargetChecker.get("type").getAsString()) {
                case "minDistance":
                    this.destinationChecker = new MinDistanceDecorator(destinationChecker, jsontargetChecker.get("minDistance").getAsInt());
                    break;
                case "maxDistance":
                    this.destinationChecker = new MaxDistanceDecorator(destinationChecker, jsontargetChecker.get("maxDistance").getAsInt());
                    break;
                case "straightLine":
                    this.destinationChecker = new SimpleStraightLineDecorator(destinationChecker, null);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void selectPc(Pc targetPc) {
        if (!selfMovement) {
            if (targets.size() == maxNumberOfTargets)
                targets.removeFirst();
            targets.add(targetPc);
        }
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (!targets.isEmpty() || this.selfMovement) {
            this.targetSquare = targetSquare;
        }
    }


    @Override
    public Set<Square> validDestinations(Square targetSquare) {
        return destinationChecker.validSquares(targetSquare);
    }


    @Override
    public void resetAction() {
        targets.clear();
        targetSquare = null;
    }


    @Override
    public void apply(Pc shooter) {
        if (selfMovement)
            targets.add(shooter);
        if (!isComplete())
            return;
        targets.getFirst().moveTo(targetSquare);
        resetAction();
    }


    @Override
    public boolean isComplete() {
        return (selfMovement || !targets.isEmpty()) &&
                targetSquare != null;
    }
}
