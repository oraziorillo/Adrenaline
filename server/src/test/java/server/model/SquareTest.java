package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.enums.SquareColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import server.model.deserializers.SquareDeserializer;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class SquareTest {
    
    private Square tested;
    private Square onRight, s1,s2,s3;
    private Pc pc;
    private final int row = 2;
    private final int coloumn = 2;
    private final SquareColourEnum colour = SquareColourEnum.BLUE;
    private GameBoard gameBoard;
    private Deck<WeaponCard> weaponsDeck;
    private Deck<AmmoTile> ammoTileDeck;

    @Mock
    private ModelEventHandler eventHandler;



    @Mock
    private AmmoTile ammoTile;


    @Before
    public void ammoTileDeckConstructionFine() throws FileNotFoundException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type ammoTileType = new TypeToken<ArrayList<AmmoTile>>(){}.getType();

        JsonReader reader;

        reader = new JsonReader(new FileReader("src/main/resources/json/ammoTiles.json"));
        ArrayList<AmmoTile> ammoTiles = gson.fromJson(reader, ammoTileType);

        ammoTileDeck = new Deck<>();
        ammoTiles.forEach(a -> ammoTileDeck.add(a));
    }



    @Before
    public void weaponsDeckConstrucionFine() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type weaponsType = new TypeToken<ArrayList<WeaponCard>>(){}.getType();

        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
        ArrayList<WeaponCard> weapons = customGson.fromJson(reader, weaponsType);

        weaponsDeck = new Deck<>();
        weapons.forEach(w -> weaponsDeck.add(w));
    }



    @Before
    public void init() throws FileNotFoundException {
        int numberOfMap = 2;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Square.class, new SquareDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(
                new FileReader("src/main/resources/json/game_boards/gameBoard" + (numberOfMap + 1) + ".json"));
        gameBoard = customGson.fromJson(reader, GameBoard.class);

        gameBoard.addModelEventHandler(eventHandler);
        gameBoard.init(weaponsDeck, ammoTileDeck);
        pc = new Pc(PcColourEnum.BLUE, null);
        pc.addModelEventHandler(eventHandler);
        tested = gameBoard.getSquare(row, coloumn);
        tested.addModelEventHandler(eventHandler);

    }



   @Test
   public void SettersWorksFine(){
        tested.setTargetable( true );
        assertTrue( tested.isTargetable() );
   }



    @Test
    public void addAndRemovePcWorksAsOnSets(){
        Pc pc = Mockito.mock( Pc.class );
        assumeTrue( tested.getPcs().isEmpty() );
        tested.addPc( pc );
        assertEquals( 1, tested.getPcs().size() );
        assertTrue( tested.getPcs().contains( pc ) );
        tested.removePc( pc );
        assertFalse( tested.getPcs().contains( pc ) );
        assertTrue( tested.getPcs().isEmpty() );
    }
    
    @Test
    public void onCardinalDirectionReturnsTheVisibleSquareOnTheSelectedDirectionIfThereIsOne(){
        onRight = gameBoard.getSquare(row+1, coloumn);
        assertSame(onRight, tested.onDirection( CardinalDirectionEnum.EAST ) );
    }
    
    @Test
    public void onCardinalDirectionReturnsNullWhenNoSquareInThatDirection(){
        assertSame(null, tested.onDirection( CardinalDirectionEnum.SOUTH ));
    }
    
    @Test
    public void atDistanceReturnedCollectionContainsAllTheInterestedSquaresAndNoMore(){
        int distance = 1;
        Collection<Square> ok = new HashSet<>();
        Collection<Square> nope = new HashSet<>();
        ok.add( gameBoard.getSquare(row, coloumn) );
        ok.add( gameBoard.getSquare(row-1, coloumn) );
        ok.add( gameBoard.getSquare( row, coloumn+1) );
        ok.add( gameBoard.getSquare( row, coloumn-1));
        nope.add( s3 );
        assertFalse( tested.atDistance( distance ).stream().anyMatch( nope::contains ) );
        assertEquals( tested.atDistance( distance ), ok );
    }
    
    @Test
    public void atDistanceThrowsExceptionWithNegativeDistances(){
        assertThrows( IllegalArgumentException.class,()->tested.atDistance( -1 ) );
    }
   
    @Test
    public void atDistanceReturnsCallingSquareOnlyWithDistance0(){
        Collection<Square> result = tested.atDistance( 0 );
        assertEquals( 1, result.size() );
        assertTrue( result.contains( tested ) );
    }


    @Test
    public void allSquaresInDirectionWorksFine() {
        Collection<Square> ok = new HashSet<>();
        ok.add( gameBoard.getSquare(row, coloumn) );
        ok.add( gameBoard.getSquare(row, coloumn-1) );
        assertEquals(tested.allSquaresOnDirection(CardinalDirectionEnum.WEST), ok );
    }
    //TODO test di allSquaresInDirection e allSquares


    @Test
    public void allSquaresWorksFine() {
        Collection<Square> ok = new HashSet<>();
        ok.add( gameBoard.getSquare(row, coloumn) );
        ok.add( gameBoard.getSquare(row, coloumn-1) );
        ok.add( gameBoard.getSquare(row, coloumn+1) );
        ok.add( gameBoard.getSquare(row-1, coloumn) );
        ok.add( gameBoard.getSquare(row-1, coloumn+1) );
        ok.add( gameBoard.getSquare(row-1, coloumn-1) );
        ok.add( gameBoard.getSquare(row-1, coloumn-2) );
        ok.add( gameBoard.getSquare(row-2, coloumn-1) );
        ok.add( gameBoard.getSquare(row-2, coloumn) );
        ok.add( gameBoard.getSquare(row-2, coloumn-1) );
        ok.add( gameBoard.getSquare(row-2, coloumn+1) );
        ok.add( gameBoard.getSquare(row-2, coloumn-2) );
        assertEquals(tested.allSquares(), ok);
    }
}

