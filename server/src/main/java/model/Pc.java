package model;

import enums.AmmoEnum;
import enums.PcColourEnum;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.squares.Square;
import java.util.ArrayList;
import java.util.List;

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
        if (powerUps.get(index) == null)
            throw new NullPointerException("You have to select a powerUp!");
        return powerUps.get(index);
    }

    public List<PowerUpCard> getPowerUps() {
        return powerUps;
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


    public void collect() throws EmptySquareException, NotEnoughAmmoException {
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


    public boolean hasAtLeastOneAmmo(){
        return pcBoard.hasAtLeastOneAmmo() || powerUps.size() > 1;
    }


    public boolean hasEnoughAmmo(short[] ammo) {
        short [] pcAmmo = pcBoard.getAmmo();
        powerUps.stream().filter(PowerUpCard::isSelectedAsAmmo).forEach(p -> pcAmmo[p.getColour().ordinal()]++);
        for(AmmoEnum colour : AmmoEnum.values()){
            if(pcAmmo[colour.ordinal()] < ammo[colour.ordinal()])
                return false;
        }
        return true;
    }

    //potremmo anche rendere payAmmo un boolean che restituisce True se può effettuare il pagamento, false altrimenti
    public void payAmmo(short[] cost) throws NotEnoughAmmoException {
        if (!hasEnoughAmmo(cost)) {
            throw new NotEnoughAmmoException();
        }
        short [] remainingCost = pcBoard.payAmmo(cost);
        powerUps.forEach(p -> {
            if (p.isSelectedAsAmmo() && remainingCost[p.getColour().ordinal()] > 0) {
                remainingCost[p.getColour().ordinal()]--;
                discardPowerUp(p);
            }
        });
    }
}


