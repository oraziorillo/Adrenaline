package common.dto_model;

import server.model.AmmoTile;

public class AmmoTileDTO {

    private short[] ammos;
    private boolean hasPowerUp;
    protected String extension = ".png";

    AmmoTileDTO(AmmoTile ammoTile){
        this.ammos = ammoTile.getAmmo();
        this.hasPowerUp = ammoTile.containsPowerUp();
    }

    public String getImagePath() {
        //TODO
        return "Ciao";
    }

    public short[] getAmmos() {
        return ammos;
    }



    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public boolean hasPowerUp() {
        return hasPowerUp;
    }
}
