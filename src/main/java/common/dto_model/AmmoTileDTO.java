package common.dto_model;

import server.model.AmmoTile;

public class AmmoTileDTO {

    private short[] ammo;
    private boolean hasPowerUp;
    protected String extension = ".png";

    AmmoTileDTO(AmmoTile ammoTile){
        this.ammo = ammoTile.getAmmo();
        this.hasPowerUp = ammoTile.containsPowerUp();
    }

    public String getImagePath() {
        //TODO
        return "Ciao";
    }

    public short[] getAmmo() {
        return ammo;
    }



    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    public void setAmmo(short[] ammo) {
        this.ammo = ammo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
