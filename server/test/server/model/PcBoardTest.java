package server.model;

import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PcBoardTest {

    private PcBoard testedPcBoard;

    @Before
    public void setupTest() {
        testedPcBoard = new PcBoard(PcColourEnum.GREEN);
    }

    @Test
    public void increasePointsWorksFine() {
        testedPcBoard.increasePoints(10);
        assertEquals(10, testedPcBoard.getPoints());
    }

    @Test
    public void getMarksReturnsTheNUmberOfMarksRelatedToEachPc() {
        short numOfMarks = 2;
        testedPcBoard.addMarks(PcColourEnum.YELLOW, numOfMarks);
        assertEquals(0, testedPcBoard.getMarks(PcColourEnum.PURPLE));
        assertEquals(testedPcBoard.getMarks(PcColourEnum.YELLOW), numOfMarks);
    }

    @Test
    public void getMarksNeverReturnsANumberOfMarksBiggerThan3() {
        short numOfMarks = 4;
        testedPcBoard.addMarks(PcColourEnum.YELLOW, numOfMarks);
        assertEquals(3, testedPcBoard.getMarks(PcColourEnum.YELLOW));
        assertNotEquals(testedPcBoard.getMarks(PcColourEnum.YELLOW), numOfMarks);
        testedPcBoard.addMarks(PcColourEnum.YELLOW, (short)1);
        assertEquals(3, testedPcBoard.getMarks(PcColourEnum.YELLOW));
    }

    @Test
    public void addAmmoWorksFineAndNeverSetsForEachColourMoreThanThreeAmmos() {
        short [] firstAmmo = new short[]{2,1,0};
        AmmoTile ammoTile = Mockito.mock(AmmoTile.class);
        when(ammoTile.getAmmo()).thenReturn(firstAmmo);
        when(ammoTile.hasPowerUp()).thenReturn(false);
        testedPcBoard.addAmmo(ammoTile);
        assertEquals(3, testedPcBoard.getAmmo()[AmmoEnum.BLUE.ordinal()]);
        assertEquals(2, testedPcBoard.getAmmo()[AmmoEnum.RED.ordinal()]);
        assertEquals(1, testedPcBoard.getAmmo()[AmmoEnum.YELLOW.ordinal()]);
        short [] secondAmmo = new short[]{2,1,0};
        AmmoTile ammoTile1 = Mockito.mock(AmmoTile.class);
        when(ammoTile.getAmmo()).thenReturn(secondAmmo);
        when(ammoTile.hasPowerUp()).thenReturn(false);
        assertEquals(3, testedPcBoard.getAmmo()[AmmoEnum.BLUE.ordinal()]);
    }

    @Test
    public void addDamageWorksFine() {
        short inflictedDamage = 7;
        testedPcBoard.addDamage(PcColourEnum.YELLOW, inflictedDamage);
        for (int i = 0; i < inflictedDamage; i++)
            assertEquals(testedPcBoard.getDamageTrack()[i], PcColourEnum.YELLOW);
        assertNotEquals(testedPcBoard.getDamageTrack()[inflictedDamage], PcColourEnum.YELLOW);
        assertNull(testedPcBoard.getDamageTrack()[inflictedDamage]);
        testedPcBoard.addDamage(PcColourEnum.BLUE, inflictedDamage);
        assertEquals(testedPcBoard.getDamageTrack()[inflictedDamage], PcColourEnum.BLUE);
    }

    @Test
    public void hasAtLeastOneAmmoWorksFine() {
        short [] ammoToPay = new short[]{3,3,3};
        assertEquals(true, testedPcBoard.hasAtLeastOneAmmo());
        testedPcBoard.payAmmo(ammoToPay);
        assertFalse(testedPcBoard.hasAtLeastOneAmmo());
    }

    @Test
    public void payAmmoDecreaseTheNumberOfAmmosAndReturnsTheRemainingAmmosToPay() {
        short [] ammoToPay = new short[]{2,1,2};
        short [] remainingAmmos = new short[]{1,0,1};
        assertArrayEquals(remainingAmmos, testedPcBoard.payAmmo(ammoToPay));
    }

    @Test
    public void resetDamageTrackFlushesTheOldValuesAndSetsTheDamageTrackIndexToZero() {
        PcColourEnum attackerColour = PcColourEnum.YELLOW;
        short valueOfDamage = 5;
        testedPcBoard.addDamage(attackerColour, valueOfDamage);
        assertEquals(5, testedPcBoard.getDamageTrackIndex());
        testedPcBoard.resetDamageTrack();
        assertEquals(0, testedPcBoard.getDamageTrackIndex());
        for(int i =0; i < valueOfDamage; i++)
            assertNull(testedPcBoard.getDamageTrack()[i]);
    }
}
