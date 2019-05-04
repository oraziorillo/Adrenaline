package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public abstract class Action {
    Game currGame;
    TargetChecker targetChecker;
    LinkedList<Pc> targets;

    Action (Game currGame){
        this.currGame = currGame;
        targetChecker = new EmptyChecker();
        targets = new LinkedList<>();
    }

    /**
     * executes the action
     */
    abstract void apply();

    /**
     * selects the targets that are valid for the current action
     *
     * @return a Set of all possible target Tiles
     */
    public Set<Tile> validTargetTiles() {
        return targetChecker.validTiles();
    }
}




class DamageMarksAction extends Action {
    private short damage;
    private short marks;

    DamageMarksAction(JSONObject jsonAction, Game game){
        super(game);
        this.damage = ((Long) jsonAction.get("damage")).shortValue();
        this.marks = ((Long) jsonAction.get("marks")).shortValue();
        JSONArray jsonTargetCheckers = (JSONArray) jsonAction.get("targetCheckers");
        JSONObject jsonTargetChecker;
        for(int i = 0; i < jsonTargetCheckers.size(); i++) {
            jsonTargetChecker = (JSONObject) jsonTargetCheckers.get(i);
            switch ((String)jsonTargetChecker.get("type")){
                case "visible":
                    this.targetChecker = new VisibleDecorator(targetChecker, null);
                    break;
                case "blindness":
                    this.targetChecker = new BlindnessDecorator(targetChecker, null);
                    break;
                case "minDistance":
                    this.targetChecker = new MinDistanceDecorator(targetChecker, (JSONObject)jsonTargetChecker);
                    break;
                case "maxDistance":
                    this.targetChecker = new MaxDistanceDecorator(targetChecker, (JSONObject)jsonTargetChecker);
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

    public Set<Pc> validTargets(){
        HashSet<Pc> validTargets = new HashSet<>();
        for (Tile t : validTargetTiles()) {
            validTargets.addAll(t.getPcs());
        }
        return validTargets;
    }


    @Override
    public void apply() {
        for (Pc pc: targets) {
            if (damage != 0)
                pc.takeDamage(damage);
            if (marks != 0)
                pc.takeMarks(marks);
        }
        targets.clear();
    }

    /**
     * add a set of Pc to the list of the targets of the action
     * @param pcs list containing Pcs on which damage/marks must be applied
     */
    public void addTargets(List<Pc> pcs){
        targets.addAll(pcs);
    }
}



class MovementAction extends Action {
    private int maxDist;
    private Tile destination;

    MovementAction(JSONObject jsonAction, Game game) {
        super(game);
        this.maxDist = (int)jsonAction.get("maxDist");
        this.destination = null;
    }

    /**
     * sets the destination of the move action
     * @param destination tile of destination
     */
    public void setDestination(Tile destination){
        this.destination = destination;
    }

    @Override
    public void apply() {
        for (Pc pc : targets) {
            pc.move(destination, maxDist);
        }
        targets.clear();
    }
}

