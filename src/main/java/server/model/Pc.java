package server.model;

import common.dto_model.PcBoardDTO;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.SquareDTO;
import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import common.events.*;
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

        //notify listeners
        events.fireEvent(new PointsIncreasedEvents(modelMapper.map(this, PcDTO.class), earnedPoints));
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

        SquareDTO oldPos = modelMapper.map(currSquare, SquareDTO.class);

        if (s == null) {
            throw new IllegalArgumentException("Invalid square");
        }

        this.currSquare.removePc(this);
        this.currSquare = s;
        this.currSquare.addPc(this);

        //notify listeners
        SquareDTO newPos = modelMapper.map(currSquare, SquareDTO.class);
        events.fireEvent(new MovementEvent(colour.getName(), oldPos, newPos));
    }


    public void drawPowerUp(){

        PowerUpCard powerUpToDraw = currGame.drawPowerUp();
        if (powerUpToDraw != null)
            powerUps.add(powerUpToDraw);

        //TODO in questo caso non solo l'evento è privato ma bisogna mandare la carta al solo utente che l'ha pescata
        //notify listeners
        //events.firePropertyChange(POWER_UP_DROWN, old, modelMapper.map(this, PcDTO.class));
    }


    public void discardPowerUp(PowerUpCard p) {
        //TODO index è inutilizzato: serviva a qualcosa?
        int oldIndex;
        if (powerUps.contains(p)) {
             oldIndex = powerUps.indexOf(p);
            powerUps.remove(p);
        } else {
            throw new IllegalArgumentException("You don't have this powerUp");
        }

        //notify listeners
        events.fireEvent(new PowerUpDiscardedEvent(modelMapper.map(p, PowerUpCardDTO.class), this.getName()));
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
        events.fireEvent(new AmmoChangedEvent(modelMapper.map(this, PcDTO.class),
                ammoTile.getAmmo(), null, true));

        if (ammoTile.containsPowerUp() && powerUps.size() < MAX_POWER_UPS_IN_HAND)
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
        events.fireEvent(new AmmoChangedEvent(modelMapper.map(this, PcDTO.class),
                ammoPaid, powerUpsDiscarded, false));

        return true;
    }


    public void takeMarks(PcColourEnum shooterColour, short marks) {
        if (this.colour == shooterColour)
            return;
        pcBoard.addMarks(shooterColour, marks);

        //notify listeners
        events.fireEvent(new MarksTakenEvent(modelMapper.map(this, PcDTO.class), shooterColour.getName(), marks));
    }


    public void takeDamage(PcColourEnum shooterColour, short damages) {

        if (this.colour == shooterColour)
            return;
        short totalDamage;
        totalDamage = (short) (pcBoard.getMarks(shooterColour) + damages);
        pcBoard.addDamage(shooterColour, totalDamage);

        //notify damage taken
        events.fireEvent(new DamageTakenEvent(modelMapper.map(this, PcDTO.class), shooterColour.getName(), damages));

        int damageIndex = pcBoard.getDamageTrackIndex();
        if (damageIndex >= LIFE_POINTS - 2) {
            boolean overkill = damageIndex == (LIFE_POINTS - 1);
            currGame.killOccurred(this.colour, overkill);

            //notify death
            events.fireEvent(new DeathEvent(modelMapper.map(this, PcDTO.class)));
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


    public void spawn(Square t) {

        if (!t.isSpawnPoint()) {
            throw new IllegalArgumentException("Not a spawn Square");
        }
        pcBoard.resetDamageTrack();
        this.adrenaline = 0;
        this.currSquare = t;
        currSquare.addPc(this);

        //notify listeners
        events.fireEvent(new SpawnEvent(getName(), modelMapper.map(t, SquareDTO.class)));
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
        pcBoard.addModelEventHandler(events);
    }


    @Override
    public String toString() {
        return this.colour.getName();
    }


    void increaseNumberOfDeaths() {
        pcBoard.increaseNumberOfDeaths();

        //notify listeners
        events.fireEvent(new NumberOfDeathIncreasedEvent(modelMapper.map(pcBoard, PcBoardDTO.class)));
    }
}



