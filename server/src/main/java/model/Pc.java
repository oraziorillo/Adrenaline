package model;

import exceptions.FullyArmedException;
import enums.PcColourEnum;
import exceptions.NotEnoughAmmosException;
import java.util.ArrayList;

import static model.Constants.LIFEPOINTS;
import static model.Constants.MAX_WEAPONS_IN_HAND;

public class Pc {
    private final Game currGame;
    private final String name;
    private final PcColourEnum colour;
    private PcBoard pcBoard;
    private short adrenaline;
    private WeaponCard[] weapons;
    private ArrayList<PowerUpCard> powerUps;
    private Square currSquare;


    public Pc(PcColourEnum colour, Game game) {
        this.currGame = game;
        this.name = colour.getName();
        this.colour = colour;
        this.adrenaline = 0;
        this.pcBoard = new PcBoard();
        this.weapons = new WeaponCard[MAX_WEAPONS_IN_HAND];
        this.powerUps = new ArrayList<>();
        this.currSquare = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
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
        return name;
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

    public WeaponCard[] getWeapons(){
        return this.weapons;
    }

    public PowerUpCard getPowerUpCard(int index){
        if(index < 0 || index > powerUps.size() - 1){
            throw new IllegalArgumentException("This index is not valid");
        }
        return powerUps.get(index);
    }

    private WeaponCard weaponAtIndex(int index) {
        WeaponCard temp;
        if (index < 0 || index > 3) {
            throw new IllegalArgumentException("This index is not valid");
        }
        temp = weapons[index];
        return temp;
    }

    public void moveTo(Square t){
        if(t == null){
            throw new IllegalArgumentException("Invalid tile");
        }
        else {
            this.currSquare.removePc(this);
            this.currSquare = t;
            this.currSquare.addPc(this);
        }
    }

    public void drawPowerUp(){
        powerUps.add((PowerUpCard)currGame.powerUpsDeck.draw());
    }

    public void discardPowerUp(PowerUpCard p){
        if(powerUps.contains(p)){
            powerUps.remove(p);
        }
        else{
            throw new IllegalArgumentException("You don't have this powerUp");
        }
    }

    public void collectWeapon(int weaponIndex) throws FullyArmedException {        //l'arma deve poi essere rimossa dal punto di generazione
        if (!currGame.getSpawnSquares().contains( currSquare )) {
            throw new IllegalStateException("You are not in a SpawnSquare");
        }
        if (isFullyArmed()){
            throw new FullyArmedException("You have to drop one card");
        }
        SpawnSquare workingTile = ( SpawnSquare ) currSquare;
        int index = 0;
        while (index < weapons.length && weapons[index] != null) {
            index += 1;
        }
        weapons[index] = workingTile.pickWeapon(weaponIndex);
    }

    public void switchWeapons(int weaponToGrabIndex, int weaponToDropIndex) {
        if (!currGame.getSpawnSquares().contains( currSquare )) {
            throw new IllegalStateException("You are not in a SpawnSquare");
        }
        SpawnSquare workingTile = ( SpawnSquare ) currSquare;
        weapons[weaponToDropIndex] = workingTile.switchWeapon(weaponToGrabIndex, weaponAtIndex(weaponToDropIndex));
    }

    public void collectAmmos() {
        if (currGame.getSpawnSquares().contains( currSquare )) {     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in an AmmoSquare");
        }
        AmmoSquare workingTile = ( AmmoSquare ) currSquare;
        AmmoCard card = workingTile.pickAmmo();
        pcBoard.addAmmos(card);
        if (card.containsPowerup()) {      //da rivedere, troppo dipendente dalla classe AmmoCard??
            drawPowerUp();
        }
    }

    public void takeMarks(PcColourEnum colour, short marks){
        pcBoard.addMarks(colour, marks);
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
        if(!currGame.getSpawnSquares().contains(t)){
            throw new IllegalArgumentException("Not a spawn Square");
        }
        pcBoard.increaseNumberOfDeaths();
        pcBoard.resetDamageTrack();
        this.adrenaline = 0;
        this.currSquare = t;
    }

    public void payAmmos(short[] ammos) throws NotEnoughAmmosException{
        pcBoard.payAmmos(ammos);
    }
}


