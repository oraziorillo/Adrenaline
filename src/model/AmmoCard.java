package model;

public class AmmoCard {
    private final short[] ammos;
    private final boolean hasPowerup;
    private final AmmoEnum colour;

    public short[] getAmmos() {
        return ammos;
    }
    public boolean containsPowerup(){
        return hasPowerup;
    }

    public AmmoEnum getColour() {
        return colour;
    }

    public AmmoCard(AmmoEnum colour,short[] ammos){
        this.colour=colour;
        this.ammos=ammos;
        this.hasPowerup=false;
    }
    public AmmoCard(AmmoEnum colour,short[] ammos,Powerup powerup){
        this.colour=colour;
        this.ammos=ammos;
        this.hasPowerup=true;
    }
}
