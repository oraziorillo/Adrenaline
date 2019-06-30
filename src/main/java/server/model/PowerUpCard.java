package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import common.dto_model.PowerUpCardDTO;
import common.enums.AmmoEnum;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * Represents a power up card
 */
public class PowerUpCard {

    @Expose private String name;
    @Expose private Action action;
    @Expose private AmmoEnum colour;
    private boolean selectedAsAmmo;

    public PowerUpCard(JsonObject jsonPowerUpCard){
        this.name = jsonPowerUpCard.get("name").getAsString();
        this.colour = AmmoEnum.valueOf(jsonPowerUpCard.get("colour").getAsString());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type actionType = new TypeToken<Action>(){}.getType();

        this.action = customGson.fromJson(jsonPowerUpCard.get("action"), actionType);
    }


    public String getName() {
        return name;
    }

    public boolean isSelectedAsAmmo() {
        return selectedAsAmmo;
    }

    public AmmoEnum getColour(){ return this.colour; }

    public Action getAction() {
        return action;
    }

    public void useAction(Pc shooter) {
        action.apply(shooter);
    }

    public void setSelectedAsAmmo(boolean selectedAsAmmo) {
        this.selectedAsAmmo = selectedAsAmmo;
    }


    public PowerUpCardDTO convertToDTO(){
        PowerUpCardDTO powerUpCardDTO = new PowerUpCardDTO();
        powerUpCardDTO.setName(name);
        powerUpCardDTO.setColour(colour);
        return powerUpCardDTO;
    }

}

