package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import common.enums.PcColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import server.model.deserializers.GameBoardDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class MovementOnMapTest {

    @Mock
    private Game game;
    private GameBoard gameBoard;
    private Pc pc1;


    @Before
    public void init() throws FileNotFoundException {
        int numberOfMap = 1;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(
                new FileReader("src/main/resources/json/game_boards/gameBoard" + (numberOfMap + 1) + ".json"));
        gameBoard = customGson.fromJson(reader, GameBoard.class);

        pc1 = new Pc(PcColourEnum.BLUE, game);
    }


    @Test
    public void spawnTest() {
        assertThrows(IllegalArgumentException.class, () -> pc1.spawn(gameBoard.getSquare(2, 2)));

        pc1.spawn(gameBoard.getSquare(1, 0));
        assertTrue(gameBoard.getSquare(1, 0).getPcs().contains(pc1));
    }


    @Test
    public void movementTest() {
        pc1.spawn(gameBoard.getSquare(1, 0));

        pc1.moveTo(gameBoard.getSquare(2, 2));
        assertFalse(gameBoard.getSquare(1, 0).getPcs().contains(pc1));
        assertTrue(gameBoard.getSquare(2, 2).getPcs().contains(pc1));


        assertThrows(IllegalArgumentException.class, () -> pc1.moveTo(gameBoard.getSquare(3, 3)));
        assertTrue(gameBoard.getSquare(2, 2).getPcs().contains(pc1));

        pc1.moveTo(gameBoard.getSquare(2, 3));
        assertFalse(gameBoard.getSquare(2, 2).getPcs().contains(pc1));
        assertTrue(gameBoard.getSquare(2, 3).getPcs().contains(pc1));
    }
}
