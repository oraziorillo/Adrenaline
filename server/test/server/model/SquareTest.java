package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import server.model.deserializers.SquareDeserializer;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    @Mock
    private ModelEventHandler eventHandler;

    @Mock
    private Deck<AmmoTile> uselessDeck;



//    @Before
//    public void setupAndContructorTest(){
//        AmmoTile uselessTile = Mockito.mock( AmmoTile.class );
//        setupSquareMocks();
//        tested = new AmmoSquare(row, coloumn,colour );
//        tested.addModelEventHandler(eventHandler);
//        //test constructor
//        assertTrue( tested.getVisibles().isEmpty() );assertEquals(tested.getRow(), row);
//        assertEquals( tested.getCol(), coloumn);
//        assertEquals( tested.getColour(), colour );
//        assertFalse( tested.isTargetable() );
//        assertTrue( tested.getPcs().isEmpty() );
//        tested.addVisible( onRight );
//        tested.addVisible( s1 );
//        tested.addVisible( s2 );
//        tested.addVisible( s3 );
//    }
//
//    /**
//     * assicurarsi che non ci sia nessun tile a sinistra di tested
//     */
//    private void setupSquareMocks(){
//        onRight = Mockito.mock( Square.class );
//        s1 =  Mockito.mock( Square.class );
//        s2 = Mockito.mock( Square.class );
//        s3 = Mockito.mock( Square.class );
//        /*Mantenere la minimappa dei tile aggiornata
//        **|niente|s3     |
//        **|s2    |s1     |
//        **|tested|onright|
//         */
//        Mockito.when( onRight.getRow() ).thenReturn( row );
//        Mockito.when( onRight.getCol() ).thenReturn(coloumn + 1);
//        Mockito.when( s1.getRow() ).thenReturn( row - 1 );
//        Mockito.when( s1.getCol() ).thenReturn( coloumn + 1 );
//        Mockito.when( s2.getRow() ).thenReturn(row - 1);
//        Mockito.when( s2.getCol() ).thenReturn( coloumn );
//        Mockito.when( s3.getRow() ).thenReturn( row - 2 );
//        Mockito.when( s3.getCol() ).thenReturn( coloumn + 1 );
//        HashSet<Square> onRightVis = new HashSet<>(); onRightVis.add( tested ); onRightVis.add( s1 );
//        HashSet<Square> s1Vis = new HashSet<>(); s1Vis.add( tested ); s1Vis.add( s2 ); s1Vis.add( s3 );
//        HashSet<Square> s2Vis = new HashSet<>(); s2Vis.add( tested ); s2Vis.add( s1 );
//        HashSet<Square> s3Vis = new HashSet<>(); s3Vis.add( s1 );
//        Mockito.when( onRight.getVisibles() ).thenReturn( onRightVis );
//        Mockito.when( s1.getVisibles() ).thenReturn( s1Vis );
//        Mockito.when( s2.getVisibles() ).thenReturn( s2Vis );
//        Mockito.when( s3.getVisibles() ).thenReturn( s3Vis );
//        //dij: distanza j da i
//        HashSet<Square> d10 = new HashSet<>();        d10.add( s1 );                                        Mockito.when( s1.atDistance( 0 ) ).thenReturn( d10 );
//        HashSet<Square> d11 = new HashSet<>(d10);     d11.add( s3 ); d11.add( s2 ); d11.add( onRight );     Mockito.when( s1.atDistance( 1 ) ).thenReturn( d11 );
//        HashSet<Square> d12 = new HashSet<>(d11);     d12.add( tested );                                    Mockito.when( s1.atDistance( 2 ) ).thenReturn( d12 );
//        HashSet<Square> dr0  = new HashSet<>();       dr0.add( onRight );                                   Mockito.when( onRight.atDistance( 0 ) ).thenReturn( dr0 );
//        HashSet<Square> dr1 = new HashSet<>(dr0);     dr1.add( tested ); dr1.add( s1 );                     Mockito.when( onRight.atDistance( 1 ) ).thenReturn( dr1 );
//        HashSet<Square> dr2 = new HashSet<>(dr1);     dr2.addAll( dr1 ); dr2.add( s2 ); dr2.add( s3 );      Mockito.when( onRight.atDistance( 2 ) ).thenReturn( dr2 );
//        HashSet<Square> d20 = new HashSet<>();        d20.add( s2 );                                        Mockito.when( s2.atDistance( 0 ) ).thenReturn( d20 );
//        HashSet<Square> d21 = new HashSet<>(d20);     d21.add( tested ); d21.add( s1 );                     Mockito.when( s2.atDistance( 1 ) ).thenReturn( d21 );
//        HashSet<Square> d22 = new HashSet<>(d21);     d22.addAll( d21 ); d22.add( s3 ); d22.add( onRight ); Mockito.when( s2.atDistance( 2 ) ).thenReturn( d22 );
//        HashSet<Square> d30 = new HashSet<>();        d30.add( s3 );                                        Mockito.when( s3.atDistance( 0 ) ).thenReturn( d30 );
//        HashSet<Square> d31 = new HashSet<>(d30);     d31.add( s1 );                                        Mockito.when( s3.atDistance( 1 ) ).thenReturn( d31 );
//        HashSet<Square> d32 = new HashSet<>(d31);     d32.addAll( d31 ); d32.add( s2 ); d32.add( onRight ); Mockito.when( s3.atDistance( 2 ) ).thenReturn( d32 );
//        HashSet<Square> d33 = new HashSet<>(d32);     d33.addAll( d32 ); d33.add( tested );                 Mockito.when( s3.atDistance( 3 ) ).thenReturn( d33 );
//    }



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

    //TODO test di allSquaresInDirection e allSquares

}

