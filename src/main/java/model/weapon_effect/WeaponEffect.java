package model.weapon_effect;

import model.Pc;
import model.Server;
import model.target_checker.TargetChecker;
import org.json.simple.JSONObject;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class WeaponEffect {
    private short[] cost;
    private LinkedList<Action> actions;
    private HashSet<Pc> targets;
    private TargetChecker targetChecker;


    public WeaponEffect(String jsonName) {
        try {
            JSONObject jsonObject = (JSONObject) Server.readJson(jsonName);
            this.cost = (short[]) jsonObject.get("cost");
            this.targets = new HashSet<>();
            //this.targetChecker =
            this.actions = new LinkedList<>();
            Action[] temp = (Action[]) jsonObject.get("actions");
            Collections.addAll(actions, temp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public short[] getCost(){
        return cost.clone();
    }

    public Set<Pc> validTargets() {
        //TODO metodo incompleto
        HashSet<Pc> validTargets = new HashSet<>();
        for (Pc pc : game.getCharacters())
            if (targetChecker.isValid(pc))
                validTargets.add(pc);
        return validTargets;
    }

    public void execute() {
        for (Pc currPc : targets){
            for (Action currAction : actions) {
                currAction.applyOn(currPc);
            }
        }
        targets.clear();
    }
}
