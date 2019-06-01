package model.squares;

import enums.SquareColourEnum;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.Deck;
import model.Pc;
import model.WeaponCard;

public class SpawnPoint extends Square {

    private WeaponCard[] weapons;
    private Deck<WeaponCard> weaponDeck;
    private int weaponToGrabIndex;
    private int weaponToDropIndex;

    public SpawnPoint(int x, int y, SquareColourEnum colour, Deck<WeaponCard> deck) {
        super(x, y, colour);
        this.weaponDeck = deck;
        this.weaponToGrabIndex = -1;
        this.weaponToDropIndex = -1;
        weapons = new WeaponCard[3];
        for (int i = 0; i < 3; i++)
            weapons[i] = weaponDeck.draw();
    }


    @Override
    public boolean isEmpty() {
        for (WeaponCard weapon : weapons)
            if (weapon != null)
                return false;
        return true;
    }


    @Override
    public boolean isSpawnPoint() {
        return true;
    }
    
    @Override
    public void setWeaponToGrabIndex(int weaponToGrabIndex) {
        if (weapons[weaponToGrabIndex] != null)
            this.weaponToGrabIndex = weaponToGrabIndex;
        else
            throw new NullPointerException("You have to choose a weapon to grab");
    }


    @Override
    public void setWeaponToDropIndex(int weaponToDropIndex) {
        this.weaponToDropIndex = weaponToDropIndex;
    }


    @Override
    public void resetWeaponIndexes() {
        this.weaponToDropIndex = -1;
        this.weaponToGrabIndex = -1;
    }


    public WeaponCard weaponAtIndex(int index){
        if (index >= 0 && index <= 2)
            return weapons[index];
        return null;
    }
    
    /**
     * Adds the pre-selected weapon to the given player, unless
     * 1. this square is not empty
     * 2. the pre-selected weapon index is not negative
     * 3. the weapon slot on that index is not empty
     * 4. the given pc already has the max weapon number and didn't select a weapon to drop (drop index <0)
     * @param currPc the pc collecting from this tile
     * @throws EmptySquareException if no weapon is available
     * @throws NotEnoughAmmoException if the player cannot pay the half-recharge cost
     */
    @Override
    public void collect(Pc currPc) throws EmptySquareException, NotEnoughAmmoException {
        if (isEmpty())
            throw new EmptySquareException();
        if (weaponToGrabIndex < 0)
            throw new IllegalStateException("You have to choose a weapon to grab");
        if (weapons[weaponToGrabIndex] == null)
            throw new NullPointerException("No weapon on that slot");
        WeaponCard weaponToGrab = weapons[weaponToGrabIndex];WeaponCard weaponToDrop = currPc.weaponAtIndex(weaponToDropIndex);
        weapons[weaponToGrabIndex] = weaponToDrop;
        currPc.addWeapon(weaponToGrab, weaponToDropIndex);
        short[] cost = weaponToGrab.getAmmo();
        cost[weaponToGrab.getWeaponColour().ordinal()]--;
        if (weaponToDropIndex < 0 && currPc.isFullyArmed())
            throw new IllegalStateException("You have to choose a weapon to drop");
        if (!currPc.hasEnoughAmmo(cost))
            throw new NotEnoughAmmoException();
        currPc.payAmmo(cost);
        resetWeaponIndexes();
    }


    public void refill() {
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i] == null && weaponDeck.size() > 0) {
                weapons[i] = weaponDeck.draw();
            }
        }
    }

}
