package server.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import server.model.GameBoard;
import server.model.squares.Square;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameBoardDeserializer implements JsonDeserializer<GameBoard> {

    @Override
    public GameBoard deserialize(JsonElement jsonGameBoard, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Square.class, new SquareDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type squaresType = new TypeToken<ArrayList<Square>>(){}.getType();

        return new GameBoard(
                jsonGameBoard.getAsJsonObject().get("rows").getAsInt(),
                jsonGameBoard.getAsJsonObject().get("columns").getAsInt(),
                customGson.fromJson(jsonGameBoard.getAsJsonObject().get("squares"), squaresType),
                customGson.fromJson(jsonGameBoard.getAsJsonObject().get("doors"), int[].class)
        );
    }
}
