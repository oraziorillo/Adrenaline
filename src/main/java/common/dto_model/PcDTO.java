package common.dto_model;

import common.enums.PcColourEnum;

import java.util.ArrayList;

public class PcDTO extends DTO{

    private PcColourEnum colour;
    private PcBoardDTO pcBoard;
    private WeaponCardDTO[] weapons;
    private PowerUpCardDTO[] powerUps;
    private SquareDTO currSquare;
    private short adrenaline;

    public PcColourEnum getColour() {
        return colour;
    }
    
    public void setColour(PcColourEnum colour) {
        this.colour = colour;
    }
    
    public PcBoardDTO getPcBoard() {
        return pcBoard;
    }

    public void setPcBoard(PcBoardDTO pcBoard) {
        this.pcBoard = pcBoard;
    }

    public WeaponCardDTO[] getWeapons() {
        return weapons;
    }

    public void setWeapons(WeaponCardDTO[] weapons) {
        this.weapons = weapons;
    }

    public PowerUpCardDTO[] getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(PowerUpCardDTO[] powerUps) {
        this.powerUps = powerUps;
    }

    public SquareDTO getCurrSquare() {
        return currSquare;
    }

    public void setCurrSquare(SquareDTO currSquare) {
        this.currSquare = currSquare;
    }

    public short getAdrenaline() {
        return adrenaline;
    }

    public void setAdrenaline(short adrenaline) {
        this.adrenaline = adrenaline;
    }

    public String getName() {
        return colour.getName();
    }
}
