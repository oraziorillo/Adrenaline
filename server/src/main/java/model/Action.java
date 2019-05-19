package model;

import enums.CardinalDirectionEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.LinkedList;
import java.util.Set;


public abstract class Action {

    int maxNumberOfTargets;
    boolean additionalDamage, exclusiveForOldTargets, explosive;
    TargetChecker basicTargetChecker, orientedTargetChecker;
    LinkedList<Pc> targets;
    Square targetSquare;



    Action (){
        this.maxNumberOfTargets = 1;
        this.basicTargetChecker = new EmptyChecker();
        this.targets = new LinkedList<>();
    }


    public boolean isAdditionalDamage(){
        return additionalDamage;
    }

    public boolean isExclusiveForOldTargets(){
        return exclusiveForOldTargets;
    }

    public void setTargetSquare(Square s){
        this.targetSquare = s;
    }


    public void setOrientedTargetChecker(CardinalDirectionEnum direction, boolean isBeyondWalls){
         orientedTargetChecker = isBeyondWalls ? new BeyondWallsStraightLineDecorator(basicTargetChecker, direction)
                                               : new SimpleStraightLineDecorator(basicTargetChecker, direction);
    }


    Set<Square> validSquares(Square shooterSquare) {
        return (orientedTargetChecker == null) ? basicTargetChecker.validSquares(shooterSquare)
                                               : orientedTargetChecker.validSquares(shooterSquare);
    }


    public void addTarget (Pc target){
        if (targets.size() == maxNumberOfTargets) {
            //targets.element().setTargetable(true);
            targets.removeFirst();
        }
        //target.setTargetable(false);
        targets.add(target);
    }


    abstract void apply(Pc shooter);
}




class DamageMarksAction extends Action {
    private short damage;
    private short marks;

    DamageMarksAction(JSONObject jsonAction) {
        super();
        this.damage = ((Long) jsonAction.get("damage")).shortValue();
        this.marks = ((Long) jsonAction.get("marks")).shortValue();
        this.maxNumberOfTargets = (int) jsonAction.get("maxNumberOfTargets");
        this.explosive = (boolean) jsonAction.get("explosive");
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


    @Override
    public void apply(Pc shooter) {
        if (explosive) {
            targetSquare.getPcs().addAll(targets);
        }
        targets.forEach(pc -> {
            if (damage != 0)
                pc.takeDamage(shooter.getColour(), damage);
            if (marks != 0)
                pc.takeMarks(shooter.getColour(), marks);
        });
        targets.clear();

    }
}



class MovementAction extends Action {
    private int maxWalkingDistance;
    private boolean visibleDestination;
    private Square destination;

    MovementAction(JSONObject jsonAction) {
        super();
        this.maxWalkingDistance = (int)jsonAction.get("maxWalkingDistance");
        this.destination = null;
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

    /**
     * sets the destination of the move action
     * @param destination tile of destination
     */
    public void setDestination(Square destination){
        this.destination = destination;
    }

    /*
    @Override
    public Set validTargets(Square referenceSquare) {
        return validSquares(referenceSquare);
    }

     */

    @Override
    public void apply(Pc shooter) {
        for (Pc pc : targets) {
            pc.moveTo(destination);
        }
        targets.clear();
    }
}

