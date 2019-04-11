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
public class AmmoTileTest {

    final int x=3;
    final int y=5;
    final RoomColourEnum colour=RoomColourEnum.BLUE;
    @Mock Deck<AmmoCard> deck;
    AmmoCard card1,card2;
    AmmoTile tested;


    @Before
    public void setup(){
        card1 = new AmmoCard(new short[]{2, 0, 0}, true);
        card2 = new AmmoCard(new short[]{0, 2, 1}, false);
        Mockito.when(deck.draw()).thenReturn(card1).thenReturn(card2);
        tested = new AmmoTile(x, y, colour, deck);

    }

    @Test
    public void xySettedFine() {
<<<<<<< HEAD
        int x = 3;
        int y = 5;
        Deck<AmmoCard> deck = Mockito.mock(Deck.class);
        AmmoCard card = new AmmoCard(new short[]{2, 0, 0}, true);
        Mockito.when(deck.draw()).thenReturn(card);
        AmmoTile tested = new AmmoTile(3, 5, TileColourEnum.BLUE, deck);
        assertEquals("x is different", tested.getX(), 3);
        assertEquals("y is different", tested.getY(), 5);
=======
        assertEquals("x is different", tested.getX(), x);
        assertEquals("y is different", tested.getY(), y);
>>>>>>> f50f154c96dd1898424e0578900e99c4cc4fc400
    }

    @Test
    public void drawsOnGenerationTheFirstCardOfDeck() {
<<<<<<< HEAD
        Deck<AmmoCard> deck = Mockito.mock(Deck.class);
        AmmoCard card = new AmmoCard(new short[]{2, 0, 0}, true);
        AmmoCard card2 = new AmmoCard(new short[]{0, 2, 1}, false);
        Mockito.when(deck.draw()).thenReturn(card2).thenReturn(card);
        AmmoTile tile = new AmmoTile(1, 2, TileColourEnum.GREEN, deck);
        assertSame(tile.drawCard(), card2);

=======
        assertSame(deck.draw(), card2);
        assertSame(tested.drawCard(), card1);
>>>>>>> f50f154c96dd1898424e0578900e99c4cc4fc400
    }

    @Test
    public void roomColourSettedFine(){
        assertEquals(tested.getRoomColour(),colour);
    }

}
