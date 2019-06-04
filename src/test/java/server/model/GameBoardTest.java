package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import server.enums.CardinalDirectionEnum;
import server.model.deserializers.GameBoardDeserializer;
import org.junit.Test;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTest {

    private static final int ROW = 2;
    private static final int COL = 3;
    private static final int N_MAP = 3;

    private GameBoard gameBoard;

    @Before
    public void initFine() throws FileNotFoundException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/gameBoards.json"));

        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(N_MAP), GameBoard.class);
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


    @Test
    public void allSquaresTest(){
        Square mySquare = gameBoard.getSquare(2,1);
        Set<Square> allSquaresResult = mySquare.allSquares();
        Set<Square> squaresOnGameBoard = new HashSet<>();
        for (int i = 0;  i < 3; i++)
            for (int j = 0; j < 4; j++)
                squaresOnGameBoard.add(gameBoard.getSquare(i, j));
        assertEquals(allSquaresResult, squaresOnGameBoard);
    }


    @Test
    public void allSquaresOnDirectionTest(){
        Square mySquare = gameBoard.getSquare(ROW, COL);
        Set<Square> squaresOnDir = new HashSet<>();

        //check on east
        for (int j = COL; j < 4; j++)
            squaresOnDir.add(gameBoard.getSquare(ROW, j));
        assertEquals(squaresOnDir, mySquare.allSquaresOnDirection(CardinalDirectionEnum.EAST));
        squaresOnDir.clear();

        //check on south
        for (int j = ROW; j < 3; j++)
            squaresOnDir.add(gameBoard.getSquare(j, COL));
        assertEquals(squaresOnDir, mySquare.allSquaresOnDirection(CardinalDirectionEnum.SOUTH));
        squaresOnDir.clear();

        //check on west
        for (int j = 0; j <= COL; j++)
            squaresOnDir.add(gameBoard.getSquare(ROW, j));
        assertEquals(squaresOnDir, mySquare.allSquaresOnDirection(CardinalDirectionEnum.WEST));
        squaresOnDir.clear();

        //check on north
        for (int j = 0; j <= ROW; j++)
            squaresOnDir.add(gameBoard.getSquare(j, COL));
        assertEquals(squaresOnDir, mySquare.allSquaresOnDirection(CardinalDirectionEnum.NORTH));
        squaresOnDir.clear();

        //check on nullDir
        for (int i = 0; i < 3; i++)
            squaresOnDir.add(gameBoard.getSquare(i, COL));
        for (int j = 0; j < 4; j++)
            squaresOnDir.add(gameBoard.getSquare(ROW, j));
        assertEquals(squaresOnDir, mySquare.allSquaresOnDirection(null));
        squaresOnDir.clear();

        System.out.println("THE KING IN THE NORTH");
    }
}
