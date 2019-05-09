package model;

import model.enumerations.TileColourEnum;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TileTest {

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
        tester.addPc(c1);
        tester.addPc(c2);
        assertTrue(tester.getPcs().contains(c1));
        assertTrue(tester.getPcs().contains(c2));
        tester.removePc(c1);
        assertFalse(tester.getPcs().contains(c1));
        assertTrue(tester.getPcs().contains(c2));
    }

    class ConcreteTile extends Tile {

        ConcreteTile(int x, int y) {
            super(x, y, TileColourEnum.GREEN);
        }

        @Override
        public void refill() {}

        @Override
        public void collect(Pc player, int objectIndex) {}
    }


}

