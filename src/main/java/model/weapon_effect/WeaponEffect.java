package model.weapon_effect;

import model.Server;
import model.WeaponCard;
import org.json.simple.JSONObject;
import java.util.Collections;
import java.util.LinkedList;


public class WeaponEffect {
    private WeaponCard card;
    private short[] cost;
    private LinkedList<Action> actions;



    /**
     * constructor for WeaponEffect
     * @param jsonName name of json file containing card effects
     * @param card card in which this effect is used
     */
    public WeaponEffect(String jsonName, WeaponCard card) {
        try {
            JSONObject jsonObject = (JSONObject) Server.readJson(jsonName);
            this.card = card;
            this.cost = (short[]) jsonObject.get("cost");
            this.actions = new LinkedList<>();
            Action[] temp = (Action[]) jsonObject.get("actions");
            Collections.addAll(actions, temp);

        } catch (Exception e) {
            e.printStackTrace();
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
