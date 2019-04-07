package model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AmmoTileTest {

    @Test
    public void xySettedFine(){
        int x=3;
        int y=5;
        Deck<AmmoCard> deck= Mockito.mock(Deck.class);
        AmmoCard card=new AmmoCard(new short[]{2,0,0},true);
        Mockito.when(deck.draw()).thenReturn(card);
        AmmoTile tested=new AmmoTile(3,5,deck);
        assertEquals("x is different",tested.getX(),3);
        assertEquals("y is different",tested.getY(),5);
    }

    @Test
    public void drawsOnGenerationTheFirstCardOfDeck(){
        Deck<AmmoCard> deck=Mockito.mock(Deck.class);
        AmmoCard card=new AmmoCard(new short[]{2,0,0},true);
        AmmoCard card2=new AmmoCard(new short[]{0,2,1},false);
        Mockito.when(deck.draw()).thenReturn(card2).thenReturn(card);
        AmmoTile tile=new AmmoTile(1,2,deck);
        assertSame(tile.drawCard(),card2);

    }


}
