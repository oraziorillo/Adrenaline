package model;

public class AmmoCard {
    /**
     * Index this array with AmmoEnum
     */
    private short[] ammos;
    private boolean hasPowerup;
    /**
     * Standard getter
     * @return ammos
     */
    public short[] getAmmos() {
        return ammos;
    }
    /**
     * standard getter
     * @return hasPowerup
     */
    public boolean containsPowerup(){
        return hasPowerup;
    }
    /**
     * Constructor
     * @param ammos every field must be non-negative. Sum must be 3 if hasPowerup==false, 2 else
     * @param hasPowerup for 2ammos-1powerup cards
     */
    public AmmoCard(short[] ammos,boolean hasPowerup){
        if(!validParameters(ammos,hasPowerup)){
            throw new IllegalArgumentException("illegal AmmoCard");
        }
        this.ammos=ammos;
        this.hasPowerup=hasPowerup;
    }
    public static boolean validParameters(short[] ammos, boolean hasPowerup){
        short t=0;
        for(short s: ammos){
            if(s<0){
                return false;
            }
            t+=s;
        }
        return ((hasPowerup&&t==2)||(t==3));
    }
}
