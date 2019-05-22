package model;

import enums.AmmoEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class WeaponCard {
    private String name;
    private boolean loaded;
    private AmmoEnum weaponColour;
    private short[] ammo;
    private short[] currentCost;
    private ArrayList<WeaponEffect> fireModes = new ArrayList<>();
    private ArrayList<WeaponEffect> upgrades = new ArrayList<>();
    private LinkedList<WeaponEffect> effectsToApply;


    /**
     * constructor for WeaponCard
     * @param jsonWeaponCard JsonObject representing a WeaponCard
     */
    WeaponCard(JSONObject jsonWeaponCard) {
        this.name = (String) jsonWeaponCard.get("name");
        this.loaded = true;
        this.weaponColour = AmmoEnum.valueOf((String) jsonWeaponCard.get("firstAmmo"));
        this.ammo = new short[3];
        this.currentCost = new short[3];
        JSONArray jsonAmmos = (JSONArray) jsonWeaponCard.get("ammo");
        for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++) {
            ammo[i] = ((Long) jsonAmmos.get(i)).shortValue();
        }
        JSONArray jsonFireModes = (JSONArray) jsonWeaponCard.get("firemodes");
        for (Object jsonFireMode : jsonFireModes) {
            WeaponEffect weaponEffect = new WeaponEffect((JSONObject)jsonFireMode);
            this.fireModes.add(weaponEffect);
        }
        JSONArray jsonUpgrades = (JSONArray) jsonWeaponCard.get("upgrades");
        for (Object jsonUpgrade : jsonUpgrades) {
            WeaponEffect weaponEffect = new WeaponEffect((JSONObject)jsonUpgrade);
            this.upgrades.add(weaponEffect);
        }
        this.effectsToApply = new LinkedList<>();
        effectsToApply.add(fireModes.get(0));
    }

    public boolean isLoaded(){
        return loaded;
    }

    public String getName() {
        return name;
    }

    public short[] getAmmo() {
        return ammo;
    }

    public AmmoEnum getWeaponColour() {
        return weaponColour;
    }

    public LinkedList<WeaponEffect> getEffectsToApply(){
        return effectsToApply;
    }

    public short[] getCurrentCost(){
        return currentCost;
    }

    public ArrayList<WeaponEffect> getFireModes(){
        return fireModes;
    }

    public ArrayList<WeaponEffect> getUpgrades() {
        return upgrades;
    }

    public void selectFireMode(int index){
        WeaponEffect eff;
        if(fireModes.size() > index) {
            eff = fireModes.get(index);
            //if effectsToApply isn't composed only by asynchronous moves, reset it
            if (!effectsToApply.stream().map(WeaponEffect::isAsynchronous).reduce(true, (a, b) -> a && b)) {
                this.effectsToApply = new LinkedList<>();
                this.currentCost = new short[3];
            }
            for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                currentCost[i] += eff.getCost()[i];
            this.effectsToApply.add(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addUpgrade(int index) {
        WeaponEffect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                currentCost[i] += eff.getCost()[i];
            this.effectsToApply.add(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addFirst(int index) {
        WeaponEffect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                currentCost[i] += eff.getCost()[i];
            this.effectsToApply.addFirst(eff);
        } else {
            throw new IllegalArgumentException();
        }

    }

    public void removeUpgrade(int index) {
        WeaponEffect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                currentCost[i] -= eff.getCost()[i];
            effectsToApply.remove(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void clear() {
        effectsToApply.clear();
    }
}
