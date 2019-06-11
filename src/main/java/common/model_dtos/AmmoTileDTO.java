package common.model_dtos;

import server.model.AmmoTile;

public class AmmoTileDTO {

    private short[] ammos;
    private boolean hasPowerUp;
    protected String extension = ".png";

    AmmoTileDTO(AmmoTile ammoTile){
        this.ammos = ammoTile.getAmmo();
        this.hasPowerUp = ammoTile.containsPowerup();
    }

    public String getImagePath() {
        //TODO
    }

    public short[] getAmmos() {
        return ammos;
    }


    public boolean hasPowerUp() {
        return hasPowerUp;
    }
}
