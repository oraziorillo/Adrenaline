package server.model;

import common.Constants;

import java.util.Arrays;

/**
 * Represents the ammo tiles
 */
public class AmmoTile {

    private final short[] ammo;
    private final boolean hasPowerUp;

    /**
     * Constructor
     *
     * @param ammo every field must be non-negative. Sum must be 3 if hasPowerUp==false, 2 otherwise
     * @param hasPowerUp for 2ammo-1powerUp cards
     */
    AmmoTile(short[] ammo, boolean hasPowerUp) {
        if (!validParameters(ammo, hasPowerUp)) {
            throw new IllegalArgumentException("Illegal AmmoTile");
        }
        this.ammo = ammo;
        this.hasPowerUp = hasPowerUp;
    }

    public short[] getAmmo() {
        return ammo.clone();
    }

    public boolean containsPowerUp() {
        return hasPowerUp;
    }

    /**
     * Checks if a card of given parameters could exist
     *
     * @param ammo       an array of ammunition
     * @param hasPowerUp true iif the card has a powerUp inside
     * @return false if some value of ammo is negative,
     *         or if the sum of all the elements of ammo (+1 if hasPowerUp=true) is not equal to AMMO_COLOURS_NUMBER.
     *         true otherwise.
     */

    static boolean validParameters(short[] ammo, boolean hasPowerUp) {
        short t = 0;
        //Save in t the sum of the elements in ammo
        for (short s : ammo) {
            //if any element is negative the parameters are invalid
            if (s < 0) {
                return false;
            }
            t += s;
        }
        //powerup counts as an ammo
        if (hasPowerUp) {
            t++;
        }
        return t == Constants.AMMO_COLOURS_NUMBER;
    }

    @Override
    public boolean equals(Object obj) {
        AmmoTile casted = (AmmoTile) obj;
        return Arrays.equals(this.ammo, casted.ammo) && (this.hasPowerUp == casted.hasPowerUp);
    }
}


