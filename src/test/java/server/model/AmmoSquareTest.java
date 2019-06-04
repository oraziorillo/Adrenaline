package server.model;

import server.enums.SquareColourEnum;
import server.exceptions.EmptySquareException;
import server.model.squares.AmmoSquare;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@RunWith(MockitoJUnitRunner.class)
public class AmmoSquareTest {

    private final int x = 3;
    private final int y = 5;
    private final SquareColourEnum colour = SquareColourEnum.BLUE;
    private AmmoSquare tested;
    @Mock
    private AmmoTile card1, card2;
    @Mock
    private Deck<AmmoTile> deck;
    @Mock
    private Pc pc;

    @Before
    public void setup() {
        Mockito.when(deck.draw()).thenReturn(card1).thenReturn(card2);
        tested = new AmmoSquare( x,y,SquareColourEnum.BLUE);
    }

    @Test
    public void constructedFine() {
        assertEquals("x is different", tested.getRow(), x);
        assertEquals("y is different", tested.getCol(), y);
        assertEquals( "Colour has changed", tested.getColour(), colour );
    }

    @Test
    public void drawsOnSpawnTheFirstCardOfDeck() {
        assertSame(tested.getAmmoTile(), card1);
    }

    @Test
    public void isEmptyWorksAndDoesntAutoRefill() throws EmptySquareException {
       assertFalse( tested.isEmpty() );
       tested.collect(pc);
       assertTrue( tested.isEmpty() );
    }
    
    @Test
   public void refillWorksFine() throws EmptySquareException {
       tested.collect(pc);
       assumeTrue(tested.isEmpty());
       tested.refill();
       assertFalse( tested.isEmpty() );
       assertSame( tested.getAmmoTile(), card2 );
    }
}

