package model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DeckTest {
    @Mock private Game g;
    private Deck<Integer> tested;
    private static final ArrayList<Integer> zeroTo99 = new ArrayList<>();
    
    @BeforeClass
    public static void setUpClass(){
        for (int i=0; i<100; i++){
            zeroTo99.add( i );
        }
    }
    
    @Before
    public void setup(){
        tested = new Deck<>();
        for(Integer i: zeroTo99){
            tested.add( i );
        }
    }
    
    @Test
    public void containsAddedObjects() {
        for(Integer i: zeroTo99){
            assertTrue( tested.contains( i ) );
        }
        assertEquals(tested.size(), zeroTo99.size());
    }

    @Test
    public void drawnCardsAreRemovedFromDeck() {
        ArrayList<Integer> remaining = ( ArrayList<Integer> ) zeroTo99.clone();
        Integer drew = tested.draw();
        remaining.remove(drew);
        assertFalse(tested.contains(drew));
        for (Integer i : remaining) {
            assertTrue(tested.contains(i));
        }
    }

    @Test
    public void equalsisOrderSensitive() {
        Deck<Integer> d2 = new Deck<>();
        for(Integer i: zeroTo99){
            d2.add( i );
        }
        assertNotEquals(tested, d2);
        assertNotEquals(d2, tested);
    }
    
    @Test
    public void equalsWorksWithSameCardsInSameOrder() throws CloneNotSupportedException {
        Deck<Integer> d2 = ( Deck<Integer> ) tested.clone();
        assertEquals( tested,d2 );
    }
    
    @Test
    public void equalsReturnsFalseOnOtherClasses(){
        assertNotEquals( tested, "ciao"  );
    }
    
    @Test
    public void shuffleWorksFine() throws CloneNotSupportedException {
        Deck<Integer> clone = ( Deck<Integer> ) tested.clone();
        clone.shuffle();
        assertNotEquals(tested,clone);
    }

    @Test
    public void sameHashcodeWithSameCards() {
        Deck<String> d1 = new Deck<>();
        Deck<String> d2 = new Deck<>();
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
