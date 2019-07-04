package server.model.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import server.model.actions.Action;
import server.model.actions.DamageMarksAction;
import server.model.actions.MovementAction;

import java.lang.reflect.Type;

public class ActionSerializer implements JsonSerializer<Action> {

    @Override
    public JsonElement serialize(Action src, Type typeOfSrc, JsonSerializationContext context) {
        return src.isMovement()
                ? context.serialize(src, MovementAction.class)
                : context.serialize(src, DamageMarksAction.class);
    }
}
