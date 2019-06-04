package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import server.model.deserializers.GameBoardDeserializer;
import org.junit.Test;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class GameBoardTest {

    private GameBoard gameBoard;

    @Before
    public void initFine() throws FileNotFoundException {
        int numberOfMap = 0;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/gameBoards.json"));
        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);
    }

    @Test
    public void checkVisibles(){
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 4; c++){
                Square tempSquare = gameBoard.getSquare(r, c);
                if (tempSquare != null) {
                    System.out.print(tempSquare.toPairString() + " ---- can see ---> ");
                    tempSquare.getVisibles().forEach(v -> System.out.print(v.toPairString() + " "));
                    System.out.println();
                } else {
                    System.out.println("not present");
                }
            }
    }
}
