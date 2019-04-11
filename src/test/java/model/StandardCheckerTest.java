package model;

import model.target_checker.ConcreteTargetChecker;
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
    Pc pc1;
    @Mock
    Pc pc2;
    @Mock
    Deck<AmmoCard> aDeck;
    @Mock
    Deck<WeaponCard> wDeck;
    Tile t1;
    Tile t2;

    @Before
    public void setup() {
        Mockito.when(aDeck.draw()).thenReturn(new AmmoCard(new short[]{1, 2, 0}, false));
        t1 = new AmmoTile(0, 1, RoomColourEnum.BLUE, aDeck);
        t2 = new GenerationTile(2, 3, RoomColourEnum.RED, wDeck);
        Mockito.when(pc1.getCurrentTile()).thenReturn(t1);//pc1 è su t1
        Mockito.when(pc2.getCurrentTile()).thenReturn(t2);//pc2 è su t2
        //t1 vede t2
        t1.addVisible(t2);


    }

    @Test
    public void needVisibilityWorksFine() {
        ConcreteTargetChecker vis = new ConcreteTargetChecker(3, 5, true);
        ConcreteTargetChecker nonVis = new ConcreteTargetChecker(3, 5, false);
        ArrayList<Pc> list1 = new ArrayList<>();
        ArrayList<Pc> list2 = new ArrayList<>();
        list1.add(pc1);
        list2.add(pc2);
        assertTrue("con vis puoi sparare da t1 a t2", vis.isValid(list2, t1));
        assertFalse("con vis non puoi sparare da t2 a t1", vis.isValid(list2, t2));
        assertTrue("con nonVis t1 spara a t2", nonVis.isValid(list2, t1));
        assertTrue("con nonVis t2 spara a t1", nonVis.isValid(list1, t2));

    }

    @Test
    public void minDistFailsTest() {
        ConcreteTargetChecker testes = new ConcreteTargetChecker(3, 5, true);
        ArrayList<Pc> list2 = new ArrayList<>();
        list2.add(pc2);
        t2 = new GenerationTile(0, 1, RoomColourEnum.RED, wDeck);
        assertFalse("Più vicini di mindist", testes.isValid(list2, t1));
    }

    @Test
    public void maxDistFailsTest() {
        ConcreteTargetChecker testes = new ConcreteTargetChecker(0, 0, true);
        ArrayList<Pc> list2 = new ArrayList<>();
        list2.add(pc2);
        t2 = new GenerationTile(1, 1, RoomColourEnum.RED, wDeck);
        assertFalse("Più lontani di maxDist", testes.isValid(list2, t1));
    }

    @Test
    public void exceptionOnMinDistBiggerThanMaxDist() {
        assertThrows("not valid parameters", IllegalArgumentException.class, () -> new ConcreteTargetChecker(1, 0, true));
    }

}
