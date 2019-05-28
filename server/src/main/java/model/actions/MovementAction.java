package model.actions;

import model.*;
import model.squares.Square;
import model.target_checkers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;

public class MovementAction extends Action {

    private boolean selfMovement;
    private Pc target;
    private TargetChecker destinationChecker;


    public MovementAction(JSONObject jsonAction) {
        super(jsonAction);
        this.selfMovement = (boolean)jsonAction.get("selfMovement");
        JSONArray jsonCheckers;
        JSONObject jsonChecker;

        jsonCheckers = (JSONArray) jsonAction.get("target_checkers");
        for (Object checker : jsonCheckers) {
            jsonChecker = (JSONObject) checker;
            switch ((String) jsonChecker.get("type")) {
                case "visible":
                    this.basicTargetChecker = new VisibleDecorator(basicTargetChecker);
                    break;
                case "blindness":
                    this.basicTargetChecker = new BlindnessDecorator(basicTargetChecker);
                    break;
                case "minDistance":
                    this.basicTargetChecker = new MinDistanceDecorator(basicTargetChecker, jsonChecker);
                    break;
                case "maxDistance":
                    this.basicTargetChecker = new MaxDistanceDecorator(basicTargetChecker, jsonChecker);
                    break;
                case "straightLine":
                    this.basicTargetChecker = new SimpleStraightLineDecorator(basicTargetChecker, null);
                    break;
                case "beyondWallsStraightLine":
                    this.basicTargetChecker = new BeyondWallsStraightLineDecorator(basicTargetChecker, null);
                    break;
                case "sameRoom":
                    this.basicTargetChecker = new SameRoomDecorator(basicTargetChecker);
                    break;
                case "differentRoom":
                    this.basicTargetChecker = new DifferentRoomDecorator(basicTargetChecker);
                    break;
                default:
                    break;
            }
        }

        jsonCheckers = (JSONArray) jsonAction.get("destinationChecker");
        for(Object checker : jsonCheckers) {
            jsonChecker = (JSONObject) checker;
            switch ((String) jsonChecker.get("type")) {
                case "minDistance":
                    this.basicTargetChecker = new MinDistanceDecorator(basicTargetChecker, jsonChecker);
                    break;
                case "maxDistance":
                    this.basicTargetChecker = new MaxDistanceDecorator(basicTargetChecker, jsonChecker);
                    break;
                case "straightLine":
                    this.basicTargetChecker = new SimpleStraightLineDecorator(basicTargetChecker, null);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void selectPc(Pc targetPc) {
        if (!selfMovement)
            target = targetPc;
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (target != null)
            this.targetSquare = targetSquare;
    }


    @Override
    public HashSet<Square> validDestinations(Square targetSquare) {
        return destinationChecker.validSquares(targetSquare);
    }


    @Override
    public void apply(Pc shooter) {
        if (selfMovement)
            target = shooter;
        target.moveTo(targetSquare);

        target = null;
        destinationChecker = null;
        orientedTargetChecker = null;
    }


    @Override
    public boolean isComplete() {
        return (selfMovement || target != null) &&
                targetSquare != null;
    }
}
