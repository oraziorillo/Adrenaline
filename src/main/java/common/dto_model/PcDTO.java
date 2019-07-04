package common.dto_model;

import common.enums.PcColourEnum;
import java.util.ArrayList;

public class PcDTO implements DTO {

    private PcColourEnum colour;
    private PcBoardDTO pcBoard;
    private WeaponCardDTO[] weapons;
    private ArrayList<PowerUpCardDTO> powerUps;
    private short adrenaline;
    private int squareRow;
    private int squareCol;


    public String getName() {
        return pcBoard.getColour().getName();
    }

    public PcBoardDTO getPcBoard() {
        return pcBoard;
    }

    public PcColourEnum getColour() {
        return colour;
    }

    public void setColour(PcColourEnum colour) {
        this.colour = colour;
    }

    public int getSquareRow() {
        return squareRow;
    }

    public void setSquareRow(int squareRow) {
        this.squareRow = squareRow;
    }

    public int getSquareCol() {
        return squareCol;
    }

    public void setSquareCol(int squareCol) {
        this.squareCol = squareCol;
    }

    public void setPcBoard(PcBoardDTO pcBoard) {
        this.pcBoard = pcBoard;
    }

    public WeaponCardDTO[] getWeapons() {
        return weapons;
    }


    public ArrayList<PowerUpCardDTO> getPowerUps() {
        return powerUps;
    }


    public short getAdrenaline() {
        return adrenaline;
    }

    public void setAdrenaline(short adrenaline) {
        this.adrenaline = adrenaline;
    }

    public void setWeapons(WeaponCardDTO[] weapons) {
        this.weapons = weapons;
    }

    public void setPowerUps(ArrayList<PowerUpCardDTO> powerUps) {
        this.powerUps = powerUps;
    }

    public String squareToString() {
        return "(" + squareRow + "," + squareCol + ")";
    }

    public String powerUpsToString() {
        StringBuilder powerUpsString = new StringBuilder("\nYour power ups:");
        int i = 0;
        for (PowerUpCardDTO p : this.powerUps) {
            powerUpsString.append("\n[").append(i + 1).append("]").append(" ")
                    .append(p.getColour()).append(p.getColour().getTabs()).append(p.getName());
            i++;
        }
        return powerUpsString.toString();
    }
}
