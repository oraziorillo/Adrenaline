package model;


public class AmmoCard {
    /**
     * Index this array with AmmoEnum
     */
    private final short[] ammos;
    private final boolean hasPowerup;
    public static final short AMMOSFORCARD = 3;

    public short[] getAmmos() {
        return ammos;
    }

    public boolean containsPowerup() {
        return hasPowerup;
    }

    /**
     * Constructor
     *
     * @param ammos      every field must be non-negative. Sum must be 3 if hasPowerup==false, 2 otherwise
     * @param hasPowerup for 2ammos-1powerup cards
     */
    public AmmoCard(short[] ammos, boolean hasPowerup) {
        if (!validParameters(ammos, hasPowerup)) {
            throw new IllegalArgumentException("illegal AmmoCard");
        }
        this.ammos = ammos;
        this.hasPowerup = hasPowerup;
    }

    /**
     * Checks if a card of given parameters could exist
     *
     * @param ammos      an array of ammonitions
     * @param hasPowerup true iif the card has a powerup inside
     * @return false if some value of ammos is negative, or if the sum of all the elements of ammos (+1 if hasPowerup=true) is not equal to AMMOSFORCARD. true otherwise.
     */

    public static boolean validParameters(short[] ammos, boolean hasPowerup) {
        short t = 0;
        //Save in t the sum of the elements in ammos
        for (short s : ammos) {
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
        return t == AMMOSFORCARD;
    }
}


enum AmmoEnum {
    BLUE,
    RED,
    YELLOW;
}
