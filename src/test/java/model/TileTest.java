package model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TileTest {

    class ConcreteTile extends Tile {

        ConcreteTile(int x, int y) {
            super(x, y, RoomColourEnum.GREEN);
        }
    }

    @Test
    public void ordinaryDistanceTest() {
        int x1 = 0;
        int y1 = 3;
        int x2 = 7;
        int y2 = 6;
        int expected = 10;
        Tile t1 = new ConcreteTile(x1, y1);
        Tile t2 = new ConcreteTile(x2, y2);
        assertEquals("Unexpected result", expected, Tile.distance(t1, t2));
    }

    @Test
    public void distanceZeroTest() {
        int x1 = 0;
        int y1 = 3;
        int x2 = 0;
        int y2 = 3;
        int expected = 0;
        Tile t1 = new ConcreteTile(x1, y1);
        Tile t2 = new ConcreteTile(x2, y2);
        assertEquals("Unexpected result", expected, Tile.distance(t1, t2));
    }

    @Test
    public void addVisiblesWorksFine() {
        Tile t1 = new ConcreteTile(1, 2);
        Tile t2 = new ConcreteTile(0, 0);
        t1.addVisible(t2);
        assertTrue(t1.getVisibles().contains(t2));
    }

    @Test
    public void addAndRemoveCharacterWorksFine() {
        Pc c1 = Mockito.mock(Pc.class);
        Pc c2 = Mockito.mock(Pc.class);
        Tile tester = new ConcreteTile(0, 0);
        tester.addCharacter(c1);
        tester.addCharacter(c2);
        assertTrue(tester.getPcs().contains(c1));
        assertTrue(tester.getPcs().contains(c2));
        tester.removeCharacter(c1);
        assertFalse(tester.getPcs().contains(c1));
        assertTrue(tester.getPcs().contains(c2));
    }


}

