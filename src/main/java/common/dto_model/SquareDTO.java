package common.dto_model;

import common.enums.PcColourEnum;

import java.util.Set;

public class SquareDTO {

    private int row;
    private int col;
    private boolean targetable;
    public Set<PcColourEnum> pcs;
    public AmmoTileDTO ammoTile;
    public WeaponCardDTO[] weapons;

    public WeaponCardDTO[] getWeapons() {
        return weapons;
    }

    public void setWeapons(WeaponCardDTO[] weapons) {
        this.weapons = weapons;
    }


    public AmmoTileDTO getAmmoTile() {
        return ammoTile;
    }

    public void setAmmoTile(AmmoTileDTO ammoTile) {
        this.ammoTile = ammoTile;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isTargetable() {
        return targetable;
    }

    public void setTargetable(boolean targetable) {
        this.targetable = targetable;
    }

    public Set<PcColourEnum> getPcs() {
        return pcs;
    }

    public void setPcs(Set<PcColourEnum> pcs) {
        this.pcs = pcs;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  SquareDTO){
            SquareDTO s = (SquareDTO)obj;
            return s.col == this.col && s.row == this.row;
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
