package server.model;

import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import org.mockito.Mock;
import server.model.squares.AmmoSquare;
import server.model.squares.SpawnPoint;
import server.model.squares.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PcTest {

    private final PcColourEnum colour = PcColourEnum.YELLOW;
    private Game game;
    private Pc tested;
    private Deck<PowerUpCard> deck;
    private AmmoTile ammoTile;
    private WeaponCard card0, card1, card2;
    @Mock
    private ModelEventHandler eventHandler;

    @Before
    public void SetupTest() {
        game = Mockito.mock(Game.class);
        tested = new Pc(colour, game);
        tested.addModelEventHandler(eventHandler);
        deck = Mockito.mock(Deck.class);
        ammoTile = Mockito.mock(AmmoTile.class);
        card0 = Mockito.mock(WeaponCard.class);
        card1 = Mockito.mock(WeaponCard.class);
        card2 = Mockito.mock(WeaponCard.class);
    }


    @Test
    public void isFullyArmedReturnsTrueIfPcHasThreeWeaponCards() {
//        Deck<WeaponCard> deck = Mockito.mock(Deck.class);
//
        tested.addWeapon(card0, 0);
        tested.addWeapon(card1, 1);
        tested.addWeapon(card2, 2);
        assertTrue(tested.isFullyArmed());
    }

    @Test
    public void hasMAxPowerUpNumberWorksFine(){
        assertFalse(tested.hasMaxPowerUpNumber());
    }

    @Test
    public void getColourWorksFine() {
        assertSame(tested.getColour(), colour);
    }

    @Test
    public void getAdrenalineWorksFine( ){
        assertEquals(0, tested.getAdrenaline());
        tested.takeDamage(PcColourEnum.BLUE, (short)3);
        assertEquals(1, tested.getAdrenaline());
        tested.takeDamage(PcColourEnum.GREEN, (short)3);
        assertEquals(2, tested.getAdrenaline());
    }

    @Test
    public void getPowerUpCardReturnsPowerUpAtTheSelectedIndex() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp);
        tested.drawPowerUp(1);
        assertSame(powerUp, tested.getPowerUpCard(0) );
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> tested.getPowerUpCard(1) );
    }

    @Test
    public void getWeaponAtIndexReturnsWeaponAtSelectedIndex() {
        int index = 0;
        tested.addWeapon(card0, index);
        tested.addWeapon(card1, -1);
        assertEquals(card0, tested.weaponAtIndex(index));
        assertEquals(card1, tested.weaponAtIndex(index+1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> tested.weaponAtIndex(-1));
    }

    @Test
    public void spawnAndMoveToTakeAsParameterTheNewPositionOfPcAndMoveHimThere() {
        Square firstDestinationSquare = Mockito.mock(SpawnPoint.class);
        Square secondDestinationSquare = Mockito.mock(AmmoSquare.class);
        when(firstDestinationSquare.isSpawnPoint()).thenReturn(true);
        assumeTrue(firstDestinationSquare.getPcs().isEmpty());
        tested.spawn(firstDestinationSquare);
        assertSame(firstDestinationSquare, tested.getCurrSquare());
//        assertTrue(firstDestinationSquare.getPcs().contains(tested));
        tested.moveTo(secondDestinationSquare);
//        assertFalse(firstDestinationSquare.getPcs().contains(tested));
//        assertTrue(secondDestinationSquare.getPcs().contains(tested));
        assertSame(secondDestinationSquare, tested.getCurrSquare());
    }

    @Test
    public void drawPowerUpWorksFine() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp);
        tested.drawPowerUp(1);
        assertSame(powerUp, tested.getPowerUpCard(0));
    }

    @Test
    public void discardPowerUpRemoveTheGivenPowerUpIfPresent() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp);
        tested.drawPowerUp(1);
        assertEquals(1, tested.getPowerUps().size());
        assertSame(powerUp, tested.getPowerUpCard(0));
        tested.discardPowerUp(powerUp);
        assertEquals(0, tested.getPowerUps().size());
        assertFalse(tested.getPowerUps().contains(powerUp));
        assertThrows(IllegalArgumentException.class, () -> tested.discardPowerUp(powerUp));
    }

    @Test
    public void addAmmoWorksFine() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp);
        short [] ammo = new short []{1,1,0};
        AmmoTile ammoTile = Mockito.mock(AmmoTile.class);
        when(ammoTile.getAmmo()).thenReturn(ammo);
        when(ammoTile.hasPowerUp()).thenReturn(true);
        assertEquals(0, tested.getPowerUps().size());
        tested.addAmmo(ammoTile);
        assertEquals(2, tested.getAmmo()[0]);
        assertEquals(2, tested.getAmmo()[1]);
        assertEquals(1, tested.getAmmo()[2]);
        assertEquals(1, tested.getPowerUps().size());
    }


    @Test
    public void addWeaponAddsSelectedWeaponAtSelectedIndex() {
        tested.addWeapon(card0, 0);
        tested.addWeapon(card1, 1);
        tested.addWeapon(card2, -1);
        assertEquals(3, tested.getWeapons().length);
        assertEquals(card0, tested.weaponAtIndex(0));
        assertEquals(card1, tested.weaponAtIndex(1));
        assertEquals(card2, tested.weaponAtIndex(2));
    }

    @Test
    public void takeDamageAddsToThePcBoardTheRequiredAmountOfDamage() {
        tested.takeMarks(PcColourEnum.GREEN, (short)3);
        assertEquals(0, tested.getAdrenaline());
        tested.takeDamage(PcColourEnum.GREEN, (short)3);
        assertEquals(2, tested.getAdrenaline());
        assertEquals(PcColourEnum.GREEN, tested.getDamageTrack()[5]);
        assertNull( tested.getDamageTrack()[6]);
    }

    @Test
    public void hasAtLeastOneAvailableAmmoWorksFine() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp);
        short [] ammoToPay = new short []{1,1,1};
        assertTrue(tested.hasAtLeastOneAvailableAmmo());
        tested.payAmmo(ammoToPay);
        assertFalse(tested.hasAtLeastOneAvailableAmmo());
        tested.drawPowerUp(1);
        tested.drawPowerUp(1);
        assertTrue(tested.hasAtLeastOneAvailableAmmo());
    }

    @Test
    public void hasEnoughAmmoConsidersAvailableSelectedPowerUpsAndPcAmmo() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        PowerUpCard powerUp2 = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp).thenReturn(powerUp2);
        when(powerUp.isSelectedAsAmmo()).thenReturn(false);
        when(powerUp2.isSelectedAsAmmo()).thenReturn(true);
        when(powerUp2.getColour()).thenReturn(AmmoEnum.RED);
        short [] ammo = new short []{2,1,1};
        short [] ammo2 = new short []{1,2,1};
        tested.drawPowerUp(1);
        tested.drawPowerUp(1);
        assertFalse(tested.hasEnoughAmmo(ammo));
        assertTrue(tested.hasEnoughAmmo(ammo2));
    }

    @Test
    public void payAmmoUsesAmmoAndSelectedPOwerUpsToPay() {
        PowerUpCard powerUp = Mockito.mock(PowerUpCard.class);
        when(game.drawPowerUp()).thenReturn(powerUp);
        when(powerUp.isSelectedAsAmmo()).thenReturn(true);
        when(powerUp.getColour()).thenReturn(AmmoEnum.BLUE);
        short [] ammo = new short []{2,1,1};
        short [] ammo2 = new short []{1,2,1};
        short [] resultAmmo = new short []{0,0,0};
        tested.drawPowerUp(1);
        assertTrue(tested.hasEnoughAmmo(ammo));
        assertFalse(tested.hasEnoughAmmo(ammo2));
        tested.payAmmo(ammo);
        assertArrayEquals(resultAmmo, tested.getAmmo());
        assertEquals(0, tested.getPowerUps().size());
    }
}
