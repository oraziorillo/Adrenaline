package model;

import model.target_checker.StandardChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardCheckerTest {

    @Mock
    Character c1;
    @Mock
    Character c2;
    @Mock
    Deck<AmmoCard> aDeck;
    @Mock
    Deck<Weapon> wDeck;
    Tile t1;
    Tile t2;

    @Before
    public void setup() {
        Mockito.when(aDeck.draw()).thenReturn(new AmmoCard(new short[]{1, 2, 0}, false));
        t1 = new AmmoTile(0, 1, RoomColourEnum.BLUE, aDeck);
        t2 = new GenerationTile(2, 3, RoomColourEnum.RED, wDeck);
        Mockito.when(c1.getCurrentTile()).thenReturn(t1);//c1 è su t1
        Mockito.when(c2.getCurrentTile()).thenReturn(t2);//c2 è su t2
        //t1 vede t2
        t1.addVisible(t2);


    }

    @Test
    public void needVisibilityWorksFine() {
        StandardChecker vis = new StandardChecker(3, 5, true);
        StandardChecker nonVis = new StandardChecker(3, 5, false);
        ArrayList<Character> list1 = new ArrayList<>();
        ArrayList<Character> list2 = new ArrayList<>();
        list1.add(c1);
        list2.add(c2);
        assertTrue("con vis puoi sparare da t1 a t2", vis.isValid(list2, t1));
        assertFalse("con vis non puoi sparare da t2 a t1", vis.isValid(list2, t2));
        assertTrue("con nonVis t1 spara a t2", nonVis.isValid(list2, t1));
        assertTrue("con nonVis t2 spara a t1", nonVis.isValid(list1, t2));

    }

    @Test
    public void minDistFailsTest() {
        StandardChecker testes = new StandardChecker(3, 5, true);
        ArrayList<Character> list2 = new ArrayList<>();
        list2.add(c2);
        t2 = new GenerationTile(0, 1, RoomColourEnum.RED, wDeck);
        assertFalse("Più vicini di mindist", testes.isValid(list2, t1));
    }

    @Test
    public void maxDistFailsTest() {
        StandardChecker testes = new StandardChecker(0, 0, true);
        ArrayList<Character> list2 = new ArrayList<>();
        list2.add(c2);
        t2 = new GenerationTile(1, 1, RoomColourEnum.RED, wDeck);
        assertFalse("Più lontani di maxDist", testes.isValid(list2, t1));
    }

    @Test
    public void exceptionOnMinDistBiggerThanMaxDist() {
        assertThrows("not valid parameters", IllegalArgumentException.class, () -> new StandardChecker(1, 0, true));
    }

}
