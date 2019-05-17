package model;


import enums.SquareColourEnum;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
<<<<<<< HEAD:src/test/java/model/AmmoTileTest.java
import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;


@RunWith(Theories.class)
public class AmmoTileTest {
    @DataPoints
    public static final boolean[] hasPowerup = {true, false};
    @DataPoints
    public static final short[][] ammos = {{1, 1, 1}, {3, 0, 0}, {2, 0, 0}, {0, -1, 4}, {4, 5, 6}};

    @Theory
    public void doesNotAlterCostructionParameters(short[] ammos, boolean hasPowerup) {
        assumeTrue("Invalid parameters", AmmoTile.validParameters(ammos, hasPowerup));
        AmmoTile tested = new AmmoTile(ammos, hasPowerup);
        assertEquals("Different ammos", ammos, tested.getAmmos());
        assertEquals("Different powerup", hasPowerup, tested.containsPowerup());
    }

    @Theory
    public void throwsExceptionOnInvalidParameters(short[] ammos, boolean hasPowerup) {
        assumeFalse("Valid parameters", AmmoTile.validParameters(ammos, hasPowerup));
        assertThrows("Exception not thrown", IllegalArgumentException.class,()->new AmmoTile(ammos, hasPowerup));
=======
import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class AmmoSquareTest {

    final int x = 3;
    final int y = 5;
    final SquareColourEnum colour = SquareColourEnum.BLUE;
    @Mock
    Deck<AmmoCard> deck;
    AmmoCard card1, card2;
    AmmoSquare tested;


    @Before
    public void setup() {
        card1 = new AmmoCard(new short[]{2, 0, 0}, true);
        card2 = new AmmoCard(new short[]{0, 2, 1}, false);
        Mockito.when(deck.draw()).thenReturn(card1).thenReturn(card2);
        tested = new AmmoSquare(x, y, colour, deck);

    }

    @Test
    public void xySettedFine() {
        Deck<AmmoCard> deck = Mockito.mock(Deck.class);
        AmmoCard card = new AmmoCard(new short[]{2, 0, 0}, true);
        Mockito.when(deck.draw()).thenReturn(card);
        AmmoSquare tested = new AmmoSquare(3, 5, SquareColourEnum.BLUE, deck);
        assertEquals("x is different", tested.getX(), 3);
        assertEquals("y is different", tested.getY(), 5);
    }

    @Test
    public void drawsOnSpawnTheFirstCardOfDeck() {
        Deck<AmmoCard> deck = Mockito.mock(Deck.class);
        AmmoCard card = new AmmoCard(new short[]{2, 0, 0}, true);
        AmmoCard card2 = new AmmoCard(new short[]{0, 2, 1}, false);
        Mockito.when(deck.draw()).thenReturn(card2).thenReturn(card);
        AmmoSquare tile = new AmmoSquare(1, 2, SquareColourEnum.GREEN, deck);
        assertSame(tile.pickAmmo(), card2);
>>>>>>> c6a63179590d6cf4134766d61136ab6cb031c3df:server/src/test/java/model/AmmoSquareTest.java
    }

    @Theory
    public void parameterValidatorWorksFine(short[] ammos, boolean hasPowerup) {
        boolean correct = true;
        short t = 0;
        if (hasPowerup) {
            t++;
        }
        for (short s : ammos) {
            if (s < 0) {
                correct = false;
            }
            t += s;
        }
        if (t != Constants.AMMOS_PER_CARD) {
            correct = false;
        }
        assertEquals(AmmoTile.validParameters(ammos, hasPowerup), correct);
    }

}
