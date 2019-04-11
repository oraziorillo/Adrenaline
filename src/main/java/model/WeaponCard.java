package model;

import model.weapon_effect.WeaponEffect;
import org.json.simple.JSONObject;
import java.util.LinkedList;

public abstract class WeaponCard {
    private Deck<WeaponCard> weaponCardDeck;
    private boolean loaded;
    private String name;
    private short[] ammos;
    private short[] currentCost = new short[3];
    private WeaponEffect[] fireModes;
    private WeaponEffect[] upgrades;
    private LinkedList<WeaponEffect> currEffect;


    /**
     * constructor for WeaponCard
     * @param jsonName name of the json file on which cards are described
     * @param deck deck in which this card is put
     */
    WeaponCard(String jsonName, Deck deck) {
        try {
            weaponCardDeck = deck;
            JSONObject jsonObject = (JSONObject) Server.readJson(jsonName);
            this.name = (String) jsonObject.get("name");
            this.ammos = (short[]) jsonObject.get("ammos");
            //this.fireModes =
            //this.upgrades =
            this.currEffect = new LinkedList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Deck getDeck(){
        return weaponCardDeck;
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

    public short[] getCurrentCost(){
        return currentCost;
    }

    public WeaponEffect[] getFireModes() {
        return fireModes.clone();
    }

    public WeaponEffect[] getUpgrades() {
        return upgrades;
    }

    public void addEffect(WeaponEffect eff) {
        currentCost[AmmoEnum.BLUE.ordinal()] += eff.getCost()[AmmoEnum.BLUE.ordinal()];
        currentCost[AmmoEnum.RED.ordinal()] += eff.getCost()[AmmoEnum.RED.ordinal()];
        currentCost[AmmoEnum.YELLOW.ordinal()] += eff.getCost()[AmmoEnum.YELLOW.ordinal()];
        currEffect.add(eff);
    }

    public void removeEffect(WeaponEffect eff) {
        currentCost[AmmoEnum.BLUE.ordinal()] -= eff.getCost()[AmmoEnum.BLUE.ordinal()];
        currentCost[AmmoEnum.RED.ordinal()] -= eff.getCost()[AmmoEnum.RED.ordinal()];
        currentCost[AmmoEnum.YELLOW.ordinal()] -= eff.getCost()[AmmoEnum.YELLOW.ordinal()];
        currEffect.remove(eff);
    }

    public void use() {
        for (WeaponEffect effect : currEffect) {
            effect.execute();
        }
        currEffect.clear();
    }
}
