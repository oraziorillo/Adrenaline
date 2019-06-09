package server.model.actions;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import server.model.*;
import server.model.squares.Square;
import server.model.target_checkers.*;
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
        this.damage = jsonAction.get("damage").getAsShort();
        this.marks = jsonAction.get("marks").getAsShort();
        this.squareExplosive = jsonAction.get("squareExplosive").getAsBoolean();
        this.roomExplosive = jsonAction.get("roomExplosive").getAsBoolean();
        this.additionalDamage = jsonAction.get("additionalDamage").getAsBoolean();
        this.exclusiveForOldTargets = jsonAction.get("exclusiveForOldTargets").getAsBoolean();
    }


    @Override
    public boolean isComplete() {
        return !isParameterized() || !targets.isEmpty();
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
}
