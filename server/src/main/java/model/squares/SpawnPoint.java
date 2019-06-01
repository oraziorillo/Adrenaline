package model.squares;

import com.google.gson.annotations.Expose;
import enums.SquareColourEnum;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.AmmoTile;
import model.Deck;
import model.Pc;
import model.WeaponCard;

public class SpawnPoint extends Square {

    @Expose private int weaponToGrabIndex;
    @Expose private int weaponToDropIndex;
    private WeaponCard[] weapons;
    private Deck<WeaponCard> weaponsDeck;

    public SpawnPoint(){
        super();
        this.weaponToGrabIndex = -1;
        this.weaponToDropIndex = -1;
    }

    public SpawnPoint(int x, int y, SquareColourEnum colour) {
        super(x, y, colour);
        this.weaponToGrabIndex = -1;
        this.weaponToDropIndex = -1;
    }


    @Override
    public void assignDeck(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck) {
        this.weaponsDeck = weaponsDeck;
        weapons = new WeaponCard[3];
        for (int i = 0; i < 3; i++)
            weapons[i] = weaponsDeck.draw();
    }


    @Override
    public boolean isEmpty() {
        for (WeaponCard weapon : weapons)
            if (weapon != null)
                return false;
        return true;
    }

    public WeaponCard[] getWeapons() {
        return weapons;
    }

    public int getWeaponToDropIndex() {
        return weaponToDropIndex;
    }



    @Override
    public boolean isSpawnPoint() {
        return true;
    }

    @Override
    public void setWeaponToGrabIndex(int weaponToGrabIndex) {
        if (weaponToGrabIndex >= 0 && weaponToGrabIndex < 3 && weapons[weaponToGrabIndex] != null)
            this.weaponToGrabIndex = weaponToGrabIndex;
        else
            throw new NullPointerException("You have to choose a weapon to grab");
    }


    @Override
    public void setWeaponToDropIndex(int weaponToDropIndex) {
        if (weaponToDropIndex >= 0 && weaponToDropIndex < 3)
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
        WeaponCard weaponToGrab = weapons[weaponToGrabIndex];
        if (!currPc.hasEnoughAmmo(weaponToGrab.getAmmo()))
            throw new NotEnoughAmmoException();
        if (weaponToDropIndex > 0) {
            if (!currPc.isFullyArmed())
                throw new IllegalStateException("You can't drop a weapon now");
            else {
                WeaponCard weaponToDrop = currPc.weaponAtIndex(weaponToDropIndex);
                weapons[weaponToGrabIndex] = weaponToDrop;
            }
        } else {
            if (currPc.isFullyArmed())
                throw new IllegalStateException("You have to choose a weapon to drop");
            weapons[weaponToGrabIndex] = null;
        }
        currPc.addWeapon(weaponToGrab, weaponToDropIndex);
        short[] cost = weaponToGrab.getAmmo();
        cost[weaponToGrab.getColour().ordinal()]--;
        currPc.payAmmo(cost);
        resetWeaponIndexes();
    }


    public void refill() {
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i] == null && weaponsDeck.size() > 0) {
                weapons[i] = weaponsDeck.draw();
            }
        }
    }

}
