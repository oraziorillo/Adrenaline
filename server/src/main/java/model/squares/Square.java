package model.squares;

import enums.CardinalDirectionEnum;
import enums.SquareColourEnum;
import exceptions.EmptySquareException;
import model.Pc;

import java.util.HashSet;
import java.util.Optional;

public abstract class Square {
    private final int x;
    private final int y;
    private boolean targetable;
    private final SquareColourEnum colour;
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
        this.colour = colour;
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
    public SquareColourEnum getColour() {
        return colour;
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
        return visibles;
    }


    public void setTargetable(boolean targetable){
        this.targetable = targetable;
    }


    public void setWeaponToGrabIndex(int weaponToGrabIndex){}


    public void setWeaponToDropIndex(int weaponToDropIndex){}


    public void resetWeaponIndexes(){}


    public abstract void collect(Pc currPc) throws EmptySquareException;

    /**
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
                tempTile.ifPresent(square -> temp.addAll(square.atDistance(dist - 1)));
            }
        }
        return temp;
    }

    //La tile alla card. dir. specificata si ottiene più facilmente con semplice algebra sulla mappa

    /**
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
            default:
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


    public abstract boolean isEmpty();


    public abstract boolean isSpawnPoint();
}

