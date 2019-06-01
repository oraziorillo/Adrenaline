package model;

import enums.AmmoEnum;
import enums.PcColourEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PcBoardTest {

    private PcBoard tested;

    @Before
    public void setupTest() {
        tested = new PcBoard();
    }

    @Test
    public void increaseNumberOfDeathsWorksFine(){
        tested.increaseNumberOfDeaths();
        assertTrue(tested.getNumOfDeaths() == 1);
    }

    @Test
    public void increasePointsWorksFine() {
        tested.increasePoints(10);
        assertTrue(tested.getPoints() == 10);
    }

    @Test
    public void getMarksReturnsTheNUmberOfMarksRelatedToEachPc() {
        short numOfMarks = 2;
        tested.addMarks(PcColourEnum.YELLOW, numOfMarks);
        assertTrue(tested.getMarks(PcColourEnum.PURPLE) == 0);
        assertTrue(tested.getMarks(PcColourEnum.YELLOW) ==  numOfMarks);
    }

    @Test
    public void getMarksNeverReturnsANumberOfMarksBiggerThan3() {
        short numOfMarks = 4;
        tested.addMarks(PcColourEnum.YELLOW, numOfMarks);
        assertTrue(tested.getMarks(PcColourEnum.YELLOW) == 3);
        assertFalse(tested.getMarks(PcColourEnum.YELLOW) ==  numOfMarks);
        tested.addMarks(PcColourEnum.YELLOW, (short)1);
        assertTrue(tested.getMarks(PcColourEnum.YELLOW) == 3);
    }

    @Test
    public void addAmmoWorksFineAndNeverSetsForEachColourMoreThanThreeAmmos() {
        short [] firstAmmo = new short[]{2,1,0};
        AmmoTile ammoTile = new AmmoTile(firstAmmo, false);
        tested.addAmmo(ammoTile);
        assertTrue(tested.getAmmo()[AmmoEnum.BLUE.ordinal()] == 3);
        assertTrue(tested.getAmmo()[AmmoEnum.RED.ordinal()] == 2);
        assertTrue(tested.getAmmo()[AmmoEnum.YELLOW.ordinal()] == 1);
        short [] secondAmmo = new short[]{2,1,0};
        AmmoTile ammoTile1 = new AmmoTile(secondAmmo, false);
        assertTrue(tested.getAmmo()[AmmoEnum.BLUE.ordinal()] == 3);
    }

    @Test
    public void addDamageWorksFine() {
        short inflictedDamage = 7;
        tested.addDamage(PcColourEnum.YELLOW, inflictedDamage);
        for (int i = 0; i < inflictedDamage; i++)
            assertTrue(tested.getDamageTrack()[i] == PcColourEnum.YELLOW);
        assertFalse(tested.getDamageTrack()[inflictedDamage] == PcColourEnum.YELLOW);
        assertNull(tested.getDamageTrack()[inflictedDamage]);
        tested.addDamage(PcColourEnum.BLUE, inflictedDamage);
        assertTrue(tested.getDamageTrack()[inflictedDamage] == PcColourEnum.BLUE);
    }

    @Test
    public void hasAtLeastOneAmmoWorksFine() {
        short [] ammoToPay = new short[]{3,3,3};
        assertTrue(tested.hasAtLeastOneAmmo() == true);
        tested.payAmmo(ammoToPay);
        assertFalse(tested.hasAtLeastOneAmmo());
    }

    @Test
    public void payAmmoDecreaseTheNumberOfAmmosAndReturnsTheRemainingAmmosToPay() {
        short [] ammoToPay = new short[]{2,1,2};
        short [] remainingAmmos = new short[]{1,0,1};
        assertArrayEquals(remainingAmmos, tested.payAmmo(ammoToPay));
    }

    @Test
    public void resetDamageTrackFlushesTheOldValuesAndSetsTheDamageTrackIndexToZero() {
        PcColourEnum attackerColour = PcColourEnum.YELLOW;
        short valueOfDamage = 5;
        tested.addDamage(attackerColour, valueOfDamage);
        assertTrue(tested.getDamageTrackIndex() == 5);
        tested.resetDamageTrack();
        assertTrue(tested.getDamageTrackIndex() == 0);
        for(int i =0; i < valueOfDamage; i++)
            assertNull(tested.getDamageTrack()[i]);
    }
}
