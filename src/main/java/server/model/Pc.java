package server.model;

import com.google.gson.annotations.Expose;
import common.dto_model.PcBoardDTO;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import common.events.pc_board_events.*;
import common.events.pc_events.*;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;
import server.exceptions.EmptySquareException;
import server.exceptions.NotEnoughAmmoException;
import server.model.squares.Square;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;

/**
 * Represents a player in-game
 */
public class Pc {

    private ModelMapper modelMapper = new CustomizedModelMapper().getModelMapper();

    private ModelEventHandler events = new ModelEventHandler();


    private final Game currGame;
    private PcColourEnum colour;
    @Expose private PcBoard pcBoard;
    @Expose private short adrenaline;
    @Expose private WeaponCard[] weapons;
    @Expose private ArrayList<PowerUpCard> powerUps;
    @Expose private Square currSquare;

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
        events.fireEvent(new PointsIncreasedEvents(modelMapper.map(pcBoard, PcBoardDTO.class), earnedPoints));
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

        String oldPos = currSquare.toString();

        if (s == null) {
            throw new IllegalArgumentException("Invalid square");
        }

        this.currSquare.removePc(this);
        this.currSquare = s;
        this.currSquare.addPc(this);

        //notify listeners
        events.fireEvent(new MovementEvent(modelMapper.map(this, PcDTO.class), oldPos, currSquare.toString()));
    }


    public void drawPowerUp(){

        PowerUpCard powerUpToDraw = currGame.drawPowerUp();
        if (powerUpToDraw != null) {
            powerUps.add(powerUpToDraw);

            //notify power up drown
            events.fireEvent(new PowerUpDrownEvent(
                    modelMapper.map(this, PcDTO.class),
                    modelMapper.map(powerUpToDraw, PowerUpCardDTO.class)));
        }
    }


    public void discardPowerUp(PowerUpCard p) {
        if (powerUps.contains(p)) {
            powerUps.remove(p);
        } else {
            throw new IllegalArgumentException("You don't have this powerUp");
        }

        //notify listeners
        events.fireEvent(new PowerUpDiscardedEvent(
                modelMapper.map(this, PcDTO.class),
                modelMapper.map(p, PowerUpCardDTO.class)));
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
        events.fireEvent(new AmmoChangedEvent(modelMapper.map(pcBoard, PcBoardDTO.class),
                ammoTile.getAmmo(), null, true));

        if (ammoTile.hasPowerUp() && powerUps.size() < MAX_POWER_UPS_IN_HAND)
            drawPowerUp();
    }


    public boolean payAmmo(short[] cost) {
        if (!hasEnoughAmmo(cost))
            return false;
        short[] remainingCost = pcBoard.payAmmo(cost);

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
        events.fireEvent(new AmmoChangedEvent(modelMapper.map(pcBoard, PcBoardDTO.class),
                ammoPaid, powerUpsDiscarded, false));

        return true;
    }


    public void takeDamage(PcColourEnum shooterColour, short damages) {

        if (pcBoard.getColour() == shooterColour)
            return;
        short totalDamage;
        totalDamage = (short) (pcBoard.getMarks(shooterColour) + damages);
        pcBoard.addDamage(shooterColour, totalDamage);

        int damageIndex = pcBoard.getDamageTrackIndex();
        if (damageIndex >= LIFE_POINTS - 2) {
            boolean overkill = damageIndex == (LIFE_POINTS - 1);
            currGame.killOccurred(pcBoard.getColour(), overkill);

            //notify death
            events.fireEvent(new DeathEvent(modelMapper.map(pcBoard, PcBoardDTO.class)));
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
            events.fireEvent(new AdrenalineUpEvent(modelMapper.map(this, PcDTO.class)));
    }


    public void takeMarks(PcColourEnum shooterColour, short marks) {
        if (pcBoard.getColour() == shooterColour)
            return;
        pcBoard.addMarks(shooterColour, marks);
    }


    public void notifyDamageMarks(String shooterName, short damages, short marks){
        events.fireEvent(new DamageMarksTakenEvent(modelMapper.map(pcBoard, PcBoardDTO.class), shooterName, damages, marks));
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
        events.fireEvent(new SpawnEvent(modelMapper.map(this, PcDTO.class)));
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


    void increaseNumberOfDeaths() {
        pcBoard.increaseNumberOfDeaths();

        //notify listeners
        events.fireEvent(new NumberOfDeathIncreasedEvent(modelMapper.map(pcBoard, PcBoardDTO.class)));
    }
}



