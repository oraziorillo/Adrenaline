package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import server.enums.CardinalDirectionEnum;
import server.model.GameBoard;
import server.model.deserializers.GameBoardDeserializer;
import server.model.squares.Square;
import server.model.target_checkers.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TargetCheckerTest {

    //test executed with map number 0
    private GameBoard gameBoard;
    int row = 1;
    int coloumn = 2;


    @Before
    public void initMap() throws FileNotFoundException {
        int numberOfMap = 2;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/gameBoards.json"));
        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);
    }


    @Test
    public void emptyCheckerReturnsAllSquaresOfTheGameBoard() {
        EmptyChecker baseChecker = new EmptyChecker();
        assertEquals(gameBoard.getSquare(row, coloumn).allSquares(), baseChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void visibilityCheckerReturnsAllVisiblesOfADefinedsquare() {
        VisibilityDecorator visibilityChecker = new VisibilityDecorator(new EmptyChecker());
        assertEquals(gameBoard.getSquare(row, coloumn).getVisibles(), visibilityChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void blindnessCheckerReturnsAllNonVisibileSquaresFromTheSelectedOne() {
        BlindnessDecorator blindnessChecker = new BlindnessDecorator(new EmptyChecker());
        Set<Square> validSquaresNonVisible = new HashSet<>();
        validSquaresNonVisible.add(gameBoard.getSquare(0, 3)); validSquaresNonVisible.add(gameBoard.getSquare(1, 0));
        validSquaresNonVisible.add(gameBoard.getSquare(1, 1)); validSquaresNonVisible.add(gameBoard.getSquare(2, 1));
        assertEquals(validSquaresNonVisible, blindnessChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void minDistanceCheckerReturnsAllSquareAtTheSelectedMinimumDistance() {
        int minDistance2 = 2;
        MinDistanceDecorator minDistanceChecker = new MinDistanceDecorator(new EmptyChecker(), minDistance2);
        Set<Square> validSquaresMinDistance = new HashSet<>();
        validSquaresMinDistance.add(gameBoard.getSquare(0, 0)); validSquaresMinDistance.add(gameBoard.getSquare(0, 1));
        validSquaresMinDistance.add(gameBoard.getSquare(0, 3)); validSquaresMinDistance.add(gameBoard.getSquare(1, 0));
        validSquaresMinDistance.add(gameBoard.getSquare(2, 1)); validSquaresMinDistance.add(gameBoard.getSquare(2, 3));
        validSquaresMinDistance.add(gameBoard.getSquare(1, 1));
        assertEquals(validSquaresMinDistance, minDistanceChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void maxDistanceCheckerReturnsAllSquareAtTheSelectedMaximumDistance() {
        int maxDistance = 2;
        MaxDistanceDecorator maxDistanceChecker = new MaxDistanceDecorator(new EmptyChecker(), maxDistance);
        Set<Square> validSquaresMaxDistance = new HashSet<>();
        validSquaresMaxDistance.add(gameBoard.getSquare(0, 1)); validSquaresMaxDistance.add(gameBoard.getSquare(0, 2));
        validSquaresMaxDistance.add(gameBoard.getSquare(1, 2)); validSquaresMaxDistance.add(gameBoard.getSquare(1, 3));
        validSquaresMaxDistance.add(gameBoard.getSquare(0, 3)); validSquaresMaxDistance.add(gameBoard.getSquare(2, 1));
        validSquaresMaxDistance.add(gameBoard.getSquare(2, 2)); validSquaresMaxDistance.add(gameBoard.getSquare(2, 3));
        assertEquals(validSquaresMaxDistance, maxDistanceChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void sameRoomCheckerReturnsSquaresOfTheSameRoomWithRespectToTheSelectedOne() {
        SameRoomDecorator sameRoomChecker = new SameRoomDecorator(new EmptyChecker());
        Set<Square> validSquaresSameYellowRoom = new HashSet<>();
        Set<Square> validSquaresSameBlueRoom = new HashSet<>();

        validSquaresSameYellowRoom.add(gameBoard.getSquare(row, coloumn)); validSquaresSameYellowRoom.add(gameBoard.getSquare(1, 3));
        validSquaresSameYellowRoom.add(gameBoard.getSquare(2, 2)); validSquaresSameYellowRoom.add(gameBoard.getSquare(2, 3));

        validSquaresSameBlueRoom.add(gameBoard.getSquare(0, 0)); validSquaresSameBlueRoom.add(gameBoard.getSquare(0, 1));
        validSquaresSameBlueRoom.add(gameBoard.getSquare(0, 2));

        assertEquals(validSquaresSameYellowRoom, sameRoomChecker.validSquares(gameBoard.getSquare(row, coloumn)));
        assertEquals(validSquaresSameBlueRoom, sameRoomChecker.validSquares(gameBoard.getSquare(0 ,0)));
    }


    @Test
    public void differentRoomCheckerReturnsSquaresOfDifferentRoomWithRespectToTheSelectedOne() {
        DifferentRoomDecorator differentRoomChecker = new DifferentRoomDecorator(new EmptyChecker());
        Set<Square> validSquaresDifferentRoom = new HashSet<>();
        validSquaresDifferentRoom.add(gameBoard.getSquare(0, 0)); validSquaresDifferentRoom.add(gameBoard.getSquare(0, 1));
        validSquaresDifferentRoom.add(gameBoard.getSquare(0, 2)); validSquaresDifferentRoom.add(gameBoard.getSquare(0, 3));
        validSquaresDifferentRoom.add(gameBoard.getSquare(1, 0)); validSquaresDifferentRoom.add(gameBoard.getSquare(1, 1));
        validSquaresDifferentRoom.add(gameBoard.getSquare(2, 1));
        assertEquals(validSquaresDifferentRoom, differentRoomChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void simpleStraightLineCheckerReturnsAllSquareInTheSelectedDirection() {
        SimpleStraightLineDecorator simpleStraightLineChecker = new SimpleStraightLineDecorator(new EmptyChecker(), CardinalDirectionEnum.EAST);
        Set<Square> validOrientedSquares = new HashSet<>();
        validOrientedSquares.add(gameBoard.getSquare(0, 0)); validOrientedSquares.add(gameBoard.getSquare(0, 1));
        validOrientedSquares.add(gameBoard.getSquare(0, 2)); validOrientedSquares.add(gameBoard.getSquare(0, 3));
        assertEquals(validOrientedSquares, simpleStraightLineChecker.validSquares(gameBoard.getSquare(0, 0)));
    }


    @Test
    public void simpleStraightLineCheckerReturnsSquaresInAllDirectionsAndThenSpecificDirection() {
        SimpleStraightLineDecorator AllDirectionsStraightLineChecker = new SimpleStraightLineDecorator(new EmptyChecker(), null);
        Set<Square> allDirectionsSquares = new HashSet<>();
        allDirectionsSquares.add(gameBoard.getSquare(row, coloumn)); allDirectionsSquares.add(gameBoard.getSquare(0, 2));
        allDirectionsSquares.add(gameBoard.getSquare(1, 3)); allDirectionsSquares.add(gameBoard.getSquare(2, 2));
        assertEquals(allDirectionsSquares, AllDirectionsStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));
        SimpleStraightLineDecorator selectedDirectionStraightLineChecker = new SimpleStraightLineDecorator(AllDirectionsStraightLineChecker, CardinalDirectionEnum.SOUTH);
        Set<Square> selectedDirectionSquares = new HashSet<>();
        selectedDirectionSquares.add(gameBoard.getSquare(row, coloumn)); selectedDirectionSquares.add(gameBoard.getSquare(2, 2));
        assertEquals(selectedDirectionSquares, selectedDirectionStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void beyondWallsStraightLineCheckerReturnAllSquaresInTheSelectedDirection() {
        BeyondWallsStraightLineDecorator beyondWallsStraightLineChecker = new BeyondWallsStraightLineDecorator(new EmptyChecker(), CardinalDirectionEnum.WEST);
        Set<Square> selectedDirectionSquares = new HashSet<>();
        selectedDirectionSquares.add(gameBoard.getSquare(row, coloumn)); selectedDirectionSquares.add(gameBoard.getSquare(1, 0));
        selectedDirectionSquares.add(gameBoard.getSquare(1, 1));
        assertEquals(selectedDirectionSquares, beyondWallsStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void beyondWallsStraightLineCheckerReturnsSquaresInAllDirectionsAndThenSpecificDirection() {
        BeyondWallsStraightLineDecorator beyondWallsStraightLineChecker = new BeyondWallsStraightLineDecorator(new EmptyChecker(), null);
        Set<Square> allDirectionsSquares = new HashSet<>();
        allDirectionsSquares.add(gameBoard.getSquare(row, coloumn)); allDirectionsSquares.add(gameBoard.getSquare(1, 0));
        allDirectionsSquares.add(gameBoard.getSquare(1, 1)); allDirectionsSquares.add(gameBoard.getSquare(1, 3));
        allDirectionsSquares.add(gameBoard.getSquare(0, 2)); allDirectionsSquares.add(gameBoard.getSquare(2, 2));
        assertEquals(allDirectionsSquares, beyondWallsStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));
        BeyondWallsStraightLineDecorator orientedBeyondWallsStraightLineChecker = new BeyondWallsStraightLineDecorator(beyondWallsStraightLineChecker, CardinalDirectionEnum.EAST);
        Set<Square> selectedDirectionSquares = new HashSet<>();
        selectedDirectionSquares.add(gameBoard.getSquare(row, coloumn)); selectedDirectionSquares.add(gameBoard.getSquare(1, 3));
        assertEquals(selectedDirectionSquares, orientedBeyondWallsStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void maxDistanceFromVisibleCheckerReturnsAllSquaresAtMaxDIstanceFromEachVisibleSquare() {
        int maxDistance = 1;
        MaxDistanceFromVIsiblesDecorator maxDistanceFromVisiblesChecker = new MaxDistanceFromVIsiblesDecorator(new EmptyChecker(), maxDistance);
        Set<Square> validSquares = gameBoard.getSquare(row, coloumn).allSquares();
        validSquares.remove(gameBoard.getSquare(1, 1));
        assertEquals(validSquares, maxDistanceFromVisiblesChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }

    //TODO test in cui si combinano gli effetti dei targetChecker
}
