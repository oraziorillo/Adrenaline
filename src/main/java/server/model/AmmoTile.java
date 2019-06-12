package server.model;

import java.util.Arrays;

/**
 * Represents the ammo tiles
 */
public class AmmoTile {

    private final short[] ammo;
    private final boolean hasPowerup;

    /**
     * Constructor
     * @param ammo every field must be non-negative. Sum must be 3 if hasPowerup==false, 2 otherwise
     * @param hasPowerup for 2ammos-1powerup cards
     */
    AmmoTile(short[] ammo, boolean hasPowerup) {
        if (!validParameters(ammo, hasPowerup)) {
            throw new IllegalArgumentException("Illegal AmmoTile");
        }
        this.ammo = ammo;
        this.hasPowerup = hasPowerup;
    }

    public short[] getAmmo() {
        return ammo.clone();
    }

    public boolean containsPowerup() {
        return hasPowerup;
    }

    /**
     * Checks if a card of given parameters could exist
     *
     * @param ammo an array of ammunition
     * @param hasPowerup true iif the card has a powerup inside
     * @return false if some value of ammo is negative, or if the sum of all the elements of ammo (+1 if hasPowerup=true) is not equal to AMMO_COLOURS_NUMBER. true otherwise.
     */

    static boolean validParameters(short[] ammo, boolean hasPowerup) {
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
        if (hasPowerup) {
            t++;
        }
        return t == Constants.AMMO_COLOURS_NUMBER;
    }
    
    @Override
    public boolean equals(Object obj) {
        AmmoTile casted = (AmmoTile) obj;
        return Arrays.equals( this.ammo,casted.ammo ) && (this.hasPowerup==casted.hasPowerup);
    }
}


