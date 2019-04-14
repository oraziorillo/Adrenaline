package model;

import model.Enumerations.AmmoEnum;
import model.Enumerations.PcColourEnum;
import model.Enumerations.TileColourEnum;
import model.Exceptions.NotEnoughAmmosException;

import java.util.HashSet;

public class Pc {

    private final Game currGame;
    private final String name;
    private final PcColourEnum colour;
    private short points;
    private short numOfDeath;
    private short[] marks;
    private short[] ammos;
    private PcColourEnum[] damageTrack;
    private WeaponCard[] weapons;
    private HashSet<PowerUpCard> powerUps;
    private Tile currTile;


    public Pc(String name, PcColourEnum colour, Game game) {
        this.currGame = game;
        this.name = name;
        this.colour = colour;
        this.points = 0;
        this.numOfDeath = 0;
        this.marks = new short[5];
        this.ammos = new short[3];
        this.damageTrack = new PcColourEnum[12];
        this.weapons = new WeaponCard[3];
        this.powerUps = new HashSet<>();
        this.currTile = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
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

    public Game getCurrGame(){
        return currGame;
    }

    public String getName() {
        return name;
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

    //cambiare questo metodo in modo che riceva x,y della tile di destinazione è molto più comodo (fermo restando che si abbia l'istanza di game)
    public void move(int x,int y,int maxDist) {
        Tile targetTile=currGame.map[x][y];
        if(!currTile.atDistance(maxDist).contains(targetTile)){
            throw new IllegalArgumentException("Too far away");
        }
        currTile.removePc(this);
        targetTile.addPc(this);
        this.currTile =targetTile;
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

        AmmoCard card = workingTile.drawCard();
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
        short totaldamage = (short) (marks[colour.ordinal()] + damage);
        while (damageTrack[index] != null) {
            index = index + 1;
        }
        while (totaldamage != 0) {       //il controller dovrà controllare ogni volta se il giocatore è morto, verificando che all'indice 10 il valore sia != null
            damageTrack[index] = colour;
            if (index > 10) {
                totaldamage = 0;
            } else {
                index += 1;
            }
        }
    }

    public void takeMarks(short marks) {
        this.marks[colour.ordinal()] = marks;
    }

    public void increasePoints(int n) {
        this.points += n;
    }

    public void respawn(TileColourEnum colour) {
        this.damageTrack = new PcColourEnum[12];
        numOfDeath = (short) (numOfDeath + 1);
        //TODO: è dato dal colour che viene passato come parametro
        //this.currTile = getGenerationTile(colour);
    }

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
}


