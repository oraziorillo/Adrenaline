package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class AmmoSquareTest {

    final int x = 3;
    final int y = 5;
    final TileColourEnum colour = TileColourEnum.BLUE;
    @Mock
    Deck<AmmoTile> deck;
    AmmoTile card1, card2;
    AmmoSquare tested;


    @Before
    public void setup() {
        card1 = new AmmoTile(new short[]{2, 0, 0}, true);
        card2 = new AmmoTile(new short[]{0, 2, 1}, false);
        Mockito.when(deck.draw()).thenReturn(card1).thenReturn(card2);
        tested = new AmmoSquare(x, y, colour, deck);

    }

    @Test
    public void xySettedFine() {
        Deck<AmmoTile> deck = Mockito.mock(Deck.class);
        AmmoTile card = new AmmoTile(new short[]{2, 0, 0}, true);
        Mockito.when(deck.draw()).thenReturn(card);
        AmmoSquare tested = new AmmoSquare(3, 5, TileColourEnum.BLUE, deck);
        assertEquals("x is different", tested.getX(), 3);
        assertEquals("y is different", tested.getY(), 5);
    }

    @Test
    public void drawsOnSpawnTheFirstCardOfDeck() {
        Deck<AmmoTile> deck = Mockito.mock(Deck.class);
        AmmoTile card = new AmmoTile(new short[]{2, 0, 0}, true);
        AmmoTile card2 = new AmmoTile(new short[]{0, 2, 1}, false);
        Mockito.when(deck.draw()).thenReturn(card2).thenReturn(card);
        AmmoSquare tile = new AmmoSquare(1, 2, TileColourEnum.GREEN, deck);
        assertSame(tile.pickAmmo(), card2);
    }

    @Test
    public void roomColourSettedFine() {
        assertEquals(tested.getTileColour(), colour);
    }

}
