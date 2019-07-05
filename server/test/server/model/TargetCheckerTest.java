package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import common.enums.CardinalDirectionEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import server.model.deserializers.SquareDeserializer;
import server.model.squares.Square;
import server.model.target_checkers.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TargetCheckerTest {

    //test executed with map number 1
    private GameBoard gameBoard;
    private static final int row = 1;
    private static final int coloumn = 2;
    @Mock
    private ModelEventHandler eventHandler;
    @Mock
    private Deck<WeaponCard> weaponCardDeck;
    @Mock
    private Deck<AmmoTile> ammoTileDeck;


    @Before
    public void initMap() throws FileNotFoundException {
        int numberOfMap = 1;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Square.class, new SquareDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(
                new FileReader("src/main/resources/json/game_boards/gameBoard" + numberOfMap + ".json"));
        gameBoard = customGson.fromJson(reader, GameBoard.class);
        WeaponCard card1 = new WeaponCard();
        WeaponCard card2 = new WeaponCard();
        WeaponCard card3 = new WeaponCard();
        WeaponCard card4 = new WeaponCard();
        WeaponCard card5 = new WeaponCard();
        WeaponCard card6 = new WeaponCard();
        WeaponCard card7 = new WeaponCard();
        WeaponCard card8 = new WeaponCard();
        WeaponCard card9 = new WeaponCard();
        short[] ammo = new short[3];
        AmmoTile ammoTile1 = new AmmoTile(ammo, false);
        AmmoTile ammoTile2 = new AmmoTile(ammo, false);
        AmmoTile ammoTile3 = new AmmoTile(ammo, false);
        AmmoTile ammoTile4 = new AmmoTile(ammo, false);
        AmmoTile ammoTile5 = new AmmoTile(ammo, false);
        AmmoTile ammoTile6 = new AmmoTile(ammo, false);
        AmmoTile ammoTile7 = new AmmoTile(ammo, false);
        Mockito.when(weaponCardDeck.draw()).thenReturn(card1).thenReturn(card2).thenReturn(card3)
                                           .thenReturn(card4).thenReturn(card5).thenReturn(card6)
                                           .thenReturn(card7).thenReturn(card8).thenReturn(card9);
        Mockito.when(ammoTileDeck.draw()).thenReturn(ammoTile1).thenReturn(ammoTile2).thenReturn(ammoTile3)
                .thenReturn(ammoTile4).thenReturn(ammoTile5).thenReturn(ammoTile6).thenReturn(ammoTile7);
        gameBoard.init(weaponCardDeck, ammoTileDeck);
        gameBoard.addModelEventHandler(eventHandler);
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
        TargetChecker blindnessChecker = new BlindnessDecorator(new EmptyChecker());
        Set<Square> validSquaresNonVisible = new HashSet<>();
        validSquaresNonVisible.add(gameBoard.getSquare(2, 1));
        validSquaresNonVisible.add(gameBoard.getSquare(2, 2));
        assertEquals(validSquaresNonVisible, blindnessChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void minDistanceCheckerReturnsAllSquareAtTheSelectedMinimumDistance() {
        int minDistance2 = 2;
        MinDistanceDecorator minDistanceChecker = new MinDistanceDecorator(new EmptyChecker(), minDistance2);
        Set<Square> validSquaresMinDistance = new HashSet<>();
        validSquaresMinDistance.add(gameBoard.getSquare(0, 0));
        validSquaresMinDistance.add(gameBoard.getSquare(0, 1));
        validSquaresMinDistance.add(gameBoard.getSquare(1, 0));
        validSquaresMinDistance.add(gameBoard.getSquare(2, 1));
        validSquaresMinDistance.add(gameBoard.getSquare(2, 2));
        validSquaresMinDistance.add(gameBoard.getSquare(2, 3));
        assertEquals(validSquaresMinDistance, minDistanceChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void maxDistanceCheckerReturnsAllSquareAtTheSelectedMaximumDistance() {
        int maxDistance = 2;
        MaxDistanceDecorator maxDistanceChecker = new MaxDistanceDecorator(new EmptyChecker(), maxDistance);
        Set<Square> validSquaresMaxDistance = new HashSet<>();
        validSquaresMaxDistance.add(gameBoard.getSquare(0, 1));
        validSquaresMaxDistance.add(gameBoard.getSquare(0, 2));
        validSquaresMaxDistance.add(gameBoard.getSquare(1, 0));
        validSquaresMaxDistance.add(gameBoard.getSquare(1, 1));
        validSquaresMaxDistance.add(gameBoard.getSquare(1, 2));
        validSquaresMaxDistance.add(gameBoard.getSquare(1, 3));
        validSquaresMaxDistance.add(gameBoard.getSquare(2, 1));
        validSquaresMaxDistance.add(gameBoard.getSquare(2, 3));
        assertEquals(validSquaresMaxDistance, maxDistanceChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void sameRoomCheckerReturnsSquaresOfTheSameRoomWithRespectToTheSelectedOne() {
        SameRoomDecorator sameRoomChecker = new SameRoomDecorator(new EmptyChecker());
        Set<Square> validSquaresSameYellowRoom = new HashSet<>();
        Set<Square> validSquaresSameBlueRoom = new HashSet<>();

        validSquaresSameYellowRoom.add(gameBoard.getSquare(row, coloumn));
        validSquaresSameYellowRoom.add(gameBoard.getSquare(1, 0));
        validSquaresSameYellowRoom.add(gameBoard.getSquare(1, 1));

        assertEquals(validSquaresSameYellowRoom, sameRoomChecker.validSquares(gameBoard.getSquare(row, coloumn)));

        validSquaresSameBlueRoom.add(gameBoard.getSquare(0, 0));
        validSquaresSameBlueRoom.add(gameBoard.getSquare(0, 1));
        validSquaresSameBlueRoom.add(gameBoard.getSquare(0, 2));

        assertEquals(validSquaresSameBlueRoom, sameRoomChecker.validSquares(gameBoard.getSquare(0 ,0)));
    }


    @Test
    public void differentRoomCheckerReturnsSquaresOfDifferentRoomWithRespectToTheSelectedOne() {
        DifferentRoomDecorator differentRoomChecker = new DifferentRoomDecorator(new EmptyChecker());
        Set<Square> validSquaresDifferentRoom = new HashSet<>();
        validSquaresDifferentRoom.add(gameBoard.getSquare(0, 0));
        validSquaresDifferentRoom.add(gameBoard.getSquare(0, 1));
        validSquaresDifferentRoom.add(gameBoard.getSquare(0, 2));
        validSquaresDifferentRoom.add(gameBoard.getSquare(1, 3));
        validSquaresDifferentRoom.add(gameBoard.getSquare(2, 1));
        validSquaresDifferentRoom.add(gameBoard.getSquare(2, 2));
        validSquaresDifferentRoom.add(gameBoard.getSquare(2, 3));
        assertEquals(validSquaresDifferentRoom, differentRoomChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void simpleStraightLineCheckerReturnsAllSquareInTheSelectedDirection() {
        SimpleStraightLineDecorator simpleStraightLineChecker = new SimpleStraightLineDecorator(
                new EmptyChecker(), CardinalDirectionEnum.EAST);
        Set<Square> validOrientedSquares = new HashSet<>();
        validOrientedSquares.add(gameBoard.getSquare(0, 0));
        validOrientedSquares.add(gameBoard.getSquare(0, 1));
        validOrientedSquares.add(gameBoard.getSquare(0, 2));
        assertEquals(validOrientedSquares, simpleStraightLineChecker.validSquares(gameBoard.getSquare(0, 0)));
    }


    @Test
    public void simpleStraightLineCheckerReturnsSquaresInAllDirectionsAndThenSpecificDirection() {
        SimpleStraightLineDecorator AllDirectionsStraightLineChecker = new SimpleStraightLineDecorator(
                new EmptyChecker(), null);
        Set<Square> allDirectionsSquares = new HashSet<>();
        allDirectionsSquares.add(gameBoard.getSquare(row, coloumn));
        allDirectionsSquares.add(gameBoard.getSquare(0, 2));
        allDirectionsSquares.add(gameBoard.getSquare(1, 3));
        allDirectionsSquares.add(gameBoard.getSquare(1, 0));
        allDirectionsSquares.add(gameBoard.getSquare(1, 1));

        assertEquals(allDirectionsSquares, AllDirectionsStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));

        SimpleStraightLineDecorator selectedDirectionStraightLineChecker = new SimpleStraightLineDecorator(
                AllDirectionsStraightLineChecker, CardinalDirectionEnum.SOUTH);
        Set<Square> selectedDirectionSquares = new HashSet<>();
        selectedDirectionSquares.add(gameBoard.getSquare(row, coloumn));

        assertEquals(selectedDirectionSquares, selectedDirectionStraightLineChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }


    @Test
    public void beyondWallsStraightLineCheckerReturnAllSquaresInTheSelectedDirection() {
        BeyondWallsStraightLineDecorator beyondWallsStraightLineChecker = new BeyondWallsStraightLineDecorator(
                new EmptyChecker(), CardinalDirectionEnum.WEST);
        Set<Square> selectedDirectionSquares = new HashSet<>();
        selectedDirectionSquares.add(gameBoard.getSquare(row, coloumn));
        selectedDirectionSquares.add(gameBoard.getSquare(1, 0));
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
    public void maxDistanceFromVisibleCheckerReturnsAllSquaresAtMaxDistanceFromEachVisibleSquare() {
        int maxDistance = 1;
        MaxDistanceFromVIsiblesDecorator maxDistanceFromVisiblesChecker = new MaxDistanceFromVIsiblesDecorator(new EmptyChecker(), maxDistance);
        Set<Square> validSquares = gameBoard.getSquare(row, coloumn).allSquares();
        assertEquals(validSquares, maxDistanceFromVisiblesChecker.validSquares(gameBoard.getSquare(row, coloumn)));
    }
}
