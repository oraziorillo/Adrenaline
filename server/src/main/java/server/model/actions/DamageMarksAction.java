package server.model.actions;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import common.enums.CardinalDirectionEnum;
import server.model.Pc;
import server.model.squares.Square;
import server.model.target_checkers.*;

import java.util.Set;
import java.util.stream.Collectors;

public class DamageMarksAction extends Action {
    @Expose private short damage;
    @Expose private short marks;
    @Expose private boolean squareExplosive;
    @Expose private boolean roomExplosive;
    @Expose private boolean additionalDamage;
    @Expose private boolean exclusiveForOldTargets;
    @Expose private boolean targetsOnDifferentSquares;
    private TargetChecker orientedTargetChecker;


    public DamageMarksAction() {
        super();
    }

    public DamageMarksAction(JsonObject jsonAction) {
        super(jsonAction);
        this.damage = jsonAction.get("damage").getAsShort();
        this.marks = jsonAction.get("marks").getAsShort();
        this.squareExplosive = jsonAction.get("squareExplosive").getAsBoolean();
        this.roomExplosive = jsonAction.get("roomExplosive").getAsBoolean();
        this.additionalDamage = jsonAction.get("additionalDamage").getAsBoolean();
        this.exclusiveForOldTargets = jsonAction.get("exclusiveForOldTargets").getAsBoolean();
        this.targetsOnDifferentSquares = jsonAction.get("targetsOnDifferentSquares").getAsBoolean();
    }


    @Override
    public boolean isComplete() {
        return !isParameterized() || !targets.isEmpty();
    }


    @Override
    public boolean isSelfMovement() {
        return false;
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


    @Override
    public void setOrientedTargetChecker(CardinalDirectionEnum direction, boolean isBeyondWalls){
        orientedTargetChecker = isBeyondWalls ? new BeyondWallsStraightLineDecorator(targetChecker, direction)
                : new SimpleStraightLineDecorator(targetChecker, direction);
    }


    public Set<Square> validSquares(Square shooterSquare) {
        return (orientedTargetChecker == null) ? targetChecker.validSquares(shooterSquare)
                : orientedTargetChecker.validSquares(shooterSquare);
    }


    @Override
    public void selectPc(Pc targetPc) {
        if(!roomExplosive && !squareExplosive) {
            if (targets.size() < maxNumberOfTargets){
                if (targetsOnDifferentSquares){
                    if (targets.stream().filter(pc -> pc.getCurrSquare() == targetPc.getCurrSquare()).collect(Collectors.toList()).isEmpty())
                        targets.add(targetPc);
                }
                targets.add(targetPc);
            }
        }
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (this.targetSquare == null) {
            if (squareExplosive) {
                this.targetSquare = targetSquare;
                targets.addAll(targetSquare.getPcs());
            } else if (roomExplosive) {
                this.targetSquare = targetSquare;
                TargetChecker t = new SameRoomDecorator(new EmptyChecker());
                Set<Square> room = t.validSquares(targetSquare);
                room.forEach(s -> targets.addAll(s.getPcs()));
            }
        }
    }


    @Override
    public void resetAction() {
        targetSquare = null;
        targets.clear();
        orientedTargetChecker = null;
    }


    @Override
    public Set<Pc> apply(Pc shooter) {
        //if shooter has been added to the targets set, it is removed before damage is applied
        targets.remove(shooter);
        targets.forEach(pc -> {
            if (damage != 0)
                pc.takeDamage(shooter.getColour(), damage);
            if (marks != 0)
                pc.takeMarks(shooter.getColour(), marks);
            pc.notifyDamageMarks(shooter.getName(), damage, marks);
        });
        return targets;
    }
}
