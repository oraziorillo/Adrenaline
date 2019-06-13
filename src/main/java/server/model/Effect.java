package server.model;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import common.enums.CardinalDirectionEnum;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import java.lang.reflect.Type;
import java.util.*;

public class Effect {
    @Expose private boolean oriented;
    @Expose private boolean beyondWalls;
    @Expose private boolean asynchronous;
    @Expose private boolean sameTarget;
    @Expose private boolean memorizeTargetSquare;   //per il rocket launcher
    @Expose private short[] cost;
    @Expose private List<Action> actions;

    /**
     * constructor for Effect
     *
     * @param jsonEffect JsonObject representing a weapon effect
     */
    public Effect(JsonObject jsonEffect) {
        this.cost = new short[3];
        this.oriented = jsonEffect.get("oriented").getAsBoolean();
        this.beyondWalls = jsonEffect.get("beyondWalls").getAsBoolean();
        this.asynchronous = jsonEffect.get("asynchronous").getAsBoolean();
        this.sameTarget = jsonEffect.get("sameTarget").getAsBoolean();
        this.memorizeTargetSquare = jsonEffect.get("memorizeTargetSquare").getAsBoolean();

        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Action> actionDeserializer = new ActionDeserializer();
        gsonBuilder.registerTypeAdapter(Action.class, actionDeserializer);
        Gson customGson = gsonBuilder.create();

        Type actionsType = new TypeToken<LinkedList<Action>>(){}.getType();

        this.cost = customGson.fromJson(jsonEffect.get("cost"), short[].class);
        this.actions = customGson.fromJson(jsonEffect.get("actions"), actionsType);
    }

    public boolean isOriented() {
        return oriented;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    public boolean hasSameTarget() {
        return sameTarget;
    }

    public boolean memorizeTargetSquare() {
        return memorizeTargetSquare;
    }

    public void assignDirection(CardinalDirectionEnum direction) {
        actions.forEach(a -> a.setOrientedTargetChecker(direction, beyondWalls));
    }

    public short[] getCost() {
        return cost.clone();
    }

    public List<Action> getActions() {
        return actions;
    }

    public Action getActionAtIndex(int index) {
        return actions.get(index);
    }

    public Set<Pc> execute(Pc shooter) {
        Set<Pc> shotTargets = new HashSet<>();
        for (Action currAction : actions) {
            shotTargets.addAll(currAction.apply(shooter));
        }
        return shotTargets;
    }
}



