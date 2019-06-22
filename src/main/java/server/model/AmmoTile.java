package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.Arrays;

/**
 * Represents the ammo tiles
 */
public class AmmoTile {

    private short[] ammo;
    private boolean hasPowerup;

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

    public boolean containsPowerup() {
        return hasPowerup;
    }

    /**
     * given the number of ammos, it sets the boolean in the right way according to the rules
     */
    public void setHasPowerUp() {
        short sum = 0;
        for (short s : ammo) {
            if (s > 2)
                throw new IllegalArgumentException();
            sum += s;
        }
        if (sum == 3)
            this.hasPowerup = false;
        else if (sum == 2)
            this.hasPowerup = true;
        else
            throw new IllegalArgumentException();
    }
    
    @Override
    public boolean equals(Object obj) {
        AmmoTile casted = (AmmoTile) obj;
        return Arrays.equals( this.ammo,casted.ammo ) && (this.hasPowerup==casted.hasPowerup);
    }
}


