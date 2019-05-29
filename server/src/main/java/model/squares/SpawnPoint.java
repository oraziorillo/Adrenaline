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

    @Override
    public void collect(Pc currPc) throws EmptySquareException, NotEnoughAmmoException {
        if (isEmpty())
            throw new EmptySquareException();
        if (weaponToGrabIndex < 0)
            throw new IllegalStateException("You have to choose a weapon to grab");
        if (weapons[weaponToGrabIndex] == null)
            throw new NullPointerException();
        WeaponCard weaponToGrab = weapons[weaponToGrabIndex];
        if (weaponToDropIndex < 0 && currPc.isFullyArmed())
            throw new IllegalStateException("You have to choose a weapon to drop");
        if (!currPc.hasEnoughAmmo(weaponToGrab.getAmmo()))
            throw new NotEnoughAmmoException();
        WeaponCard weaponToDrop = currPc.weaponAtIndex(weaponToDropIndex);
        weapons[weaponToGrabIndex] = weaponToDrop;
        currPc.addWeapon(weaponToGrab, weaponToDropIndex);
        short[] cost = weaponToGrab.getAmmo();
        cost[weaponToGrab.getWeaponColour().ordinal()]--;
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
