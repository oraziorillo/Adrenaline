package model;

import model.Enumerations.AmmoEnum;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class WeaponCard {
    private Deck<WeaponCard> weaponCardDeck;
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
     * @param deck deck in which this card is put
     */
    WeaponCard(JSONObject jsonWeaponCard, Deck<WeaponCard> deck) {
        this.weaponCardDeck = deck;
        this.name = (String) jsonWeaponCard.get("name");
        this.loaded = true;
        this.ammos = (short[]) jsonWeaponCard.get("ammos");
        for (int i = 1; jsonWeaponCard.get("fireMode" + i) != null; i++) {
            WeaponEffect weaponEffect = new WeaponEffect((JSONObject) jsonWeaponCard.get("fireMode" + i), this);
            fireModes.add(weaponEffect);
        }
        for (int i = 1; jsonWeaponCard.get("upgrade" + i) != null; i++) {
            WeaponEffect weaponEffect = new WeaponEffect((JSONObject) jsonWeaponCard.get("upgrade" + i), this);
            upgrades.add(weaponEffect);
        }
        this.currEffect = new LinkedList<>();
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

    public void addEffect(int index) {
        WeaponEffect eff=fireModes.get(index);
        currentCost[AmmoEnum.BLUE.ordinal()] += eff.getCost()[AmmoEnum.BLUE.ordinal()];
        currentCost[AmmoEnum.RED.ordinal()] += eff.getCost()[AmmoEnum.RED.ordinal()];
        currentCost[AmmoEnum.YELLOW.ordinal()] += eff.getCost()[AmmoEnum.YELLOW.ordinal()];
        currEffect.add(eff);
    }

    public void removeEffect(int index) {
        WeaponEffect eff=fireModes.get(index);
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
