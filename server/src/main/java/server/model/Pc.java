package server.model;

import com.google.gson.annotations.Expose;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.WeaponCardDTO;
import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import common.events.pc_board_events.*;
import common.events.pc_events.*;
import common.exceptions.EmptySquareException;
import common.exceptions.NotEnoughAmmoException;
import server.model.squares.Square;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;

/**
 * Represents a player in-game
 */
public class Pc {

    private ModelEventHandler events = new ModelEventHandler();

    private Game currGame;
    @Expose private PcColourEnum colour;
    @Expose private PcBoard pcBoard;
    @Expose private short adrenaline;
    @Expose private WeaponCard[] weapons;
    @Expose private ArrayList<PowerUpCard> powerUps;
    private Square currSquare;


    public Pc(PcColourEnum colour, Game game) {
        this.currGame = game;
        this.colour = colour;
        this.pcBoard = new PcBoard(colour);
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


    public void setCurrGame(Game currGame) {
        this.currGame = currGame;
    }


    public WeaponCard[] getWeapons() {
        return weapons;
    }


    public PcColourEnum[] getDamageTrack() {
        return pcBoard.getDamageTrack();
    }


    public short getAdrenaline() {
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

        //notify listeners
        events.fireEvent(new PointsIncreasedEvents(pcBoard.convertToDTO(), earnedPoints));
    }


    public void flipBoard(){
        pcBoard.flipBoard();
    }


    public Square getCurrSquare() {
        return this.currSquare;
    }


    public void setCurrSquare(Square currSquare) {
        this.currSquare = currSquare;
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

        String oldPos = currSquare.toString();

        if (s == null) {
            throw new IllegalArgumentException("Invalid square");
        }

        this.currSquare.removePc(this);
        this.currSquare = s;
        this.currSquare.addPc(this);

        //notify listeners
        events.fireEvent(new MovementEvent(convertToDTO(), oldPos, currSquare.toString()));
    }


    public void drawPowerUp(int numOfPowerUps){

        PowerUpCardDTO[] powerUpsDTOs = new PowerUpCardDTO[numOfPowerUps];

        int temp = numOfPowerUps;
        while (temp != 0) {
            PowerUpCard powerUpToDraw = currGame.drawPowerUp();
            if (powerUpToDraw != null) {
                powerUps.add(powerUpToDraw);
                powerUpsDTOs[numOfPowerUps - temp] = powerUpToDraw.convertToDTO();
            }
            temp--;
        }

        //notify power up drown
        if (numOfPowerUps == 2)
            events.fireEvent(new PowerUpDrownEvent(this.convertToDTO(), powerUpsDTOs[0], powerUpsDTOs[1]));
        else
            events.fireEvent(new PowerUpDrownEvent(this.convertToDTO(), powerUpsDTOs[0], null));
    }


    public void discardPowerUp(PowerUpCard p) {
        if (powerUps.contains(p)) {
            powerUps.remove(p);
        } else {
            throw new IllegalArgumentException("You don't have this powerUp");
        }

        //notify listeners
        events.fireEvent(new PowerUpDiscardedEvent(convertToDTO(), p.convertToDTO()));
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


    public void addAmmo(AmmoTile ammoTile) {
        pcBoard.addAmmo(ammoTile);

        //notify ammo change
        events.fireEvent(new AmmoChangedEvent(pcBoard.convertToDTO(),
                ammoTile.getAmmo(), null, true));

        if (ammoTile.hasPowerUp() && powerUps.size() < MAX_POWER_UPS_IN_HAND)
            drawPowerUp(1);
    }


    public boolean payAmmo(short[] cost) {
        if (!hasEnoughAmmo(cost))
            return false;
        short[] remainingCost = pcBoard.payAmmo(cost.clone());

        //ammo that was paid
        short[] ammoPaid = new short[AMMO_COLOURS_NUMBER];
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++)
            ammoPaid[i] = (short) (cost[i] - remainingCost[i]);

        List<PowerUpCard> powerUpsToDiscard = new ArrayList<>();
        List<String> powerUpsDiscarded = new ArrayList<>();
        powerUps.forEach(p -> {
            if (p.isSelectedAsAmmo() && remainingCost[p.getColour().ordinal()] > 0) {
                remainingCost[p.getColour().ordinal()]--;
                powerUpsToDiscard.add(p);
                powerUpsDiscarded.add(p.getName());
            }
        });

        powerUps.removeAll(powerUpsToDiscard);

        //notify ammo payment
        events.fireEvent(new AmmoChangedEvent(pcBoard.convertToDTO(),
                ammoPaid, powerUpsDiscarded, false));

        return true;
    }


    public void takeDamage(PcColourEnum shooterColour, short damages) {

        if (pcBoard.getColour() == shooterColour)
            return;
        short totalDamage;
        totalDamage = (short) (pcBoard.getMarks(shooterColour) + damages);
        pcBoard.addDamage(shooterColour, totalDamage);

        events.notifyDamaged(colour);

        int damageIndex = pcBoard.getDamageTrackIndex();
        if (damageIndex >= LIFE_POINTS - 2) {
            boolean overkill = damageIndex == (LIFE_POINTS - 1);
            currGame.killOccurred(pcBoard.getColour(), overkill);

            //notify death
            events.fireEvent(new DeathEvent(pcBoard.convertToDTO()));
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
            events.fireEvent(new AdrenalineUpEvent(convertToDTO()));
    }


    public void takeMarks(PcColourEnum shooterColour, short marks) {
        if (pcBoard.getColour() == shooterColour)
            return;
        pcBoard.addMarks(shooterColour, marks);
    }


    public void notifyDamageMarks(String shooterName, short damages, short marks){
        events.fireEvent(new DamageMarksTakenEvent(pcBoard.convertToDTO(), shooterName, damages, marks));
    }


    public void spawn(Square t) {

        if (!t.isSpawnPoint()) {
            throw new IllegalArgumentException("Not a spawn Square");
        }
        pcBoard.resetDamageTrack();
        this.adrenaline = 0;
        this.currSquare = t;
        currSquare.addPc(this);

        //notify listeners
        events.fireEvent(new SpawnEvent(convertToDTO()));
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


    public void addModelEventHandler(ModelEventHandler events) {
        this.events = events;
    }
    
    
    @Override
    public String toString() {
        return pcBoard.getColour().getName();
    }


    public void increaseNumberOfDeaths() {
        pcBoard.increaseNumberOfDeaths();

        //notify listeners
        events.fireEvent(new NumberOfDeathIncreasedEvent(pcBoard.convertToDTO()));
    }


    public PcDTO convertToDTO(){
        PcDTO pcDTO = new PcDTO();
        pcDTO.setColour(colour);
        pcDTO.setPcBoard(pcBoard.convertToDTO());
        pcDTO.setAdrenaline(adrenaline);
        if (currSquare != null) {
            pcDTO.setSquareRow(currSquare.getRow());
            pcDTO.setSquareCol(currSquare.getCol());
        }
        pcDTO.setWeapons(convertWeaponsDTO());
        pcDTO.setPowerUps(convertPowerUpsDTO());
        return pcDTO;
    }


    private WeaponCardDTO[] convertWeaponsDTO(){
        WeaponCardDTO[] newWeaponCardsDTO = new WeaponCardDTO[MAX_WEAPONS_IN_HAND];
        for (int i = 0; i < MAX_WEAPONS_IN_HAND; i++) {
            if (weapons[i] != null) {
                newWeaponCardsDTO[i] = weapons[i].convertToDTO();
            }
        }
        return newWeaponCardsDTO;
    }


    private ArrayList<PowerUpCardDTO> convertPowerUpsDTO(){
        ArrayList<PowerUpCardDTO> powerUpCardDTOs = new ArrayList<>();
        for (PowerUpCard powerUp: powerUps) {
            if (powerUp != null) {
                powerUpCardDTOs.add(powerUp.convertToDTO());
            }
        }
        return powerUpCardDTOs;
    }

}



