package model.actions;

import model.*;
import model.squares.Square;
import model.targetCheckers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;

public class DamageMarksAction extends Action {
    private short damage;
    private short marks;
    private LinkedList<Pc> targets;
    private int maxNumberOfTargets;
    private boolean squareExplosive, roomExplosive, additionalDamage, exclusiveForOldTargets;

    public DamageMarksAction(JSONObject jsonAction) {
        super(jsonAction);
        this.targets = new LinkedList<>();
        this.damage = ((Long) jsonAction.get("damage")).shortValue();
        this.marks = ((Long) jsonAction.get("marks")).shortValue();
        this.maxNumberOfTargets = (int) jsonAction.get("maxNumberOfTargets");
        this.squareExplosive = (boolean) jsonAction.get("squareExplosive");
        this.roomExplosive = (boolean) jsonAction.get("roomExplosive");
        this.additionalDamage = (boolean) jsonAction.get("additionalDamage");
        this.exclusiveForOldTargets = (boolean) jsonAction.get("exclusiveForOldTargets");
        JSONArray jsonTargetCheckers = (JSONArray) jsonAction.get("targetCheckers");
        JSONObject jsonTargetChecker;
        for (Object checker : jsonTargetCheckers) {
            jsonTargetChecker = (JSONObject) checker;
            switch ((String) jsonTargetChecker.get("type")) {
                case "visible":
                    this.basicTargetChecker = new VisibleDecorator(basicTargetChecker);
                    break;
                case "blindness":
                    this.basicTargetChecker = new BlindnessDecorator(basicTargetChecker);
                    break;
                case "minDistance":
                    this.basicTargetChecker = new MinDistanceDecorator(basicTargetChecker, jsonTargetChecker);
                    break;
                case "maxDistance":
                    this.basicTargetChecker = new MaxDistanceDecorator(basicTargetChecker, jsonTargetChecker);
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
    }



    @Override
    public void selectPc(Pc targetPc) {
        if(!roomExplosive && ! squareExplosive)
            addTarget(targetPc);
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (squareExplosive) {
            targetSquare.getPcs().addAll(targets);
        } else if (roomExplosive) {
            TargetChecker t = new SameRoomDecorator(new EmptyChecker());
            HashSet<Square> room = t.validSquares(targetSquare);
            room.forEach(s -> s.getPcs().addAll(targets));
        }
    }


    @Override
    public void apply(Pc shooter) {
        targets.forEach(pc -> {
            if (damage != 0)
                pc.takeDamage(shooter.getColour(), damage);
            if (marks != 0)
                pc.takeMarks(shooter.getColour(), marks);
        });
        targets.clear();
        orientedTargetChecker = null;

    }


    @Override
    public boolean isAdditionalDamage(){
        return additionalDamage;
    }


    @Override
    public boolean isExclusiveForOldTargets(){
        return exclusiveForOldTargets;
    }


    private void addTarget (Pc target){
        if (targets.size() == maxNumberOfTargets)
            targets.removeFirst();
        targets.add(target);
    }
}
