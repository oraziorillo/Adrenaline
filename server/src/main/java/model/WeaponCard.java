package model;

import enums.AmmoEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class WeaponCard {
    private String name;
    private boolean loaded;
    private AmmoEnum defaultAmmo;
    private short[] ammo;
    private short[] currentCost;
    //possiamo aggiungere qui un arraylist di powerUp usati con questo attacco in modo tale che quando
    //viene chiamato il metodo use questi powerup vengono effettivamente scartati (comprende soprattutto powerup
    //usati come ammo
    private ArrayList<WeaponEffect> fireModes = new ArrayList<>();
    private ArrayList<WeaponEffect> upgrades = new ArrayList<>();
    private LinkedList<WeaponEffect> currEffect;


    /**
     * constructor for WeaponCard
     * @param jsonWeaponCard JsonObject representing a WeaponCard
     */
    WeaponCard(JSONObject jsonWeaponCard) {
        this.name = (String) jsonWeaponCard.get("name");
        this.loaded = true;
        this.defaultAmmo = AmmoEnum.valueOf((String) jsonWeaponCard.get("firstAmmo"));
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
            //if currEffect isn't composed only by asynchronous moves, reset it
            if (!currEffect.stream().map(WeaponEffect::isAsynchronous).reduce(true, (a, b) -> a && b)) {
                this.currEffect = new LinkedList<>();
                this.currentCost = new short[3];
            }
            addEffect(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addUpgrade(int index) {
        WeaponEffect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            addEffect(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void removeUpgrade(int index) {
        if(upgrades.size() > index) {
            removeEffect(upgrades.get(index));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void addEffect(WeaponEffect eff) {
        for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
            currentCost[i] += eff.getCost()[i];
        this.currEffect.add(eff);
    }

    private void removeEffect(WeaponEffect eff){
        for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
            currentCost[i] -= eff.getCost()[i];
        currEffect.remove(eff);
    }


    public void use(Pc shooter) {
        for (WeaponEffect effect : currEffect) {
            effect.execute(shooter);
        }
        currEffect.clear();
    }
}
