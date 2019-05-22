package model;

import enums.CardinalDirectionEnum;
import model.actions.Action;
import model.actions.DamageMarksAction;
import model.actions.MovementAction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.LinkedList;

public class WeaponEffect {
    private boolean oriented;
    private boolean beyondWalls;
    private boolean asynchronous;
    private boolean sameTarget;
    private short[] cost;
    private LinkedList<Action> actions = new LinkedList<>();

    /**
     * constructor for WeaponEffect
     * @param jsonWeaponEffect JsonObject representing a weapon effect
     */
    WeaponEffect(JSONObject jsonWeaponEffect) {
        this.cost = new short[3];
        this.oriented = (boolean) jsonWeaponEffect.get("oriented");
        this.beyondWalls = (boolean) jsonWeaponEffect.get("beyondWalls");
        this.asynchronous = (boolean) jsonWeaponEffect.get("asynchronous");
        this.sameTarget = (boolean) jsonWeaponEffect.get("sameTarget");
        JSONArray jsonCost = (JSONArray) jsonWeaponEffect.get("cost");
        JSONArray jsonActions = (JSONArray) jsonWeaponEffect.get("actions");
        for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++) {
            cost[i] = ((Long) jsonCost.get(i)).shortValue();
        }
        for (Object jsonAction : jsonActions) {
            Action action;
            if ((boolean)(((JSONObject)jsonAction).get("isMovement"))) {
                action = new MovementAction((JSONObject)jsonAction);
            } else {
                action = new DamageMarksAction((JSONObject)jsonAction);
            }
            actions.add(action);
        }
    }

    public boolean isOriented(){
        return oriented;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    public boolean hasSameTarget() {
        return sameTarget;
    }

    public void assignDirection(CardinalDirectionEnum direction){
        actions.forEach(a -> a.setOrientedTargetChecker(direction, beyondWalls));
    }

    public short[] getCost(){
        return cost.clone();
    }

    public void execute(Pc shooter) {
        for (Action currAction : actions) {
                currAction.apply(shooter);
            }
    }
}
