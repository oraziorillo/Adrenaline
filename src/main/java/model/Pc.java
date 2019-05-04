package model;

import model.Enumerations.AmmoEnum;
import model.Enumerations.PcColourEnum;
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
    private short points;
    private short numOfDeath;
    private short adrenaline;
    private short[] marks;
    private short[] ammos;
    private PcColourEnum[] damageTrack;
    private WeaponCard[] weapons;
    private ArrayList<PowerUpCard> powerUps;
    private Tile currTile;


    public Pc(PcColourEnum colour, Game game) {
        this.currGame = game;
        this.colour = colour;
        this.name = colour.getName();
        this.points = 0;
        this.numOfDeath = 0;
        this.adrenaline = 0;
        this.marks = new short[currGame.getPcs().size()];
        this.ammos = new short[3];
        this.damageTrack = new PcColourEnum[LIFEPOINTS];
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
    //cambiare questo metodo in modo che riceva x,y della tile di destinazione è molto più comodo (fermo restando che si abbia l'istanza di currGame)

    public void move(Tile dest, int maxDist) {
        if(!currTile.atDistance(maxDist).contains(dest)){
            throw new IllegalArgumentException("Too far away");
        }
        currTile.removePc(this);
        dest.addPc(this);
        this.currTile = dest;
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
        for (int i = 0; i < card.getAmmos().length; i++) {
            this.ammos[i] += ammos[i];
            if (ammos[i] > 3)
                ammos[i] = 3;
        }
        if (card.containsPowerup()) {      //da rivedere, troppo dipendente dalla classe AmmoCard??
            drawPowerUp();
        }
    }
    public void drawPowerUp(){
        powerUps.add((PowerUpCard)currGame.powerUpsDeck.draw());

    }

    /* METODO STUPIDO A MIO AVVISO
    public void collectPowerUp(PowerUpCard p) {
        int index = 0;
        while (index < powerUps.length && powerUps[index] != null) {
            index += 1;
        }
        if (index < powerUps.length) {      //lo facciamo gestire dal controller?
            powerUps[index] = p;
        }
    }
     */

    public void takeDamage(short damage) {
        int index = 0;
        short totalDamage = (short) (marks[currGame.getCurrentPc().getColour().ordinal()] + damage);
        while (damageTrack[index] != null) {
            index = index + 1;
        }
        while (totalDamage != 0) {
            if (index == LIFEPOINTS)
                break;
            damageTrack[index] = currGame.getCurrentPc().getColour();
            index += 1;
        }
        //TODO notify controller e view
        if (index > 4)
            adrenaline = 2;
        else if (index > 1)
            adrenaline = 1;
    }

    public void takeMarks(short marks) {
        this.marks[colour.ordinal()] = marks;
    }

    public void increasePoints(int n) {
        this.points += n;
    }

    public void respawn(Tile t) throws IllegalArgumentException{
        if(!currGame.getSpawnTiles().contains(t))
            throw new IllegalArgumentException("Not a spawn Tile");
        this.damageTrack = new PcColourEnum[LIFEPOINTS];
        this.numOfDeath = (short) (numOfDeath + 1);
        this.adrenaline = 0;
        this.currTile = t;
    }

    /**
     * @param ammos
     * @throws NotEnoughAmmosException
     */
    public void payAmmos(short[] ammos) throws NotEnoughAmmosException {
        if (this.ammos[AmmoEnum.BLUE.ordinal()] < ammos[AmmoEnum.BLUE.ordinal()] ||
                this.ammos[AmmoEnum.RED.ordinal()] < ammos[AmmoEnum.RED.ordinal()] ||
                this.ammos[AmmoEnum.YELLOW.ordinal()] < ammos[AmmoEnum.YELLOW.ordinal()]) {
            throw new NotEnoughAmmosException();
        }
        this.ammos[AmmoEnum.BLUE.ordinal()] -= ammos[AmmoEnum.BLUE.ordinal()];
        this.ammos[AmmoEnum.RED.ordinal()] -= ammos[AmmoEnum.RED.ordinal()];
        this.ammos[AmmoEnum.YELLOW.ordinal()] -= ammos[AmmoEnum.YELLOW.ordinal()];
    }

    public ArrayList<PowerUpCard> getPowerUps() {
        return powerUps;
    }
}


