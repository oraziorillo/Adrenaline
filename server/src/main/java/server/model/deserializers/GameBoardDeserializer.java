package server.model.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import server.model.GameBoard;

import java.lang.reflect.Type;

public class GameBoardDeserializer implements JsonDeserializer<GameBoard> {

    @Override
    public GameBoard deserialize(JsonElement jsonGameBoard, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        /*
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Square.class, new SquareDeserializer());
        Gson customGson = gsonBuilder
                .registerTypeAdapter(Action.class, new ActionDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Type squaresType = new TypeToken<ArrayList<Square>>(){}.getType();

        return new GameBoard(
                jsonGameBoard.getAsJsonObject().get("rows").getAsInt(),
                jsonGameBoard.getAsJsonObject().get("columns").getAsInt(),
                customGson.fromJson(jsonGameBoard.getAsJsonObject().get("squares"), squaresType),
                customGson.fromJson(jsonGameBoard.getAsJsonObject().get("doors"), int[].class)
        );

         */
        return null;
    }
}
