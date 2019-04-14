package model;

import model.Enumerations.PcColourEnum;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Theories.class)
public class KillshotTest {
    @DataPoints
    public static boolean[] aBoolean = {true, false};
    @DataPoints
    public static PcColourEnum[] charColours = PcColourEnum.values();

    @Theory
    public void beginsSkulled() {
        assertTrue(new Killshot().isSkulled());
    }

    @Theory
    public void killMethodWorksFine(PcColourEnum charColours, boolean aBoolean) {
        Killshot tested = new Killshot();
        tested.kill(charColours, aBoolean);
        assertEquals(tested.isOverkilled(), aBoolean);
        assertEquals(tested.getColour(), charColours);
        assertFalse(tested.isSkulled());
    }

    @Theory
    public void throwsExceptionIfKilled2Times(PcColourEnum charColours, boolean aBoolean) {
        Killshot tested = new Killshot();
        tested.kill(charColours, aBoolean);
        assertThrows("Exception not thrown", IllegalStateException.class, () -> {tested.kill(charColours, aBoolean);});
    }
}
