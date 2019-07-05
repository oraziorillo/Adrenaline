package server.model;

import common.dto_model.KillShotTrackDTO;
import common.enums.PcColourEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KillShotTrackTest {

    private static final int SIZE = 7;
    private KillShotTrack killShotTrack;

    @Before
    public void init(){
        killShotTrack = new KillShotTrack(SIZE);
    }


    @Test
    public void assertInitiallyEmpty() {
        for (KillShot k : killShotTrack.getKillShotTrack()) {
            assertNull(k.getColour());
            assertTrue(k.isSkulled());
            assertFalse(k.isOverkilled());
        }
    }


    @Test
    public void assertWellFilled() {
        killShotTrack.killOccured(PcColourEnum.YELLOW, true);
        killShotTrack.killOccured(PcColourEnum.GREEN, false);
        killShotTrack.killOccured(PcColourEnum.GREEN, false);

        assertFalse(killShotTrack.getKillShotTrack()[SIZE - 1].isSkulled());
        assertTrue(killShotTrack.getKillShotTrack()[SIZE - 1].isOverkilled());
        assertEquals(killShotTrack.getKillShotTrack()[SIZE - 1].getColour(), PcColourEnum.YELLOW);

        assertFalse(killShotTrack.getKillShotTrack()[SIZE - 2].isSkulled());
        assertFalse(killShotTrack.getKillShotTrack()[SIZE - 2].isOverkilled());
        assertEquals(killShotTrack.getKillShotTrack()[SIZE - 2].getColour(), PcColourEnum.GREEN);

        assertFalse(killShotTrack.getKillShotTrack()[SIZE - 3].isSkulled());
        assertFalse(killShotTrack.getKillShotTrack()[SIZE - 3].isOverkilled());
        assertEquals(killShotTrack.getKillShotTrack()[SIZE - 3].getColour(), PcColourEnum.GREEN);

        for (int i = 0; i < SIZE - 4; i++) {
            assertTrue(killShotTrack.getKillShotTrack()[i].isSkulled());
            assertFalse(killShotTrack.getKillShotTrack()[i].isOverkilled());
            assertNull(killShotTrack.getKillShotTrack()[i].getColour());
        }
    }


    @Test
    public void convertedToValidDTO(){
        killShotTrack.killOccured(PcColourEnum.YELLOW, true);
        killShotTrack.killOccured(PcColourEnum.GREEN, false);
        killShotTrack.killOccured(PcColourEnum.GREEN, false);
        KillShotTrackDTO kstdto = killShotTrack.convertToDTO();
        assertEquals(killShotTrack.getKillShotTrack().length, killShotTrack.convertToDTO().getKillShotTrack().length);
        for (int i = 0; i < SIZE; i++) {
            assertEquals(killShotTrack.getKillShotTrack()[i].isSkulled(), kstdto.getKillShotTrack()[i].isSkulled());
            assertEquals(killShotTrack.getKillShotTrack()[i].isOverkilled(), kstdto.getKillShotTrack()[i].isOverkilled());
            assertEquals(killShotTrack.getKillShotTrack()[i].getColour(), kstdto.getKillShotTrack()[i].getColour());
        }
        assertEquals(killShotTrack.getFinalFrenzyKillShotTrack().length, killShotTrack.convertToDTO().getFinalFrenzyKillShotTrack().length);
        for (int i = 0; i < 5; i++) {
            assertEquals(killShotTrack.getFinalFrenzyKillShotTrack()[i].isSkulled(),kstdto.getFinalFrenzyKillShotTrack()[i].isSkulled());
            assertEquals(killShotTrack.getFinalFrenzyKillShotTrack()[i].isOverkilled(), kstdto.getFinalFrenzyKillShotTrack()[i].isOverkilled());
            assertEquals(killShotTrack.getFinalFrenzyKillShotTrack()[i].getColour(), kstdto.getFinalFrenzyKillShotTrack()[i].getColour());
        }
    }
}
