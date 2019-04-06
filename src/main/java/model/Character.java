package model;

public class Character {
    private CharColour[] damage;
    private Weapon[] weapons;
    private Powerup[] powerups;
    private short[] marks;
    private int points;
    private short numOfDeath;
    private short ammos[];
    private Tile position;
    private CharColour colour;

    public Character (CharColour colour){
        this.damage = new CharColour[12];
        this.weapons = new Weapon[3];
        this.powerups = new Powerup[3];
        this.marks = new short[4];
        this.points = 0;
        this.numOfDeath = 0;
        this.ammos = new short[3];
        this.position = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
        this.colour = colour;
    }

    public void addDamage(CharColour colour, int damage, int marks){
        //TODO: method
        int index = 0;
        while (damage[i] != null){
            i = i + 1;
        }

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
    }       //TODO: qui il controller dovrebbe fare dei controlli per vedere se è morto

    public void collectAmmos(short[] ammos){
        if(ammos.length!=this.ammos.length){
            throw new IllegalArgumentException("Array di dimensione errata");
        }
        for(int i=0; i<ammos.length; i++){
            this.ammos[i]+=ammos[i];
            if(ammos[i]>3)
                ammos[i]=3;
        }
    }

    public void respawn(RoomColour colour){
        this.damage = new CharColour[12];
        numOfDeath = numOfDeath + 1;
        this.position = //TODO: è dato dal colour che viene passato come parametro
    }

}
