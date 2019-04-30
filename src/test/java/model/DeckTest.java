package model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class DeckTest {
    @Mock Game g;

    @Test
    public void containsAddedObjects() {
        Deck<Integer> tested = new Deck<>(g);
        tested.add(1);
        tested.add(2);
        tested.add(3);
        assertTrue(tested.contains(1));
        assertTrue(tested.contains(2));
        assertTrue(tested.contains(3));
        assertEquals(tested.size(), 3);
    }

    @Test
    public void doesNotCantainDrawnCard() {
        Deck<Integer> tested = new Deck<>(g);
        ArrayList<Integer> support = new ArrayList<>();
        tested.add(1);
        tested.add(2);
        tested.add(3);
        support.add(1);
        support.add(2);
        support.add(3);
        Integer drew = tested.draw();
        support.remove(drew);
        assertFalse(tested.contains(drew));
        for (Integer i : support) {
            assertTrue(tested.contains(i));
        }


    }

    @Test
    public void equalsIfSameCardsInside() {
        Deck<String> d1 = new Deck<>(g);
        Deck<String> d2 = new Deck<>(g);
        ArrayList<String> al = new ArrayList<>();
        d1.add("ciao");
        d2.add("ciao");
        al.add("ciao");
        d1.add("seconda");
        d2.add("seconda");
        al.add("seconda");
        assertEquals(d1, d2);
        assertEquals(d2, d1);
        assertNotEquals(d1, al);
        assertNotEquals(d2, al);
        d2.draw();
        assertNotEquals(d1, d2);
        assertNotEquals(d2, d1);
    }

    @Test
    public void sameHashcodeWithSameCards() {
        Deck<String> d1 = new Deck<>(g);
        Deck<String> d2 = new Deck<>(g);
        ArrayList<String> al = new ArrayList<>();
        d1.add("ciao");
        d2.add("ciao");
        al.add("ciao");
        d1.add("seconda");
        d2.add("seconda");
        al.add("seconda");
        assertEquals(d1.hashCode(), d2.hashCode());
        assertNotEquals(d1.hashCode(), al.hashCode());
        assertNotEquals(d2.hashCode(), al.hashCode());
        d2.draw();
        assertNotEquals(d1.hashCode(), d2.hashCode());
    }
}
