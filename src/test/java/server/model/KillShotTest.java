package server.model;

import common.enums.PcColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class KillShotTest {
    private KillShot tested;
    private PcColourEnum charColour = PcColourEnum.BLUE;
    
    @Before
    public void setup(){
        tested = new KillShot();
    }

    @Test
    public void beginsSkulledNotOverkilledAndWithNullColour() {
        assertTrue(tested.isSkulled());
        assertFalse( tested.isOverkilled() );
        assertNull( tested.getColour() );
    }

    @Test
    public void killMethodWorksFineWhenNotOverkilled() {
        tested.killOccured( charColour, false );
        assertFalse( tested.isOverkilled() );
        assertSame( tested.getColour(), charColour );
        assertFalse( tested.isSkulled() );
    }
    
    @Test
    public void killMethodWorksFineWhenOverkilled() {
        tested.killOccured( charColour, false );
        assertFalse( tested.isOverkilled() );
        assertSame( tested.getColour(), charColour );
        assertFalse( tested.isSkulled() );
    }

    @Test
    public void throwsExceptionIfKilled2Times() {
        KillShot tested = new KillShot();
        tested.killOccured(charColour, true);
        assertThrows( IllegalStateException.class, () -> {
            tested.killOccured(charColour, true);
        });
    }
}
