package model;

public class Character {
    private CharColour damage[]=new CharColour[12];
    private Weapon weapons[]=new Weapon[3];
    private Powerup powerups[]=new Powerup[3];
    private CharColour[][] marks;
    private int points;
    private short numOfDeath;
    private short ammos[]=new short[3];
    private Square position;
    private CharColour colour;

    public void addDamage(CharColour colour, int damage, int marks){
        //TODO: method
    }
    public void collectWeapon(Weapon w){
        //TODO: method
    }
    public Weapon dropWeapon(Weapon w){
        //TODO: method
        return new Weapon();
    }
    public void collectPowerup(Powerup p){
        //TODO: method
    }
    public void addPoints(int points){
        this.points+=points;
    }
    public void collectAmmos(short[] ammos){
        if(ammos.length!=this.ammos.length){
            throw new IllegalArgumentException("Array di dimensione errata");
        }
        for(int i=0;i<ammos.length;i++){
            this.ammos[i]+=ammos[i];
            if(ammos[i]>3)
                ammos[i]=3;
        }
    }

}
