package server.model;

import com.google.gson.annotations.Expose;
import common.dto_model.AmmoTileDTO;

/**
 * Represents the ammo tiles
 */
public class AmmoTile {
    @Expose private short[] ammo;
    @Expose private boolean hasPowerUp;

    /**
     * Constructor
     *
     * @param ammo every field must be non-negative. Sum must be 3 if hasPowerUp==false, 2 otherwise
     * @param hasPowerUp for 2ammo-1powerUp cards
     */
    public AmmoTile(short[] ammo, boolean hasPowerUp) {
        this.ammo = ammo;
        this.hasPowerUp = hasPowerUp;
    }


    public short[] getAmmo() {
        return ammo.clone();
    }


    public boolean hasPowerUp() {
        return hasPowerUp;
    }


    public AmmoTileDTO convertToDTO(){
        AmmoTileDTO ammoTileDTO = new AmmoTileDTO();
        ammoTileDTO.setHasPowerUp(hasPowerUp);
        ammoTileDTO.setAmmo(ammo);
        return ammoTileDTO;
    }
}


