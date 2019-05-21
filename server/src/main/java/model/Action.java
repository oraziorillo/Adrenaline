package model;

import enums.CardinalDirectionEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public abstract class Action {

    boolean optional;
    TargetChecker basicTargetChecker, orientedTargetChecker;
    Square targetSquare;



    Action (JSONObject jsonAction){
        this.optional = (boolean) jsonAction.get("optional");
        this.basicTargetChecker = new EmptyChecker();
    }


    public boolean isOptional() {
        return optional;
    }

    public void selectPc(Pc targetPc){}

    public void selectSquare(Square targetSquare){}

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

    public Set<Square> validSquares(Square shooterSquare) {
        return (orientedTargetChecker == null) ? basicTargetChecker.validSquares(shooterSquare)
                                               : orientedTargetChecker.validSquares(shooterSquare);
    }





    public abstract void apply(Pc shooter);
}



class DamageMarksAction extends Action {
    private short damage;
    private short marks;
    private LinkedList<Pc> targets;
    private int maxNumberOfTargets;
    private boolean squareExplosive, roomExplosive, additionalDamage, exclusiveForOldTargets;

    DamageMarksAction(JSONObject jsonAction) {
        super(jsonAction);
        this.targets = new LinkedList<>();
        this.damage = ((Long) jsonAction.get("damage")).shortValue();
        this.marks = ((Long) jsonAction.get("marks")).shortValue();
        this.maxNumberOfTargets = (int) jsonAction.get("maxNumberOfTargets");
        this.squareExplosive = (boolean) jsonAction.get("squareExplosive");
        this.roomExplosive = (boolean) jsonAction.get("roomExplosive");
        this.additionalDamage = (boolean) jsonAction.get("additionalDamage");
        this.exclusiveForOldTargets = (boolean) jsonAction.get("exclusiveForOldTargets");
        JSONArray jsonTargetCheckers = (JSONArray) jsonAction.get("targetChecker");
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


    public boolean isAdditionalDamage(){
        return additionalDamage;
    }

    public boolean isExclusiveForOldTargets(){
        return exclusiveForOldTargets;
    }


    private void addTarget (Pc target){
        if (targets.size() == maxNumberOfTargets)
            targets.removeFirst();
        targets.add(target);
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

    /*
    @Override
    public Set validTargets(Square referenceSquare) {
        if (isExplosive) {
            Set<Square> validSquares = validSquares(referenceSquare);
            validSquares.forEach(s -> s.setTargetable(true));
            return validSquares;
        } else {
            HashSet<Pc> validPcs = new HashSet<>();
            for (Square t : validSquares(referenceSquare)) {
                validPcs.addAll(t.getPcs());
            }
            validPcs.forEach(p -> p.setTargetable(true));
            return validPcs;
        }
    }

     */
}



class MovementAction extends Action {
    private boolean selfMovement;
    private Pc target;
    private TargetChecker destinationChecker;

    MovementAction(JSONObject jsonAction) {
        super(jsonAction);
        this.selfMovement = (boolean)jsonAction.get("selfMovement");
        JSONArray jsonCheckers;
        JSONObject jsonChecker;

        jsonCheckers = (JSONArray) jsonAction.get("targetChecker");
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
        //TODO target non è settato non fa nulla, a meno che il target è lo stesso dell'azione precedente
        this.targetSquare = targetSquare;
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


    /*
    @Override
    public Set validTargets(Square referenceSquare) {
        return validSquares(referenceSquare);
    }

     */
}

