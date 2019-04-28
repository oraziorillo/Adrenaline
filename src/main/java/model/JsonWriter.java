package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonWriter {

    public static void main(String[] args) throws Exception {
        writeJsonWeapons();

    }

    /**
     * translate array into a JSONArray
     * @param array array to be translated
     * @return JSONArray translation of array
     */
    private static JSONArray addAllToJsonArray(ArrayList<JSONObject> array) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(array);
        return jsonArray;
    }

    /**
     * builder for a JSONObject target checker
     * @param type name of target checker
     * @param bound in case of max/min distance
     * @return a JSONObject representing a target checker
     */
    private static JSONObject buildTargetChecker(String type, Integer bound) {
        JSONObject targetChecker = new JSONObject();
        targetChecker.put("type", type);
        if (bound != null)
            targetChecker.put("bound", bound);
        return targetChecker;
    }
    /**
     * builder for a a JSONObject action
     * @param isMovement boolean representing the type of the action
     * @param damage number of damage to deal
     * @param marks number of marks to apply
     * @param targetChecker type of target checker to adopt
     * @return a JSONObject representing an action
     */
    private static JSONObject buildJsonAction(boolean isMovement, int damage, int marks, JSONArray targetChecker){
        JSONObject action = new JSONObject();
        action.put("isMovement", isMovement);
        action.put("damage", damage);
        action.put("marks", marks);
        action.put("targetChecker", targetChecker);
        return action;
    }

    /**
     * builder for a JSONObject effect
     * @param cost cost of the effect
     * @param actions actions composing the effect
     * @return a JSONObject representing a weapon effect
     */
    private static JSONObject buildJsonEffect(JSONArray cost, JSONArray actions){
        JSONObject effect = new JSONObject();
        effect.put("cost", cost);
        effect.put("actions", actions);
        return effect;
    }

    /**
     * writes a json file representing a single weapon
     * @param name name of the weapon
     * @param ammos ammos of the weapon
     * @param firemodes firemodes of the weapon
     * @param upgrades possible upgrades to apply on the weapon
     * @throws Exception exception thrown by write method
     */
    private static JSONObject buildJsonWeapon(String name, JSONArray ammos, JSONArray firemodes, JSONArray upgrades){
        JSONObject weapon = new JSONObject();
        weapon.put("name", name);
        weapon.put("ammos", ammos);
        weapon.put("firemodes", firemodes);
        weapon.put("upgrades", upgrades);
        return weapon;
    }

    private static void writeJsonWeapons() throws Exception{
        JSONArray weapons = new JSONArray();
        JSONArray ammos, cost;
        ArrayList<JSONObject> firemodes, upgrades, actions, targetCheckers;

        //build Lock Rifle and add it to weapons array
        ammos = new JSONArray();
        ammos.add(2); ammos.add(0); ammos.add(0);

        cost = new JSONArray();
        cost.add(0); cost.add(0); cost.add(0);
        targetCheckers = new ArrayList<>();
        targetCheckers.add(buildTargetChecker("visible", null));
        actions = new ArrayList<>();
        actions.add(buildJsonAction(false, 2, 1, addAllToJsonArray(targetCheckers)));
        firemodes = new ArrayList<>();
        firemodes.add(buildJsonEffect(cost, addAllToJsonArray(actions)));

        cost = new JSONArray();
        cost.add(0); cost.add(1); cost.add(0);
        actions = new ArrayList<>();
        actions.add(buildJsonAction(false, 0, 1, addAllToJsonArray(targetCheckers)));
        upgrades = new ArrayList<>();
        upgrades.add(buildJsonEffect(cost, addAllToJsonArray(actions)));

        weapons.add(buildJsonWeapon("Lock Rifle", ammos, addAllToJsonArray(firemodes), addAllToJsonArray(upgrades)));

        //write json
        Files.write(Paths.get("weapons"), weapons.toJSONString().getBytes());
    }
}
