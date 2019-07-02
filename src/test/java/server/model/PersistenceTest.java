package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import server.database.GameInfo;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import server.model.deserializers.GameBoardDeserializer;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class PersistenceTest {

    private GameInfo gameInfo;

    @Before
    public void initSavedGameFine() throws FileNotFoundException {

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Action.class, new ActionDeserializer())
                .registerTypeAdapter(GameBoard.class, new GameBoardDeserializer())
                .create();

        JsonReader reader = new JsonReader(new FileReader("src/main/java/server/database/files/2d6499c2-0d2f-4e71-9030-cd5e2b10637f.json"));
        gameInfo = gson.fromJson(reader, GameInfo.class);
    }


    @Test
    public void squaresInitFine() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Square s = gameInfo.getGame().getSquare(i, j);
                if (s != null)
                    System.out.print(s.toString() + "\t\t");
                else
                    System.out.print("null\t\t");
            }
            System.out.print("\n");
        }
    }
}
