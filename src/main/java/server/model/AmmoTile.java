package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * Represents the ammo tiles
 */
public class AmmoTile {
    @Expose private short[] ammo;
    private boolean hasPowerUp;

    /**
     * Constructor
     *
     * @param ammo every field must be non-negative. Sum must be 3 if hasPowerUp==false, 2 otherwise
     * @param hasPowerUp for 2ammo-1powerUp cards
     */
    AmmoTile(short[] ammo, boolean hasPowerUp) {
        this.ammo = ammo;
        this.hasPowerUp = hasPowerUp;
    }

    /**
     * Constructor initializing the AmmoTile reading from Json
     * @param jsonAmmoTile contains the specific ammos for the tile
     */
    AmmoTile(JsonObject jsonAmmoTile) {
        this.ammo = new short[3];
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        this.ammo = gson.fromJson(jsonAmmoTile.get("ammo"), short[].class);
    }


    public short[] getAmmo() {
        return ammo.clone();
    }

    public boolean containsPowerUp() {
        return hasPowerUp;
    }


    void setHasPowerUp() {
        short sum = 0;
        for (short s : ammo) {
            if (s > 2)
                throw new IllegalArgumentException();
            sum += s;
        }
        if (sum == 3)
            hasPowerUp = false;
        else if (sum == 2)
            hasPowerUp = true;
        else
            throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object obj) {
        AmmoTile casted = (AmmoTile) obj;
        return Arrays.equals(this.ammo, casted.ammo) && (this.hasPowerUp == casted.hasPowerUp);
    }

}


