package model.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import model.actions.Action;
import model.actions.DamageMarksAction;
import model.actions.MovementAction;
import java.lang.reflect.Type;

public class ActionDeserializer implements JsonDeserializer<Action> {

    @Override
    public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        boolean isMovement = json.getAsJsonObject().get("isMovement").getAsBoolean();
        if (isMovement)
            return new MovementAction(json.getAsJsonObject());
        else
            return new DamageMarksAction(json.getAsJsonObject());
    }


}