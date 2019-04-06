package model;


import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import static org.junit.Assume.*;
import static org.junit.Assert.*;



@RunWith(Theories.class)
public class AmmoCardTest {
    @DataPoints
    public static final boolean[] hasPowerup={true,false};
    @DataPoints
    public static final short[][] ammos={{1,1,1},{3,0,0},{2,0,0},{0,-1,4},{4,5,6}};
    @Theory
    public void constructorAndGettersTest(short[] ammos, boolean hasPowerup){
        assumeTrue("Invalid parameters",AmmoCard.validParameters(ammos, hasPowerup));
        AmmoCard tested=new AmmoCard(ammos,hasPowerup);
        assertEquals("Different ammos",ammos,tested.getAmmos());
        assertEquals("Different powerup",hasPowerup,tested.containsPowerup());
    }
    @Theory
    public void exceptionTest(short[] ammos, boolean hasPowerup){
        assumeFalse("Valid parameters",AmmoCard.validParameters(ammos, hasPowerup));
        assertThrows("Exception not thrown",IllegalArgumentException.class,()->{new AmmoCard(ammos,hasPowerup);});
    }
    @Theory
    public void paramValidatorTest(short[] ammos,boolean hasPowerup){
        boolean correct=true;
        short t=0;
        if(hasPowerup){
            t++;
        }
        for(short s:ammos){
            if(s<0){
                correct=false;
            }
            t+=s;
        }
        if(t!=AmmoCard.AMMOSFORCARD){
            correct=false;
        }
        assertEquals(AmmoCard.validParameters(ammos, hasPowerup),correct);
    }

}
