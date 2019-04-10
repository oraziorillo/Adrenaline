package model;

public class Character {
    private CharacterColourEnum[] damageTrack;
    private WeaponCard[] weapons;
    private PowerUpCard[] powerUps;
    private short[] marks;
    private short points;
    private short numOfDeath;
    private short ammos[];
    private Tile currentTile;
    private CharacterColourEnum colour;

    public Character(CharacterColourEnum colour) {
        this.damageTrack = new CharacterColourEnum[12];
        this.weapons = new WeaponCard[3];
        this.powerUps = new PowerUpCard[3];
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

    public Tile getCurrentTile() {
        return this.currentTile;
    }

    private WeaponCard weaponIndex(int index) {
        WeaponCard temp;
        if (index < 0 || index > 3) {
            throw new IllegalArgumentException("This index is not valid");
        }
        temp = weapons[index];
        return temp;
    }

    public void move(CardinalDirectionEnum dir){
        //TODO
    }

    public void collectWeapon(int weaponIndex) {        //l'arma deve poi essere rimossa dal punto di generazione
        if (!(currentTile instanceof GenerationTile)) {     //potremmo far confluire tutto in un unico metodo aggiungendo un'optional
            throw new IllegalStateException("You are not in a GenerationTile");
        }
        GenerationTile workingTile = (GenerationTile) currentTile;
        int index = 0;
        while (index < weapons.length && weapons[index] != null) {
            index += 1;
        }
        weapons[index] = workingTile.pickWeapon(weaponIndex);
    }

    public void collectWeapon(int desiredWeaponIndex, int toDropWeaponIndex) {
        if (!(currentTile instanceof GenerationTile)) {
            throw new IllegalStateException("You are not in a GenerationTile");
        }
        GenerationTile workingTile = (GenerationTile) currentTile;
        weapons[toDropWeaponIndex] = workingTile.switchWeapon(desiredWeaponIndex, weaponIndex(toDropWeaponIndex));
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
        if (index < powerUps.length) {      //lo facciamo gestire dal controller?
            //TODO
            //Deck<PowerUpCard> powerUpsDeck;
            //powerUps[index] = powerUpsDeck.draw();
        }
    }

    public void takeDamage(short damage) {
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

    public void takeMarks(short marks) {
        this.marks[colour.ordinal()] = marks;
    }

    public void increasePoints(int n) {
        this.points += n;
    }

    public void respawn(RoomColourEnum colour) {
        this.damageTrack = new CharacterColourEnum[12];
        numOfDeath = (short) (numOfDeath + 1);
        //TODO: è dato dal colour che viene passato come parametro
        //this.currentTile = getGenerationTile(colour);
    }

    public void payAmmos(short[] ammos) throws NotEnoughAmmosException {
        if (this.ammos[AmmoEnum.BLUE.ordinal()] < ammos[AmmoEnum.BLUE.ordinal()] ||
                this.ammos[AmmoEnum.RED.ordinal()] < ammos[AmmoEnum.RED.ordinal()] ||
                this.ammos[AmmoEnum.YELLOW.ordinal()] < ammos[AmmoEnum.YELLOW.ordinal()]) {
            throw new NotEnoughAmmosException();
        }
        this.ammos[AmmoEnum.BLUE.ordinal()] -= ammos[AmmoEnum.BLUE.ordinal()];
        this.ammos[AmmoEnum.RED.ordinal()] -= ammos[AmmoEnum.RED.ordinal()];
        this.ammos[AmmoEnum.YELLOW.ordinal()] -= ammos[AmmoEnum.YELLOW.ordinal()];
    }


}
