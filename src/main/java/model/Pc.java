package model;

import model.Enumerations.AmmoEnum;
import model.Enumerations.PcColourEnum;
import model.Enumerations.TileColourEnum;
import model.Exceptions.NotEnoughAmmosException;

import java.util.ArrayList;

import static model.Constants.LIFEPOINTS;
import static model.Constants.MAX_WEAPONS_IN_HAND;

/**
 * @author matteo
 * @implSpec A tutti i tutorati ci hanno detto di snellire questa classe, che è troppo GodClass
 */
public class Pc {
    private final Game currGame;
    private final String name;
    private final PcColourEnum colour;
    private PcBoard pcBoard;
    private short adrenaline;
    private WeaponCard[] weapons;
    private ArrayList<PowerUpCard> powerUps;
    private Tile currTile;


    public Pc(PcColourEnum colour, Game game) {
        this.currGame = game;
        this.name = colour.getName();
        this.colour = colour;
        this.adrenaline = 0;
        this.weapons = new WeaponCard[MAX_WEAPONS_IN_HAND];
        this.powerUps = new ArrayList<>();
        this.currTile = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
    }

    public WeaponCard[] getWeapons(){
        return this.weapons;
    }

    public boolean isFullyArmed() {
        for (WeaponCard weapon : weapons) {
            if (weapon == null) {
                return false;
            }
        }
        return true;
    }

    public PowerUpCard getPowerUpCard(int index) throws IllegalArgumentException{
        if(index < 0 || index > 3){
            throw new IllegalArgumentException("This index is not valid");
        }
        return powerUps.get(index);
    }

    public boolean isFullyPoweredUp(){
        return powerUps.size() == 3;
    }

    public PcColourEnum getColour(){
        return colour;
    }

    public String getName() {
        return name;
    }

    public int getAdrenaline(){
        return adrenaline;
    }

    public Tile getCurrTile() {
        return this.currTile;
    }

    private WeaponCard weaponIndex(int index) {
        WeaponCard temp;
        if (index < 0 || index > 3) {
            throw new IllegalArgumentException("This index is not valid");
        }
        temp = weapons[index];
        return temp;
    }


    public void setCurrTile(Tile t){
        if(t == null){
            throw new IllegalArgumentException("Invalid tile");
        }
        else {
            this.currTile = t;
        }
    }


    public void collectWeapon(int weaponIndex) {        //l'arma deve poi essere rimossa dal punto di generazione
        if (!(currTile instanceof SpawnTile)) {     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in a SpawnTile");
        }
        SpawnTile workingTile = (SpawnTile) currTile;
        int index = 0;
        while (index < weapons.length && weapons[index] != null) {
            index += 1;
        }
        weapons[index] = workingTile.pickWeapon(weaponIndex);
    }

    public void collectWeapon(int desiredWeaponIndex, int toDropWeaponIndex) {
        if (!(currTile instanceof SpawnTile)) {
            throw new IllegalStateException("You are not in a SpawnTile");
        }
        SpawnTile workingTile = (SpawnTile) currTile;
        weapons[toDropWeaponIndex] = workingTile.switchWeapon(desiredWeaponIndex, weaponIndex(toDropWeaponIndex));
    }

    public void collectAmmos() {
        if (!(currTile instanceof AmmoTile)) {     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in an AmmoTile");
        }
        AmmoTile workingTile = (AmmoTile) currTile;
        AmmoCard card = workingTile.pickAmmo();
        pcBoard.addAmmos(card);
        if (card.containsPowerup()) {      //da rivedere, troppo dipendente dalla classe AmmoCard??
            drawPowerUp();
        }
    }

    public void drawPowerUp(){
        powerUps.add((PowerUpCard)currGame.powerUpsDeck.draw());
    }

    public void takeMarks(short marks){
        PcColourEnum colour = currGame.getCurrentPc().getColour();
        pcBoard.addMarks(colour, marks);
    }

    public void takeDamage(short damage) {
        short totalDamage;
        totalDamage = (short) (pcBoard.getMarks(currGame.getCurrentPc().getColour()) + damage);
        pcBoard.addDamage(currGame.getCurrentPc().getColour(), totalDamage);
        int damageIndex = pcBoard.getDamageTrackIndex();
        if(damageIndex >= LIFEPOINTS-2){
            //TODO notify controller e view
            //gestire qui la morte con eventuali observer
        }
        if (damageIndex > 4)
            adrenaline = 2;
        else if (damageIndex > 1)
            adrenaline = 1;
    }


    public void respawn(Tile t) throws IllegalArgumentException{
        if(!currGame.getSpawnTiles().contains(t)){
            throw new IllegalArgumentException("Not a spawn Tile");
        }
        pcBoard.respawn();
        this.adrenaline = 0;
        this.currTile = t;
    }

    public void payAmmos(short[] ammos) throws NotEnoughAmmosException{
        try{
            pcBoard.payAmmos(ammos);
        }
        catch (Exception NotEnoughAmmosException){
            System.out.println("It is not valid");
        }
    }
}


