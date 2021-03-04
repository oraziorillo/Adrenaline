package common.dto_model;

import common.enums.AmmoEnum;
import common.enums.PcColourEnum;

import java.util.ArrayList;

import static common.Constants.MAX_POWER_UPS_IN_HAND;
import static common.Constants.MAX_WEAPONS_IN_HAND;

public class PcDTO implements DTO {

    private boolean censored;
    private PcColourEnum colour;
    private PcBoardDTO pcBoard;
    private WeaponCardDTO[] weapons;
    private ArrayList<PowerUpCardDTO> powerUps;
    private short adrenaline;
    private int squareRow;
    private int squareCol;

    public boolean isCensored() {
        return censored;
    }

    public void setCensored(boolean censored) {
        this.censored = censored;
    }

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

    public String weaponsToString() {
        StringBuilder weapons = new StringBuilder(System.lineSeparator()).append("Your weapons:");
        int i = 0;
        for (WeaponCardDTO w : this.weapons) {
            if (w != null)
                weapons.append(System.lineSeparator()).append("[").append(i + 1).append("]").append(w.toString()).append(" (loaded = ").append(w.isLoaded()).append(")");
            i++;
        }
        return weapons.toString();
    }

    public String ammoToString() {
        StringBuilder ammo = new StringBuilder(System.lineSeparator()).append("Your ammo:");
        for (AmmoEnum a : AmmoEnum.values())
            ammo.append(System.lineSeparator()).append("• ").append(pcBoard.getAmmo()[a.ordinal()]).append(" ").append(a.toString()).append(" ammo");
        return ammo.toString();
    }

    public String powerUpsToString() {
        StringBuilder powerUpsString = new StringBuilder(System.lineSeparator()).append("Your power ups:");
        int i = 0;
        for (PowerUpCardDTO p : this.powerUps) {
            powerUpsString.append(System.lineSeparator()).append("[").append(i + 1).append("]").append(" ")
                    .append(p.getColour()).append(p.getColour().getTabs()).append(p.getName());
            i++;
        }
        return powerUpsString.toString();
    }

    public String getPcStatus(){
        return System.lineSeparator() + "Your character: " + this.colour.getName() +
                System.lineSeparator() + "Your position: (" + this.squareRow + "," + this.squareCol + ")" +
                weaponsToString() + ammoToString() + powerUpsToString() +
                pcBoard.toString();
    }

    public PcDTO getCensoredDTO() {
        PcDTO censoredPcDTO = new PcDTO();

        WeaponCardDTO[] censoredWeapons = new WeaponCardDTO[MAX_WEAPONS_IN_HAND];
        WeaponCardDTO tmp;
        for (int i = 0; i < MAX_WEAPONS_IN_HAND; i++) {
            tmp = weapons[i];
            if (tmp != null && tmp.isLoaded())
                censoredWeapons[i] = WeaponCardDTO.getCardBack();
            else if (tmp != null)
                censoredWeapons[i] = tmp;
        }
        censoredPcDTO.setWeapons(censoredWeapons);

        ArrayList<PowerUpCardDTO> censoredPowerUps = new ArrayList<>();
        for (int i = 0; i < MAX_POWER_UPS_IN_HAND; i++) {
            if (i < powerUps.size())
                censoredPowerUps.add(PowerUpCardDTO.getCardBack());
        }
        censoredPcDTO.setPowerUps(censoredPowerUps);

        censoredPcDTO.setCensored(true);
        censoredPcDTO.setColour(colour);
        censoredPcDTO.setSquareRow(squareRow);
        censoredPcDTO.setSquareCol(squareCol);
        censoredPcDTO.setAdrenaline(adrenaline);
        censoredPcDTO.setPcBoard(pcBoard);
        return censoredPcDTO;
    }

}
