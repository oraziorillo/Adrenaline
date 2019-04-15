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
        int i = 1; Action action; JSONObject tempAction;
        while (jsonWeaponEffect.get("action" + i) != null) {
            tempAction = (JSONObject) jsonWeaponEffect.get("action" + i);
            if ((boolean)tempAction.get("isDamageMarksAction")) {
                action = new DamageMarksAction(tempAction, this);
                actions.add(action);
            } else {
                action = new MovementAction(tempAction, this);
                actions.add(action);
            }
            i++;
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
