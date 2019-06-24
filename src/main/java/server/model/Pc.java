package server.model;

import common.dto_model.PcDTO;
import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;
import server.exceptions.EmptySquareException;
import server.exceptions.NotEnoughAmmoException;
import server.model.squares.Square;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;

/**
 * Represents a player in-game
 */
public class Pc {

    private ModelMapper modelMapper = new CustomizedModelMapper().getModelMapper();

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

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


    public boolean hasMaxPowerUpNumber() {
        return powerUps.size() == MAX_POWER_UPS_IN_HAND;
    }


    public String getName() {
        return colour.getName();
    }


    public PcColourEnum getColour() {
        return colour;
    }


    public WeaponCard[] getWeapons() {
        return weapons;
    }


    public PcColourEnum[] getDamageTrack() {
        return pcBoard.getDamageTrack();
    }


    public int getAdrenaline() {
        return adrenaline;
    }


    public short[] getAmmo() {
        return this.pcBoard.getAmmo();
    }


    public PcBoard getPcBoard() {
        return pcBoard;
    }


    public void increasePoints(int earnedPoints){
        pcBoard.increasePoints(earnedPoints);
    }


    public void flipBoard(){
        pcBoard.flipBoard();
    }


    public Square getCurrSquare() {
        return this.currSquare;
    }


    public PowerUpCard getPowerUpCard(int index) {
        if (index < 0 || index > powerUps.size() - 1) {
            throw new ArrayIndexOutOfBoundsException("This index is not valid");
        }
        return powerUps.get(index);
    }

    public List<PowerUpCard> getPowerUps() {
        return powerUps;
    }


    public WeaponCard weaponAtIndex(int index) {
        if (index < 0 || index > MAX_WEAPONS_IN_HAND) {
            throw new ArrayIndexOutOfBoundsException("This index is not valid");
        }
        return weapons[index];
    }
    
    /**
     * Removes this from the square it's currently on, puts this on s
     * @param s the new square
     */
    public void moveTo(Square s) {

        PcDTO old = modelMapper.map(this, PcDTO.class);

        if (s == null) {
            throw new IllegalArgumentException("Invalid square");
        }

        this.currSquare.removePc(this);
        this.currSquare = s;
        this.currSquare.addPc(this);

        //notify listeners
        changes.firePropertyChange(MOVE_TO, old, modelMapper.map(this, PcDTO.class));
    }


    public void drawPowerUp(){

        PcDTO old = modelMapper.map(this, PcDTO.class);

        PowerUpCard powerUpToDraw = currGame.drawPowerUp();
        if (powerUpToDraw != null)
            powerUps.add(powerUpToDraw);

        //notify listeners
        //notify listeners
        changes.firePropertyChange(DRAW_POWER_UP, old, modelMapper.map(this, PcDTO.class));
    }


    public void discardPowerUp(PowerUpCard p) {

        PcDTO old = modelMapper.map(this, PcDTO.class);

        int oldIndex;
        if (powerUps.contains(p)) {
             oldIndex = powerUps.indexOf(p);
            powerUps.remove(p);
        } else {
            throw new IllegalArgumentException("You don't have this powerUp");
        }

        //notify listeners
        changes.firePropertyChange(DISCARD_POWER_UP, old, modelMapper.map(this, PcDTO.class));
    }
    
    /**
     * Calls the collect method of the current square on this
     * @throws EmptySquareException if the targetSquare is Empty
     * @throws NotEnoughAmmoException if the target pc doesn't have enough to pick a particular weapon
     * @param targetSquare square on which pc has to collect
     */
    public void collect(Square targetSquare) throws EmptySquareException, NotEnoughAmmoException {
        targetSquare.collect(this);
    }


    public void addAmmo(AmmoTile ammo) {
        pcBoard.addAmmo(ammo);
        if (ammo.containsPowerUp() && powerUps.size() < MAX_POWER_UPS_IN_HAND)
            drawPowerUp();
    }

    public void resetPowerUpAsAmmo (){
        for (PowerUpCard p: powerUps)
            p.setSelectedAsAmmo(false);
    }

    public void addWeapon(WeaponCard weapon, int index) {
        if (index != -1)
            weapons[index] = weapon;
        else {
            for(int i = 0; i < MAX_WEAPONS_IN_HAND; i++){
                if (weapons[i] == null){
                    weapons[i] = weapon;
                    break;
                }
            }
        }
    }


    public void takeMarks(PcColourEnum shooterColour, short marks) {
        if (this.colour == shooterColour)
            return;
        pcBoard.addMarks(shooterColour, marks);
    }


    public void takeDamage(PcColourEnum shooterColour, short damages) {

        PcDTO old = modelMapper.map(this, PcDTO.class);

        if (this.colour == shooterColour)
            return;
        short totalDamage;
        totalDamage = (short) (pcBoard.getMarks(shooterColour) + damages);
        pcBoard.addDamage(shooterColour, totalDamage);
        int damageIndex = pcBoard.getDamageTrackIndex();
        if (damageIndex >= LIFE_POINTS - 2) {
            boolean overkill = damageIndex == (LIFE_POINTS - 1);
            currGame.killOccurred(this.colour, overkill);

            //notify death
            changes.firePropertyChange(KILL_OCCURRED, old, modelMapper.map(this, PcDTO.class));
        }

        boolean adrenalineUp = false;

        if (damageIndex > 4 && adrenaline < 2) {
            adrenaline = 2;
            adrenalineUp = true;
        } else if (damageIndex > 1 && adrenaline < 1) {
            adrenaline = 1;
            adrenalineUp = true;
        }

        if (adrenalineUp)
            //notify adrenaline up
            changes.firePropertyChange(ADRENALINE_UP, old, modelMapper.map(this, PcDTO.class));
    }


    public void spawn(Square t) {

        PcDTO old = modelMapper.map(this, PcDTO.class);

        if (!t.isSpawnPoint()) {
            throw new IllegalArgumentException("Not a spawn Square");
        }
        pcBoard.resetDamageTrack();
        this.adrenaline = 0;
        this.currSquare = t;
        currSquare.addPc(this);

        //notify listeners
        changes.firePropertyChange(SPAWN, old, modelMapper.map(this, PcDTO.class));
    }


    public boolean hasAtLeastOneAvailableAmmo() {
        return pcBoard.hasAtLeastOneAmmo() || powerUps.size() > 1;
    }


    public boolean hasEnoughAmmo(short[] ammo) {
        short[] pcAmmo = pcBoard.getAmmo().clone();
        powerUps.stream().filter(PowerUpCard::isSelectedAsAmmo).forEach(p -> pcAmmo[p.getColour().ordinal()]++);
        for (AmmoEnum ammoColour : AmmoEnum.values()) {
            if (pcAmmo[ammoColour.ordinal()] < ammo[ammoColour.ordinal()])
                return false;
        }
        return true;
    }


    public boolean payAmmo(short[] cost) {
        if (!hasEnoughAmmo(cost))
            return false;
        short[] remainingCost = pcBoard.payAmmo(cost);
        List<PowerUpCard> powerUpToDiscard = new ArrayList<>();
        powerUps.forEach(p -> {
            if (p.isSelectedAsAmmo() && remainingCost[p.getColour().ordinal()] > 0) {
                remainingCost[p.getColour().ordinal()]--;
                powerUpToDiscard.add(p);
            }
        });
        powerUps.removeAll(powerUpToDiscard);
        return true;
    }


    void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
        pcBoard.addPropertyChangeListener(listener);
    }
}



