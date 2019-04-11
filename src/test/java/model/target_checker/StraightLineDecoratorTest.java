package model.target_checker;

import model.Character;
import model.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StraightLineDecoratorTest {

    @Mock
    Tile startingTile;
    @Mock
    Tile t1,t2;
    @Mock
    Character c1,c2;
    ArrayList<Character> list=new ArrayList<>();
    @Mock
    StandardChecker base;
    TargetCondition tested;

    @Before
    public void setUp(){
        //setup startingTile
        Mockito.when(startingTile.getX()).thenReturn(2);
        Mockito.when(startingTile.getY()).thenReturn(2);
        //setup Characters
        Mockito.when(c1.getCurrentTile()).thenReturn(t1);
        Mockito.when(c2.getCurrentTile()).thenReturn(t2);
        //setup t1
        Mockito.when(t1.getX()).thenReturn(2);
        Mockito.when(t1.getY()).thenReturn(0);
        //setup t2
        Mockito.when(t2.getX()).thenReturn(2);
        Mockito.when(t2.getY()).thenReturn(3);
        //setup list
        list.clear();
        list.add(c1);
        list.add(c2);
        //base validator always assert valid
        Mockito.when(base.isValid(list,startingTile)).thenReturn(true);
        //init tested
        tested=new StraightLineDecorator(base);
    }

    @Test
    public void onSameXTest(){
        assertTrue(tested.isValid(list,startingTile));
    }

    @Test
    public void onSameYTest(){
        //setup t1
        Mockito.when(t1.getX()).thenReturn(0);
        Mockito.when(t1.getY()).thenReturn(2);
        //setup t2
        Mockito.when(t2.getX()).thenReturn(1);
        Mockito.when(t2.getY()).thenReturn(2);
        assertTrue(tested.isValid(list,startingTile));
    }

    @Test
    public void standardNotWorkingTest(){
        Mockito.when(t1.getX()).thenReturn(1);
        assertFalse(tested.isValid(list,startingTile));
    }

    @Test
    public void onDifferentLinesFails(){
        Mockito.when(t1.getX()).thenReturn(0);
        Mockito.when(t2.getY()).thenReturn(2);
        assertFalse(tested.isValid(list,startingTile));
    }

    @Test
    public void onStartingTileIsValid(){
        Mockito.when(c2.getCurrentTile()).thenReturn(startingTile);
        assertTrue(tested.isValid(list,startingTile));
    }
}
