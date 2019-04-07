package model;


public class Character {
    private CharColourEnum[] damageTrack;
    private Weapon[] weapons;
    private Powerup[] powerups;
    private short[] marks;
    private int points;
    private int numOfDeath;
    private short ammos[];
    private Tile currentTile;
    private CharColourEnum colour;

    public Character (CharColourEnum colour){
        this.damageTrack = new CharColourEnum[12];
        this.weapons = new Weapon[3];
        this.powerups = new Powerup[3];
        this.marks = new short[5];
        this.points = 0;
        this.numOfDeath = 0;
        this.ammos = new short[3];
        this.currentTile = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
        this.colour = colour;
    }

    public void addDamage(CharColourEnum colour, int inflictedDamage, int inflictedMarks){
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

    public boolean isFullyArmed(){
        int index = 0;
        while(index < weapons.length && weapons[index] != null){
            index += 1;
        }
        return index == 3;
    }

    private Weapon weaponIndex (int index){
        Weapon temp;
        if( index < 0 || index > 3){
            throw new IllegalArgumentException("This index is not valid");
        }
        temp = weapons[index]
        return temp;
    }

    public void collectWeapon(int WeaponIndex){        //l'arma deve poi essere rimossa dal punto di generazione
        if(! ( instanceof GenerationTile)){     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in a GenerationTile");
        }
        GenerationTile workingTile = (GenerationTile) currentTile;
        int index = 0;
        while(index < weapons.length && weapons[index] != null){
            index += 1;
        }
        weapons[index] = workingTile.pickWeapon(WeaponIndex);
    }

    public void collectWeapon(int DesiredWeaponIndex, int ToDropWeaponIndex){
        if(! (currentTile instanceof GenerationTile)){
            throw new IllegalStateException("You are not in a GenerationTile");
        }
        GenerationTile workingTile = (GenerationTile) currentTile;
        weapons[ToDropWeaponIndex] = workingTile.switchWeapon(DesiredWeaponIndex, weaponIndex(ToDropWeaponIndex));
    }

    public void collectPowerup(){
        int index = 0;
        while (index < powerups.length && powerups[index] != null){
            index += 1;
        }
        if (index < powerups.length){
            powerups[index] = ammosDeck.draw();        //da implementare AmmoDeck
        }
    }

    public void addPoints(int newpoints){
        this.points += newpoints;
    }

    public void collectAmmos(){
        if(! (currentTile instanceof AmmoTile)){     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in an AmmoTile");
        }
        AmmoTile workingTile = (AmmoTile) currentTile;
        AmmoCard card = workingTile.drawCard();
        for (int i = 0; i < card.getAmmos().length; i++){
            this.ammos[i] += ammos[i];
            if(ammos[i] > 3)
                ammos[i] = 3;
        }
        if(card.containsPowerup()){      //da rivedere, troppo dipendente dalla classe AmmoCard??
            collectPowerup();
        }
    }

    public void respawn(RoomColourEnum colour){
        this.damageTrack = new CharColourEnum[12];
        numOfDeath = numOfDeath + 1;
        this.currentTile = //TODO: è dato dal colour che viene passato come parametro
    }

}
