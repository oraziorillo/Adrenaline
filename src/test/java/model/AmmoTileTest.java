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
        assertEquals("x is different", tested.getX(), x);
        assertEquals("y is different", tested.getY(), y);
    }

    @Test
    public void drawsOnGenerationTheFirstCardOfDeck() {
        assertSame(deck.draw(), card2);
        assertSame(tested.drawCard(), card1);
    }

    @Test
    public void roomColourSettedFine(){
        assertEquals(tested.getRoomColour(),colour);
    }

}
