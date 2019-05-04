package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.LinkedList;

public class WeaponEffect {
    protected Game game;
    private short[] cost;
    private LinkedList<Action> actions = new LinkedList<>();

    /**
     * constructor for WeaponEffect
     * @param jsonWeaponEffect JsonObject representing a weapon effect
     * @param game instance of currGame
     */
    public WeaponEffect(JSONObject jsonWeaponEffect, Game game) {
        this.game = game;
        this.cost = new short[3];
        JSONArray jsonCost = (JSONArray) jsonWeaponEffect.get("cost");
        JSONArray jsonActions = (JSONArray) jsonWeaponEffect.get("actions");
        for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++) {
            cost[i] = ((Long) jsonCost.get(i)).shortValue();
        }
        for (Object jsonAction : jsonActions) {
            Action action;
            if ((boolean)(((JSONObject)jsonAction).get("isMovement"))) {
                action = new MovementAction((JSONObject)jsonAction, game);
            } else {
                action = new DamageMarksAction((JSONObject)jsonAction, game);
            }
            actions.add(action);
        }
    }

    public short[] getCost(){
        return cost.clone();
    }

    public void execute() {
        for (Action currAction : actions) {
                currAction.apply();
            }
    }
}
