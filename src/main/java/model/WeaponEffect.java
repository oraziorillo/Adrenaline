package model;

import org.json.simple.JSONObject;
import java.util.LinkedList;

public class WeaponEffect {
    private WeaponCard card;
    private short[] cost;
    private LinkedList<Action> actions = new LinkedList<>();



    /**
     * constructor for WeaponEffect
     * @param jsonWeaponEffect JsonObject representing a weapon effect
     * @param card card in which this effect is used
     */
    public WeaponEffect(JSONObject jsonWeaponEffect, WeaponCard card) {
        this.card = card;
        this.cost = (short[]) jsonWeaponEffect.get("cost");
        JSONObject[] jsonActions = (JSONObject[]) jsonWeaponEffect.get("actions");
        for (JSONObject jsonAction : jsonActions) {
            Action action;
            if ((boolean)jsonAction.get("isMovement")) {
                action = new MovementAction(jsonAction, this);
            } else {
                action = new DamageMarksAction(jsonAction, this);
            }
            actions.add(action);
        }
    }

    public WeaponCard getCard(){
        return this.card;
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
