package common.dto_model;

import common.enums.PcColourEnum;

public class  PcDTO implements DTO {

    private PcBoardDTO pcBoard;
    private WeaponCardDTO[] weapons;
    private PowerUpCardDTO[] powerUps;
    private SquareDTO currSquare;
    private short adrenaline;

    public PcColourEnum getColour() {
        return pcBoard.getColour();
    }

    public void setColour(PcColourEnum colour) {
        pcBoard.setColour(colour);
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
        return pcBoard.getColour().getName();
    }
}
