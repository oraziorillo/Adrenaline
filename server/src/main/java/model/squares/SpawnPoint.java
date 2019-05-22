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

    SpawnPoint(int x, int y, SquareColourEnum colour, Deck<WeaponCard> deck) {
        super(x, y, colour);
        this.weaponDeck = deck;
        this.weaponToGrabIndex = -1;
        this.weaponToDropIndex = -1;
        weapons = new WeaponCard[3];
        for (int i = 0; i < 3; i++)
            weapons[i] = weaponDeck.draw();
    }


    @Override
    public void setWeaponToGrabIndex(int weaponToGrabIndex) {
        //TODO: lanciare eccezione per l'index
        this.weaponToGrabIndex = weaponToGrabIndex;
    }


    @Override
    public void setWeaponToDropIndex(int weaponToDropIndex) {
        //TODO: lanciare eccezione per l'index
        this.weaponToDropIndex = weaponToDropIndex;
    }


    @Override
    public void resetWeaponIndexes() {
        this.weaponToDropIndex = -1;
        this.weaponToGrabIndex = -1;
    }


    @Override
    public void collect(Pc currPc) throws EmptySquareException, NotEnoughAmmoException {
        if (isEmpty())
            throw new EmptySquareException();
        if (weaponToGrabIndex < 0)
            throw new IllegalStateException("You have to choose a weapon to grab");
        WeaponCard weaponToGrab = weapons[weaponToGrabIndex];
        if (weaponToDropIndex < 0 && currPc.isFullyArmed())
            throw new IllegalStateException("You have to choose a weapon to drop");
        else {
            WeaponCard weaponToDrop = currPc.weaponAtIndex(weaponToDropIndex);
            weapons[weaponToGrabIndex] = weaponToDrop;
        }
        currPc.addWeapon(weaponToGrab, weaponToDropIndex);
        short[] cost = weaponToGrab.getCurrentCost();
        cost[weaponToGrab.getWeaponColour().ordinal()]--;
        currPc.payAmmo(cost);
        resetWeaponIndexes();
    }


    public void refill() {
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i] == null) {
                weapons[i] = weaponDeck.draw();
            }
        }
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
}
