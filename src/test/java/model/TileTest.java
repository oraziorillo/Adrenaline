package model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TileTest {

    @Test
    public void ordinaryDistanceTest(){
        int x1=0;
        int y1=3;
        int x2=7;
        int y2=6;
        int expected=10;
        Tile t1=new ConcreteTile(x1,y1);
        Tile t2=new ConcreteTile(x2,y2);
        assertEquals("Unexpected result",expected,Tile.distance(t1,t2));
    }

    @Test
    public void distanceZeroTest(){
        int x1=0;
        int y1=3;
        int x2=0;
        int y2=3;
        int expected=0;
        Tile t1=Mockito.mock(Tile.class);
        Tile t2=Mockito.mock(Tile.class);
        Mockito.when(t1.getX()).thenReturn(x1);
        Mockito.when(t1.getY()).thenReturn(y1);
        Mockito.when(t2.getX()).thenReturn(x2);
        Mockito.when(t2.getY()).thenReturn(y2);
        assertEquals("Unexpected result",expected,Tile.distance(t1,t2));
    }


}

class ConcreteTile extends Tile{

    ConcreteTile(int x, int y) {
        super(x, y);
    }
}