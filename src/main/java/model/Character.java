package model;


public class Character {
    private CharColour[] damageTrack;
    private Weapon[] weapons;
    private Powerup[] powerups;
    private short[] marks;
    private int points;
    private short numOfDeath;
    private short ammos[];
    private Tile position;
    private CharColour colour;

    public Character (CharColour colour){
        this.damageTrack = new CharColour[12];
        this.weapons = new Weapon[3];
        this.powerups = new Powerup[3];
        this.marks = new short[5];
        this.points = 0;
        this.numOfDeath = 0;
        this.ammos = new short[3];
        this.position = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
        this.colour = colour;
    }

    public void addDamage(CharColour colour, int inflictedDamage, int inflictedMarks){
        int index = 0;
        int TotalDamage = marks[colour.ordinal()] + inflictedDamage;
        boolean overkilled = false;
        while (damageTrack[index] != null){
            index = index + 1;
        }
        while(TotalDamage != 0){       //il controller dovrà controllare ogni volta se il giocatore è morto, verificando che all'indice 10 il valore sia != null
            damageTrack[index] = colour;
            if(index > 10){
                overkilled = true;
                TotalDamage = 0;
            }
            else{
                index += 1;
            }
        }
        marks[colour.ordinal()] = inflictedMarks;        //conviene implementare un metodo SetMask?
    }

    public void collectWeapon(int WeaponIndex){        //l'arma deve poi essere rimossa dal punto di generazione
        if(! (position instanceof GenerationTile)){     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in a generation tile");
        }
        GenerationTile workingTile = (GenerationTile) position;
        int index = 0;
        while(index < weapons.length() && weapons[index] != null){
            index += 1;
        }
        weapons[index] = workingTile.pickWeapon(WeaponIndex);
    }

    public void collectWeapon(int DesiredWeaponIndex, Weapon ToDropWeapon){
        if(! (position instanceof GenerationTile)){
            throw new IllegalStateException("You are not in a generation tile");
        }
        GenerationTile workingTile = (GenerationTile) position;
        int index = 0;
        while(index < weapons.length() && weapons[index] != null){
            index += 1;
        }
        weapons[index] = workingTile.switchWeapon(DesiredWeaponIndex, ToDropWeapon);
    }

    public boolean isFullyArmed(){
        int index = 0;
        while(index < weapons.length() && weapons[index] != null){
            index += 1;
        }
        return index == 3;
    }

    public void collectWeapon(Weapon WeaponToDrop){
        short index = 0;
        short newWeaponIndex;
        if(index = weapons.length()){
            newWeaponIndex = dropWeapon();       //da implementare: chiedi all'utente quale arma vuole lasciare
        }
        //da continuare
    }

    public Weapon dropWeapon(Weapon[] weaponpoint,  Weapon w){
        short index = 0;
        while (index < 3 && !weapons[index].equals(w)){
            index += 1;
        }
        if (index == 3){
            throw new IllegalArgumentException ("You don't really have this weapon!");
        }

    }

    public void collectPowerup(){
        short index = 0;
        while (index < powerups.length() && powerups[index] != null){
            index += 1;
        }
        if (index < powerups.length()){
            powerups[i] = Ammodeck.draw();        //da implementare AmmoDeck
        }
    }

    public void addPoints(int points){
        this.points += points;
    }

    public void collectAmmos(AmmoCard card){
        if (ammos.length()!=this.card.length()){
            throw new IllegalArgumentException("Array di dimensione errata");
        }
        for (int i=0; i<card.length(); i++){
            this.ammos[i]+=ammos[i];
            if(ammos[i]>3)
                ammos[i]=3;
        }
        if(card.hasPowerUp()){      //da rivedere, troppo dipendente dalla classe AmmoCard??
            collectPowerup();
        }
    }

    public void respawn(RoomColour colour){
        this.damageTrack = new CharColour[12];
        numOfDeath = numOfDeath + 1;
        this.position = //TODO: è dato dal colour che viene passato come parametro
    }

}
