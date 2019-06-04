package server.model.deserializers;

import com.google.gson.*;
import server.model.squares.AmmoSquare;
import server.model.squares.SpawnPoint;
import server.model.squares.Square;
import java.lang.reflect.Type;

public class SquareDeserializer implements JsonDeserializer<Square> {

    @Override
    public Square deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        boolean isSpawnPoint = json.getAsJsonObject().get("isSpawnPoint").getAsBoolean();

        if (isSpawnPoint)
            return context.deserialize(json, SpawnPoint.class);
        else
            return context.deserialize(json, AmmoSquare.class);
    }
}
