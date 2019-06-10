package server.model;

import common.enums.SquareColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import server.exceptions.EmptySquareException;
import server.model.squares.AmmoSquare;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@RunWith(MockitoJUnitRunner.class)
public class AmmoSquareTest {

    private final int row = 3;
    private final int coloumn = 5;
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
        tested = new AmmoSquare(row, coloumn,SquareColourEnum.BLUE);
        tested.assignDeck(null, deck);
    }

    @Test
    public void constructedFine() {
        assertEquals("row is different", tested.getRow(), row);
        assertEquals("coloumn is different", tested.getCol(), coloumn);
        assertEquals( "Colour has changed", tested.getColour(), colour );
        assertFalse(tested.isSpawnPoint());
    }

    @Test
    public void drawsOnSpawnTheFirstCardOfDeck() {
        assertSame(tested.getAmmoTile(), card1);
    }

    @Test
    public void collectLaunchExceptionWhenAmmoSquareIsEmpty () throws EmptySquareException {
        tested.collect(pc);
        assertThrows(EmptySquareException.class, () -> tested.collect(pc));
    }

    @Test
    public void collectWorksFine() throws EmptySquareException {
        tested.collect(pc);
        assertTrue(tested.isEmpty());
        assertNull(tested.getAmmoTile());
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

