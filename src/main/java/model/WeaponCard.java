package model;

import model.Enumerations.AmmoEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class WeaponCard {
    protected Game currGame;
    private String name;
    private boolean loaded;
    private short[] ammos;
    private short[] currentCost = new short[3];
    private ArrayList<WeaponEffect> fireModes = new ArrayList<>();
    private ArrayList<WeaponEffect> upgrades = new ArrayList<>();
    private LinkedList<WeaponEffect> currEffect;


    /**
     * constructor for WeaponCard
     * @param jsonWeaponCard JsonObject representing a WeaponCard
     * @param game instance of currGame
     */
    WeaponCard(JSONObject jsonWeaponCard, Game game) {
        this.currGame = game;
        this.name = (String) jsonWeaponCard.get("name");
        this.loaded = true;
        this.ammos = new short[3];
        JSONArray jsonAmmos = (JSONArray) jsonWeaponCard.get("ammos");
        for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++) {
            ammos[i] = ((Long) jsonAmmos.get(i)).shortValue();
        }
        JSONArray jsonFireModes = (JSONArray) jsonWeaponCard.get("firemodes");
        for (Object jsonFireMode : jsonFireModes) {
            WeaponEffect weaponEffect = new WeaponEffect((JSONObject)jsonFireMode, game);
            this.fireModes.add(weaponEffect);
        }
        JSONArray jsonUpgrades = (JSONArray) jsonWeaponCard.get("upgrades");
        for (Object jsonUpgrade : jsonUpgrades) {
            WeaponEffect weaponEffect = new WeaponEffect((JSONObject)jsonUpgrade, game);
            this.upgrades.add(weaponEffect);
        }
    }

    public boolean isLoaded(){
        return loaded;
    }

    public String getName() {
        return name;
    }

    public short[] getAmmos() {
        return ammos;
    }

    public LinkedList getCurrentEffect(){
        return (LinkedList) currEffect.clone();
    }

    public short[] getCurrentCost(){
        return currentCost;
    }

    public void selectFireMode(int index){
        WeaponEffect eff;
        if(fireModes.size() > index) {
            eff = fireModes.get(index);
            this.currEffect = new LinkedList<>();
            currentCost[AmmoEnum.BLUE.ordinal()] = eff.getCost()[AmmoEnum.BLUE.ordinal()];
            currentCost[AmmoEnum.RED.ordinal()] = eff.getCost()[AmmoEnum.RED.ordinal()];
            currentCost[AmmoEnum.YELLOW.ordinal()] = eff.getCost()[AmmoEnum.YELLOW.ordinal()];
            this.currEffect.add(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addUpgrade(int index) {
        WeaponEffect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            currentCost[AmmoEnum.BLUE.ordinal()] += eff.getCost()[AmmoEnum.BLUE.ordinal()];
            currentCost[AmmoEnum.RED.ordinal()] += eff.getCost()[AmmoEnum.RED.ordinal()];
            currentCost[AmmoEnum.YELLOW.ordinal()] += eff.getCost()[AmmoEnum.YELLOW.ordinal()];
            this.currEffect.add(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void removeUpgrade(int index) {
        WeaponEffect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            currentCost[AmmoEnum.BLUE.ordinal()] -= eff.getCost()[AmmoEnum.BLUE.ordinal()];
            currentCost[AmmoEnum.RED.ordinal()] -= eff.getCost()[AmmoEnum.RED.ordinal()];
            currentCost[AmmoEnum.YELLOW.ordinal()] -= eff.getCost()[AmmoEnum.YELLOW.ordinal()];
            currEffect.remove(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void use() {
        for (WeaponEffect effect : currEffect) {
            effect.execute();
        }
        currEffect.clear();
    }
}
