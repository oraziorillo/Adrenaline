package server.model.serializers;

import com.google.gson.*;
import server.model.target_checkers.TargetChecker;

import java.lang.reflect.Type;

public class TargetCheckerSerializer implements JsonSerializer<TargetChecker> {

    @Override
    public JsonElement serialize(TargetChecker src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject json = new JsonObject();

        JsonArray jsonTargetChecker = new JsonArray();

        TargetChecker base = src.getbase();
        if (base != null)
            jsonTargetChecker.add(context.serialize(base));

        String type = src.type();
        if (type != null) {
            json.addProperty("type", type);

            String propertyName = src.propertyName();
            int propertyValue;
            if (propertyName != null) {
                propertyValue = src.propertyValue();
                json.addProperty(propertyName, propertyValue);
            }
            jsonTargetChecker.add(json);
        }

        return jsonTargetChecker;
    }
}
