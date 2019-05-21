package model;

import enums.CardinalDirectionEnum;
import enums.SquareColourEnum;
import model.squares.AmmoSquare;
import model.squares.Square;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

public class SquareTest {
    
    private Square tested;
    private Square onRight, s1,s2,s3;
    private final int x = 9;
    private final int y = 7;
    private final SquareColourEnum colour = SquareColourEnum.BLUE;
    
    @Before
    public void setupAndContructorTest(){
        //attributes setup and mocking
        Deck<AmmoTile> uselessDeck = Mockito.mock( Deck.class );
        AmmoTile uselessTile = Mockito.mock( AmmoTile.class );
        Mockito.when( uselessDeck.draw() ).thenReturn( uselessTile );
        setupSquareMocks();
        tested = new AmmoSquare( x,y,colour,uselessDeck );
        //test constructor
        assertTrue( tested.getVisibles().isEmpty() );assertEquals(tested.getX(),x);
        assertEquals( tested.getY(),y );
        assertEquals( tested.getColour(), colour );
        assertFalse( tested.isTargetable() );
        assertTrue( tested.getPcs().isEmpty() );
        tested.addVisible( onRight );
        tested.addVisible( s1 );
        tested.addVisible( s2 );
        tested.addVisible( s3 );
    }
    
    /**
     * assicurarsi che non ci sia nessuna tile a sinistra di tested
     */
    private void setupSquareMocks(){
        onRight = Mockito.mock( Square.class );
        s1 =  Mockito.mock( Square.class );
        s2 = Mockito.mock( Square.class );
        s3 = Mockito.mock( Square.class );
        /*Mantenere la minimappa dei tile aggiornata
        **|niente|s3     |
        **|s2    |s1     |
        **|tested|onright|
         */
        Mockito.when( onRight.getX() ).thenReturn( x+1 );
        Mockito.when( onRight.getY() ).thenReturn( y );
        Mockito.when( s1.getX() ).thenReturn( x+1 );
        Mockito.when( s1.getY() ).thenReturn( y+1 );
        Mockito.when( s2.getX() ).thenReturn( x );
        Mockito.when( s2.getY() ).thenReturn( y+1 );
        Mockito.when( s3.getX() ).thenReturn( x+1 );
        Mockito.when( s3.getY() ).thenReturn( y+2 );
        HashSet<Square> onRightVis = new HashSet<>(); onRightVis.add( tested ); onRightVis.add( s1 );
        HashSet<Square> s1Vis = new HashSet<>(); s1Vis.add( tested ); s1Vis.add( s2 ); s1Vis.add( s3 );
        HashSet<Square> s2Vis = new HashSet<>(); s2Vis.add( tested ); s2Vis.add( s1 );
        HashSet<Square> s3Vis = new HashSet<>(); s3Vis.add( s1 );
        Mockito.when( onRight.getVisibles() ).thenReturn( onRightVis );
        Mockito.when( s1.getVisibles() ).thenReturn( s1Vis );
        Mockito.when( s2.getVisibles() ).thenReturn( s2Vis );
        Mockito.when( s3.getVisibles() ).thenReturn( s3Vis );
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
    public void onCardinalDirectionReturnsOptionalContainingTheTileOnTheRightWhenOneTileReachableInThatDirection(){
        assertSame(onRight, tested.onDirection( CardinalDirectionEnum.EAST ).get() );
    }
    
    @Test
    public void onCardinalDirectionReturnsEmptyOptionalWhenNoTileInThatDirection(){
        assertTrue( tested.onDirection( CardinalDirectionEnum.WEST ).isEmpty());
    }
    
    @Test
    public void atDistanceReturnedCollectionContainsAllTheInterestedTilesAndNoMore(){
        //TODO: Alessio, non riesco a testare il funzionamento di questo metodo. Puoi controllarlo?
        int distance = 2;
        Collection<Square> ok = new HashSet<>();
        Collection<Square> nope = new HashSet<>();
        ok.add( tested );
        ok.add( onRight );
        ok.add( s1 );
        ok.add( s2 );
        nope.add( s3 );
       System.out.println(tested.atDistance( distance ));
        assertTrue( tested.atDistance( distance ).containsAll( ok ) );
        assertFalse( tested.atDistance( distance ).stream().anyMatch( nope::contains ) );
    }
    
    @Test
    public void atDistanceThrowsExceptionWithNegativeDistances(){
        assertThrows( IllegalArgumentException.class,()->tested.atDistance( -1 ) );
    }
   
    @Test
    public void atDistanceReturnsCallingTileOnlyWithDistance0(){
        Collection<Square> result = tested.atDistance( 0 );
        assertEquals( 1, result.size() );
        assertTrue( result.contains( tested ) );
    }
}

