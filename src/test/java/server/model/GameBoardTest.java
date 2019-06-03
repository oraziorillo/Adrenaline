package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import server.model.deserializers.GameBoardDeserializer;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class GameBoardTest {

    private GameBoard gameBoard;

    @Test
    public void initFine() throws FileNotFoundException {
        int numberOfMap = 0;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        //TODO pesca la risorsa dinamicamente
        //URL path = getClass().getResource( "/json/gameBoards.json" );
        JsonReader reader = new JsonReader(new FileReader("/home/alessio/Scrivania/progettoJavaGit/ing-sw-2019-23/src/main/resources/json/gameBoards.json"));
        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);
    }
}
