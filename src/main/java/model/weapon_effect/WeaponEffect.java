package model.weapon_effect;

import model.Character;
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
    private HashSet<Character> targets;
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

    public Set<Character> validTargets() {
        //TODO metodo incompleto
        HashSet<Character> validTargets = new HashSet<>();
        for (Character character : game.getCharacters())
            if (targetChecker.isValid(character))
                validTargets.add(character);
        return validTargets;
    }

    public void execute() {
        for (Character currCharacter : targets){
            for (Action currAction : actions) {
                currAction.applyOn(currCharacter);
            }
        }
        targets.clear();
    }
}
