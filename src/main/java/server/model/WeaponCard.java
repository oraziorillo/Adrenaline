package server.model;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import server.enums.AmmoEnum;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class WeaponCard {
    @Expose private String name;
    @Expose private AmmoEnum colour;
    @Expose private boolean chained;
    @Expose private short[] ammo;
    @Expose private List<Effect> fireModes;
    @Expose private List<Effect> upgrades;
    private boolean loaded;
    private LinkedList<Effect> effectsToApply;
    private short[] currentCost;


    WeaponCard(){
        this.loaded = true;
        this.effectsToApply = new LinkedList<>();
        this.currentCost = new short[3];
    }

    /**
     * constructor for WeaponCard
     * @param jsonWeaponCard JsonObject representing a WeaponCard
     */
    public WeaponCard(JsonObject jsonWeaponCard) {
        this.name = jsonWeaponCard.get("name").getAsString();
        this.colour = AmmoEnum.valueOf(jsonWeaponCard.get("colour").getAsString());
        this.chained = jsonWeaponCard.get("chained").getAsBoolean();
        this.ammo = new short[3];
        this.currentCost = new short[3];
        this.loaded = true;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type effectsType = new TypeToken<LinkedList<Effect>>(){}.getType();

        this.ammo = customGson.fromJson(jsonWeaponCard.get("ammo"), short[].class);
        this.fireModes = customGson.fromJson(jsonWeaponCard.get("fireModes"), effectsType);
        this.upgrades = customGson.fromJson(jsonWeaponCard.get("upgrades"), effectsType);
    }

    public void init(){
        effectsToApply.add(fireModes.get(0));
    }

    public boolean isChained() {
        return chained;
    }

    public boolean isLoaded(){
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public String getName() {
        return name;
    }

    public short[] getAmmo() {
        return ammo;
    }

    public AmmoEnum getColour() {
        return colour;
    }

    public List<Effect> getEffectsToApply(){
        return effectsToApply;
    }

    public short[] getCurrentCost(){
        return currentCost;
    }

    public List<Effect> getFireModes(){
        return fireModes;
    }

    public List<Effect> getUpgrades() {
        return upgrades;
    }

    public void selectFireMode(int index){
        Effect eff;
        if(fireModes.size() > index) {
            eff = fireModes.get(index);
            //if effectsToApply isn't composed only by asynchronous moves, reset it
            if (!effectsToApply.stream().map(Effect::isAsynchronous).reduce(true, (a, b) -> a && b)) {
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
        Effect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                currentCost[i] += eff.getCost()[i];
            this.effectsToApply.add(eff);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void pushFirstUpgrade(int index) {
        if(upgrades.size() > index) {
            Effect eff = upgrades.get(index);
            if (eff.isAsynchronous()) {
                for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                    currentCost[i] += eff.getCost()[i];
                this.effectsToApply.addFirst(eff);
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    public void removeUpgrade(int index) {
        Effect eff;
        if(upgrades.size() > index) {
            eff = upgrades.get(index);
            if (effectsToApply.remove(eff)) {
                for (int i = 0; i < Constants.AMMO_COLOURS_NUMBER; i++)
                    currentCost[i] -= eff.getCost()[i];
                effectsToApply.remove(eff);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void reset() {
        effectsToApply.clear();
        effectsToApply.add(fireModes.get(0));
        currentCost = new short[3];
    }
}
