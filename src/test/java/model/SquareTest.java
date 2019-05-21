package model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {

    @Test
    public void addVisiblesWorksFine() {
        Square t1 = new ConcreteSquare(1, 2);
        Square t2 = new ConcreteSquare(0, 0);
        t1.addVisible(t2);
        assertTrue(t1.getVisibles().contains(t2));
    }

    @Test
    public void addAndRemoveCharacterWorksFine() {
        Pc c1 = Mockito.mock(Pc.class);
        Pc c2 = Mockito.mock(Pc.class);
        Square tester = new ConcreteSquare(0, 0);
        tester.addPc(c1);
        tester.addPc(c2);
        assertTrue(tester.getPcs().contains(c1));
        assertTrue(tester.getPcs().contains(c2));
        tester.removePc(c1);
        assertFalse(tester.getPcs().contains(c1));
        assertTrue(tester.getPcs().contains(c2));
    }

    class ConcreteSquare extends Square {

        ConcreteSquare(int x, int y) {
            super(x, y, SquareColourEnum.GREEN);
        }

        @Override
        public void refill() {}

        @Override
        public void collect(Pc player, int objectIndex) {}
    }


}
