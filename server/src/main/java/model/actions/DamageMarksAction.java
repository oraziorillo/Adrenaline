package model.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import model.*;
import model.squares.Square;
import model.target_checkers.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DamageMarksAction extends Action {
    @Expose private short damage;
    @Expose private short marks;
    @Expose private boolean squareExplosive;
    @Expose private boolean roomExplosive;
    @Expose private boolean additionalDamage;
    @Expose private boolean exclusiveForOldTargets;


    public DamageMarksAction(JsonObject jsonAction) {
        super(jsonAction);
        this.targets = new LinkedList<>();
        this.damage = jsonAction.get("damage").getAsShort();
        this.marks = jsonAction.get("marks").getAsShort();
        this.squareExplosive = jsonAction.get("squareExplosive").getAsBoolean();
        this.roomExplosive = jsonAction.get("roomExplosive").getAsBoolean();
        this.additionalDamage = jsonAction.get("additionalDamage").getAsBoolean();
        this.exclusiveForOldTargets = jsonAction.get("exclusiveForOldTargets").getAsBoolean();
        JsonArray jsonTargetCheckers = jsonAction.get("targetCheckers").getAsJsonArray();
        JsonObject jsonTargetChecker;
        for (JsonElement checker : jsonTargetCheckers) {
            jsonTargetChecker = checker.getAsJsonObject();
            switch (jsonTargetChecker.get("type").getAsString()) {
                case "visibility":
                    this.targetChecker = new VisibilityDecorator(targetChecker);
                    break;
                case "blindness":
                    this.targetChecker = new BlindnessDecorator(targetChecker);
                    break;
                case "minDistance":
                    this.targetChecker = new MinDistanceDecorator(targetChecker, jsonTargetChecker.get("minDistance").getAsInt());
                    break;
                case "maxDistance":
                    this.targetChecker = new MaxDistanceDecorator(targetChecker, jsonTargetChecker.get("maxDistance").getAsInt());
                    break;
                case "maxDistanceFromVisible":
                    this.targetChecker = new MaxDistanceFromVIsiblesDecorator(targetChecker, jsonTargetChecker.getAsJsonObject().get("maxDistance").getAsInt());
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


    @Override
    public boolean isComplete() {
        return !targets.isEmpty();
    }


    @Override
    public boolean isExplosive() {
        return roomExplosive || squareExplosive;
    }


    @Override
    public boolean isAdditionalDamage(){
        return additionalDamage;
    }


    @Override
    public boolean isSquareExplosive() {
        return squareExplosive;
    }


    @Override
    public boolean isRoomExplosive() {
        return roomExplosive;
    }


    @Override
    public boolean isExclusiveForOldTargets(){
        return exclusiveForOldTargets;
    }


    //methods for tests
    @Override
    public short getDamage() {
        return damage;
    }

    @Override
    public short getMarks() {
        return marks;
    }

    @Override
    public int getMaxNumberOfTargets() {
        return maxNumberOfTargets;
    }


    //end methods for test


    @Override
    public void selectPc(Pc targetPc) {
        if(!roomExplosive && !squareExplosive) {
            if (targets.size() == maxNumberOfTargets)
                targets.removeFirst();
            targets.add(targetPc);
        }
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (squareExplosive) {
            targetSquare.getPcs().addAll(targets);
        } else if (roomExplosive) {
            TargetChecker t = new SameRoomDecorator(new EmptyChecker());
            Set<Square> room = t.validSquares(targetSquare);
            room.forEach(s -> s.getPcs().addAll(targets));
        }
    }


    @Override
    public void resetAction() {
        targets.clear();
        orientedTargetChecker = null;
    }


    @Override
    public void apply(Pc shooter) {
        targets.forEach(pc -> {
            if (damage != 0)
                pc.takeDamage(shooter.getColour(), damage);
            if (marks != 0)
                pc.takeMarks(shooter.getColour(), marks);
        });
        resetAction();
    }


    private void addTarget (Pc targetPc){
        if (targets.size() == maxNumberOfTargets)
            targets.removeFirst();
        targets.add(targetPc);
    }

}
