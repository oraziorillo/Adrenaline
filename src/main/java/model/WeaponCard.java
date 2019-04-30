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
    //possiamo aggiungere qui un arraylist di powerUp usati con questo attacco in modo tale che quando
    //viene chiamato il metodo use questi powerup vengono effettivamente scartati (comprende soprattutto powerup
    //usati come ammo
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
        JSONObject[] jsonFireModes = (JSONObject[]) jsonWeaponCard.get("firemodes");
        for (JSONObject jsonFireMode : jsonFireModes) {
            WeaponEffect weaponEffect = new WeaponEffect(jsonFireMode, this);
            this.fireModes.add(weaponEffect);
        }
        JSONObject[] jsonUpgrades = (JSONObject[]) jsonWeaponCard.get("upgrades");
        for (JSONObject jsonUpgrade : jsonUpgrades) {
            WeaponEffect weaponEffect = new WeaponEffect(jsonUpgrade, this);
            this.upgrades.add(weaponEffect);
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

    public void selectFireMode(int index){
        WeaponEffect eff = fireModes.get(index);
        this.currEffect = new LinkedList<>();
        currentCost[AmmoEnum.BLUE.ordinal()] = eff.getCost()[AmmoEnum.BLUE.ordinal()];
        currentCost[AmmoEnum.RED.ordinal()] = eff.getCost()[AmmoEnum.RED.ordinal()];
        currentCost[AmmoEnum.YELLOW.ordinal()] = eff.getCost()[AmmoEnum.YELLOW.ordinal()];
        this.currEffect.add(eff);
    }

    public void addUpgrade(int index) {
        WeaponEffect eff = upgrades.get(index);
        currentCost[AmmoEnum.BLUE.ordinal()] += eff.getCost()[AmmoEnum.BLUE.ordinal()];
        currentCost[AmmoEnum.RED.ordinal()] += eff.getCost()[AmmoEnum.RED.ordinal()];
        currentCost[AmmoEnum.YELLOW.ordinal()] += eff.getCost()[AmmoEnum.YELLOW.ordinal()];
        this.currEffect.add(eff);
    }

    public void removeUpgrade(int index) {
        WeaponEffect eff = upgrades.get(index);
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
        //qui dovrebbero essere scalati gli ammo al giocatore??
    }
}
