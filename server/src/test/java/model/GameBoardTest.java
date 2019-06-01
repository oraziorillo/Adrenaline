package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import model.deserializers.GameBoardDeserializer;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GameBoardTest {

    private GameBoard gameBoard;

    @Test
    public void initFine() throws FileNotFoundException {
        int numberOfMap = 0;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(new FileReader("/home/orazio/Documents/ids_progetto/ing-sw-2019-23/json/gameBoards.json"));
        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);
    }
}
