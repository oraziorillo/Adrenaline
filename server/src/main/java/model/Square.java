package model;

import enums.CardinalDirectionEnum;
import enums.SquareColourEnum;
import java.util.HashSet;
import java.util.Optional;

public abstract class Square {
    private final int x;
    private final int y;
    private boolean targetable;
    private final SquareColourEnum tileColour;
    private HashSet<Pc> pcs;            //ricordarsi di aggiugnere degli observer che ad ogni spostamento del pc modifichi questo insieme
    private HashSet<Square> visibles;

    /**
     * Builder for a generic tile
     * @param x first index in map
     * @param y second index in map
     * @param colour the room colour
     */
    public Square(int x, int y, SquareColourEnum colour) {
        this.x = x;
        this.y = y;
        this.targetable = false;
        this.tileColour = colour;
        this.pcs = new HashSet<>();
        this.visibles = new HashSet<>();
    }

    /**
     * Getter for x coordinate in the map
     * @return the x coordinate of this tile
     */
    public int getX() {
        return x;
    }


    /**
     * Getter for y coordinate in the map
     * @return the y coordinate of this tile
     */
    public int getY() {
        return y;
    }


    public boolean isTargetable(){
        return targetable;
    }

    /**
     * Getter for room colour
     * @return The colour of this room
     */
    public SquareColourEnum getTileColour() {
        return tileColour;
    }

    /**
     * Returns the Pcs on this tile
     * @return the Pcs on this tile
     */
    public HashSet<Pc> getPcs() {
        return pcs;
    }

    /**
     * returns the tiles that a Pc on this tile could see
     * @return the tile visibles from this
     */
    public HashSet<Square> getVisibles() {
        return (HashSet<Square>)visibles.clone();
    }


    public void setTargetable(boolean targetable){
        this.targetable = targetable;
    }
    /**
     * @author matteo
     * @implNote usare x e y non sarebbe più pulito?
     * Returns an HashSet containing all the Tiles at a given distance
     * @param dist distance of returned tiles
     * @return HashSet of Tiles at distance dist
     */
    public HashSet<Square> atDistance(int dist){
        if(dist < 0){
            throw new IllegalArgumentException("Distance has to be positive");
        }
        Optional<Square> tempTile;
        HashSet<Square> temp = new HashSet<>();
        if(dist == 0){
            temp.add(this);
        }
        else {
            for(CardinalDirectionEnum direction : CardinalDirectionEnum.values()){
                tempTile = this.onDirection(direction);
                if(tempTile.isPresent()) {
                    temp.addAll(tempTile.get().atDistance(dist - 1));
                }
            }
        }
        return temp;
    }

    //La tile alla card. dir. specificata si ottiene più facilmente con semplice algebra sulla mappa

    /**
     * @author matteo
     * @implNote usando gli indici non risulta più pulito?
     * Given a cardinal direction, returns the first tile in that direction if no wall is encountered
     * @param direction the cardinal direction
     * @return The first tile in the given direction if there is no wall between, Optional.empty else
     */
    public Optional<Square> onDirection(CardinalDirectionEnum direction){
        Optional<Square> temp = Optional.empty();
        switch(direction) {
            case NORTH:
                temp = visibles.stream().filter(elem -> elem.getY() == this.getY() + 1 && elem.getX() == this.getX()).findFirst();
                break;
            case EAST:
                temp = visibles.stream().filter(elem -> elem.getX() == this.getX() + 1 && elem.getY() == this.getY()).findFirst();
                break;
            case SOUTH:
                temp = visibles.stream().filter(elem -> elem.getY() == this.getY() - 1 && elem.getX() == this.getX()).findFirst();
                break;
            case WEST:
                temp = visibles.stream().filter(elem -> elem.getX() == this.getX() - 1 && elem.getY() == this.getY()).findFirst();
                break;
        }
        return temp;
    }

    /**
     * adds a pc to this tile
     * @param pc the pc to put on this tile
     */
    public void addPc(Pc pc) {
        pcs.add(pc);
    }

    /**
     * removes a pc from this tile
     * @param c the pc to remove
     */
    public void removePc(Pc c) {
        pcs.remove(c);
    }

    /**
     *after this method the given tile will be visible from this tile (and then contained into the getVisibles collection)
     * @param t the tile to make visible
     */
    public void addVisible(Square t) {
        visibles.add(t);
    }

    public abstract void refill();

    /**
     * @author matteo
     * @apiNote Fare qui il metodo che fa raccogliere cose mi sembra la soluzione più semplice
     * Abstract method. Used for subclasses for giving objects to a player.
     * @param player the player wich is collecting something
     * @param objectIndex the index of the object to collect (weapon). In some subclasses could be irrelevant (one object only)
     */
    public abstract void collect(Pc player, int objectIndex);

    public abstract boolean isEmpty();
}


class SpawnPoint extends Square {

    private WeaponCard[] weapons;
    private Deck<WeaponCard> weaponDeck;

    SpawnPoint(int x, int y, SquareColourEnum colour, Deck<WeaponCard> deck) {
        super(x, y, colour);
        this.weaponDeck = deck;
        weapons = new WeaponCard[3];
        for (int i = 0; i < 3; i++)
            weapons[i] = weaponDeck.draw();
    }

    public WeaponCard[] getWeapons() {
        return weapons.clone();
    }

    WeaponCard pickWeapon(int index) throws NullPointerException{
        if(weapons[index] == null) throw new NullPointerException();
        WeaponCard temp = weapons[index];
        weapons[index] = null;
        return temp;
    }

    public WeaponCard switchWeapon(int index, WeaponCard w) {
        if(weapons[index] == null) throw new NullPointerException();
        WeaponCard temp = weapons[index];
        weapons[index] = w;
        return temp;
    }

    public void refill(){
        //TODO
    }

    @Override
    public void collect(Pc player, int objectIndex) {
        //TODO
    }

    @Override
    public boolean isEmpty() {
        for (WeaponCard weapon : weapons)
            if (weapon != null)
                return false;
        return true;
    }
}


class AmmoSquare extends Square {
    private AmmoTile ammoTile;
    private Deck<AmmoTile> ammoDeck;

    AmmoSquare(int x, int y, SquareColourEnum colour, Deck<AmmoTile> deck) {
        super(x, y, colour);
        ammoDeck = deck;
        ammoTile = ammoDeck.draw();
    }

    public AmmoTile pickAmmo() {
        AmmoTile oldCard = ammoTile;
        ammoTile = null;
        return oldCard;
    }

    public void refill(){
        if(ammoTile == null) {
            ammoTile = ammoDeck.draw();
        }
    }

    @Override
    public void collect(Pc player, int objectIndex) {
        //TODO
    }

    @Override
    public boolean isEmpty(){
        return ammoTile == null;
    }
}


