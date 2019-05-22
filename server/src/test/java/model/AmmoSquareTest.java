package model;


import enums.SquareColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assumptions.*;

@RunWith(MockitoJUnitRunner.class)
public class AmmoSquareTest {

    private final int x = 3;
    private final int y = 5;
    private final SquareColourEnum colour = SquareColourEnum.BLUE;
    @Mock
    private Deck<AmmoTile> deck;
    @Mock
    private AmmoTile card1, card2;
    private AmmoSquare tested;

    @Before
    public void setup() {
        Mockito.when(deck.draw()).thenReturn(card1).thenReturn(card2);
        tested = new AmmoSquare( x,y,SquareColourEnum.BLUE,deck );
    }

    @Test
    public void constructedFine() {
        assertEquals("x is different", tested.getX(), x);
        assertEquals("y is different", tested.getY(), y);
        assertEquals( "Colour has changed", tested.getSquareColour(), colour );
    }

    @Test
    public void drawsOnSpawnTheFirstCardOfDeck() {
        assertSame(tested.pickAmmo(), card1);
    }
    
    @Test
    public void isEmptyWorksAndDoesntAutoRefill(){
       assertFalse( tested.isEmpty() );
       tested.pickAmmo();
       assertTrue( tested.isEmpty() );
    }
    
    @Test
   public void refillWorksFine(){
       tested.pickAmmo();
       assumeTrue(tested.isEmpty());
       tested.refill();
       assertFalse( tested.isEmpty() );
       assertSame( tested.pickAmmo(), card2 );
    }

}
