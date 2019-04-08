package model;

public class Character {
    private CharColourEnum[] damageTrack;
    private Weapon[] weapons;
    private Powerup[] powerUps;
    private short[] marks;
    private short points;
    private short numOfDeath;
    private short ammos[];
    private Tile currentTile;
    private CharColourEnum colour;

    public Character(CharColourEnum colour) {
        this.damageTrack = new CharColourEnum[12];
        this.weapons = new Weapon[3];
        this.powerUps = new Powerup[3];
        this.marks = new short[5];
        this.points = 0;
        this.numOfDeath = 0;
        this.ammos = new short[3];
        this.currentTile = null;       //viene posto a null perchè ancora non è stato generato sulla mappa
        this.colour = colour;
    }

    public boolean isFullyArmed() {
        int index = 0;
        while (index < weapons.length && weapons[index] != null) {
            index += 1;
        }
        return index == 3;
    }

    public Tile getCurrentTile () {
        return this.currentTile;
    }

    private Weapon weaponIndex(int index) {
        Weapon temp;
        if (index < 0 || index > 3) {
            throw new IllegalArgumentException("This index is not valid");
        }
        temp = weapons[index];
        return temp;
    }

    public void collectWeapon(int WeaponIndex) {        //l'arma deve poi essere rimossa dal punto di generazione
        if (!(currentTile instanceof GenerationTile)) {     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in a GenerationTile");
        }
        GenerationTile workingTile = (GenerationTile) currentTile;
        int index = 0;
        while (index < weapons.length && weapons[index] != null) {
            index += 1;
        }
        weapons[index] = workingTile.pickWeapon(WeaponIndex);
    }

    public void collectWeapon(int DesiredWeaponIndex, int ToDropWeaponIndex) {
        if (!(currentTile instanceof GenerationTile)) {
            throw new IllegalStateException("You are not in a GenerationTile");
        }
        GenerationTile workingTile = (GenerationTile) currentTile;
        weapons[ToDropWeaponIndex] = workingTile.switchWeapon(DesiredWeaponIndex, weaponIndex(ToDropWeaponIndex));
    }

    public void collectAmmos() {
        if (!(currentTile instanceof AmmoTile)) {     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in an AmmoTile");
        }
        AmmoTile workingTile = (AmmoTile) currentTile;
        AmmoCard card = workingTile.drawCard();
        for (int i = 0; i < card.getAmmos().length; i++) {
            this.ammos[i] += ammos[i];
            if (ammos[i] > 3)
                ammos[i] = 3;
        }
        if (card.containsPowerup()) {      //da rivedere, troppo dipendente dalla classe AmmoCard??
            collectPowerup();
        }
    }

    public void collectPowerup() {

        int index = 0;
        while (index < powerUps.length && powerUps[index] != null) {
            index += 1;
        }
        if (index < powerUps.length) {
            //TODO
            //powerUps[index] = ammosDeck.draw();
        }
    }

    public void takeDamage(short damage, CharColourEnum colour) {
        int index = 0;
        short totaldamage = (short) (marks[colour.ordinal()] + damage);
        while (damageTrack[index] != null) {
            index = index + 1;
        }
        while (totaldamage != 0) {       //il controller dovrà controllare ogni volta se il giocatore è morto, verificando che all'indice 10 il valore sia != null
            damageTrack[index] = colour;
            if (index > 10) {
                totaldamage = 0;
            } else {
                index += 1;
            }
        }
    }

    public void takeMarks (short marks, CharColourEnum colour){
        this.marks[colour.ordinal()] = marks;
    }

    public void increasePoints(int n) {
        this.points += n;
    }

    public void respawn(RoomColourEnum colour) {
        this.damageTrack = new CharColourEnum[12];
        numOfDeath = (short) (numOfDeath + 1);
        //TODO: è dato dal colour che viene passato come parametro
        //this.currentTile = getGenerationTile(colour);
    }


}
