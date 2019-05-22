package model;

import enums.PcColourEnum;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.powerUps.PowerUpCard;
import model.squares.Square;

import java.util.ArrayList;

import static model.Constants.LIFEPOINTS;
import static model.Constants.MAX_WEAPONS_IN_HAND;

public class Pc {
    private final Game currGame;
    private final PcColourEnum colour;
    private PcBoard pcBoard;
    private short adrenaline;
    private WeaponCard[] weapons;
    private ArrayList<PowerUpCard> powerUps;
    private Square currSquare;


    public Pc(PcColourEnum colour, Game game) {
        this.currGame = game;
        this.colour = colour;
        this.pcBoard = new PcBoard();
        this.weapons = new WeaponCard[MAX_WEAPONS_IN_HAND];
        this.powerUps = new ArrayList<>();
    }


    public boolean isFullyArmed() {
        for (WeaponCard weapon : weapons) {
            if (weapon == null) {
                return false;
            }
        }
        return true;
    }


    public boolean isFullyPoweredUp(){
        return powerUps.size() == 3;
    }


    public String getName() {
        return colour.getName();
    }


    public PcColourEnum getColour(){
        return colour;
    }


    public int getAdrenaline(){
        return adrenaline;
    }


    public Square getCurrSquare() {
        return this.currSquare;
    }


    public PowerUpCard getPowerUpCard(int index){
        if(index < 0 || index > powerUps.size() - 1){
            throw new IllegalArgumentException("This index is not valid");
        }
        return powerUps.get(index);
    }


    public WeaponCard weaponAtIndex(int index) {
        if (index < 0 || index > 3) {
            throw new IllegalArgumentException("This index is not valid");
        }
        return weapons[index];
    }


    public void moveTo(Square s){
        if(s == null){
            throw new IllegalArgumentException("Invalid square");
        }
        this.currSquare.removePc(this);
        this.currSquare = s;
        this.currSquare.addPc(this);
    }


    public void drawPowerUp(){
        powerUps.add(currGame.powerUpsDeck.draw());
    }


    public void discardPowerUp(PowerUpCard p){
        if(powerUps.contains(p)){
            powerUps.remove(p);
        }
        else{
            throw new IllegalArgumentException("You don't have this powerUp");
        }
    }


    public void collect() throws EmptySquareException {
        currSquare.collect(this);
    }


    public void addAmmo(AmmoTile ammo){
        pcBoard.addAmmo(ammo);
        if (ammo.containsPowerup()) {
            drawPowerUp();
        }
    }


    public void addWeapon(WeaponCard weapon, int index){
        weapons[index] = weapon;
    }

    /*
    public void collectWeapon(int weaponIndex) throws FullyArmedException {
        if (!currSquare.isSpawnPoint()) {
            throw new IllegalStateException("You are not in a SpawnPoint");
        }
        if (isFullyArmed()){
            throw new FullyArmedException("You have to drop one card");
        }
        SpawnPoint workingTile  =(SpawnPoint) currSquare;
        int index = 0;
        while (index < weapons.length && weapons[index] != null) {
            index += 1;
        }
        WeaponCard weaponToPick = workingTile.pickWeapon(weaponIndex);
        weapons[index] = weaponToPick;
        short[] cost = weaponToPick.getCurrentCost();
        cost[weaponToPick.getWeaponColour().ordinal()]--;
        payAmmo(cost);
    }


    public void switchWeapons(int weaponToGrabIndex, int weaponToDropIndex) {

        if (!currSquare.isSpawnPoint()) {
            throw new IllegalStateException("You are not in a SpawnPoint");
        }
        SpawnPoint workingTile = (SpawnPoint) currSquare;
        weapons[weaponToDropIndex] = workingTile.switchWeapon(weaponToGrabIndex, weaponAtIndex(weaponToDropIndex));
        //da correggere con payammo come metodo sopra
    }

     */


    public void takeMarks(PcColourEnum shooterColour, short marks){
        pcBoard.addMarks(shooterColour, marks);
    }


    public void takeDamage(PcColourEnum colour, short damages) {
        short totalDamage;
        totalDamage = (short) (pcBoard.getMarks(colour) + damages);
        pcBoard.addDamage(colour, totalDamage);
        int damageIndex = pcBoard.getDamageTrackIndex();
        if(damageIndex >= LIFEPOINTS - 2){
            //TODO notify controller e view
            currGame.killOccured(this.colour, damageIndex == (LIFEPOINTS - 1));
        }
        if (damageIndex > 4)
            adrenaline = 2;
        else if (damageIndex > 1)
            adrenaline = 1;
    }


    public void respawn(Square t){
        if(!t.isSpawnPoint()){
            throw new IllegalArgumentException("Not a spawn Square");
        }
        pcBoard.increaseNumberOfDeaths();
        pcBoard.resetDamageTrack();
        this.adrenaline = 0;
        this.currSquare = t;
    }


    public boolean hasEnoughAmmo(short[] ammos){
        return pcBoard.hasEnoughAmmo(ammos);
    }


    public void payAmmo(short[] ammos) throws NotEnoughAmmoException {
        pcBoard.payAmmo(ammos);
    }
}


