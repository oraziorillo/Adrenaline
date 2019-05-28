package model.squares;

import enums.CardinalDirectionEnum;
import enums.SquareColourEnum;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.Pc;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Square {
    private final int x;
    private final int y;
    private boolean targetable;
    private boolean marked;
    private final SquareColourEnum colour;
    private HashSet<Pc> pcs;
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
        this.marked = false;
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
    public Set<Pc> getPcs() {
        return pcs;
    }


    /**
     * returns the tiles that a Pc on this tile could see
     * @return the tile visibles from this
     */
    public Set<Square> getVisibles() {
        return visibles;
    }


    public void setTargetable(boolean targetable){
        this.targetable = targetable;
    }


    public void setWeaponToGrabIndex(int weaponToGrabIndex){}


    public void setWeaponToDropIndex(int weaponToDropIndex){}


    public void resetWeaponIndexes(){}


    public abstract void collect(Pc currPc) throws EmptySquareException, NotEnoughAmmoException;

    /**
     * Returns an HashSet containing all the Tiles at a given distance
     * @param dist distance of returned tiles
     * @return HashSet of Tiles at distance dist
     */
    public Set<Square> atDistance(int dist){
        if(dist < 0){
            throw new IllegalArgumentException("Distance has to be positive");
        }
        Square tempSquare;
        HashSet<Square> temp = new HashSet<>();
        if(dist != 0){
            for(CardinalDirectionEnum direction : CardinalDirectionEnum.values()){
                tempSquare = this.onDirection(direction);
                if(tempSquare != null) {
                    temp.addAll(tempSquare.atDistance(dist -1));
                }
            }
        }
        temp.add(this);
        return temp;
    }


    /**
     * Given a cardinal direction, returns the first tile in that direction if no wall is encountered
     * @param direction the cardinal direction
     * @return The first tile in the given direction if there is no wall between, Optional.empty else
     */
    public Square onDirection(CardinalDirectionEnum direction){
        Optional<Square> temp = visibles
                .stream()
                .parallel()
                .filter(s -> {
                    switch (direction) {
                        case NORTH:
                            return s.getY() == this.getY() + 1 && s.getX() == this.getX();
                        case EAST:
                            return s.getX() == this.getX() + 1 && s.getY() == this.getY();
                        case SOUTH:
                            return s.getY() == this.getY() - 1 && s.getX() == this.getX();
                        case WEST:
                            return s.getX() == this.getX() - 1 && s.getY() == this.getY();
                        default:
                            return false;
                    }
                })
                .findFirst();
        return temp.orElse(null);
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
     *after this method the given square will be "visible" from this square
     * @param t the tile to make visible
     */
    public void addVisible(Square t) {
        visibles.add(t);
    }


    /**
     * @return an HashSet containing all squares on the map
     */
    public Set<Square> allSquares(){
        LinkedList<Square> queue = new LinkedList<>();
        HashSet<Square> resultSet = new HashSet<>();
        queue.offer(this);
        resultSet.add(this);
        this.marked = true;
        while (!queue.isEmpty()){
            Square currSquare = queue.poll();
            currSquare.visibles.forEach(s -> {
                if (!marked) {
                    s.marked = true;
                    queue.offer(s);
                    resultSet.add(s);
                }
            });
        }
        return resultSet;
    }


    public Set<Square> allSquaresOnDirection(CardinalDirectionEnum direction){
        Set<Square> resultSet = allSquares();
        resultSet = resultSet.stream()
                .parallel()
                .filter(s -> {
                    switch (direction){
                        case NORTH:
                            return s.getX() == this.getX() && s.getY() >= this.getY();
                        case EAST:
                            return s.getX() >= this.getX() && s.getY() == this.getY();
                        case SOUTH:
                            return s.getX() == this.getX() && s.getY() <= this.getY();
                        case WEST:
                            return s.getX() <= this.getX() && s.getY() == this.getY();
                        default:
                            return s.getX() == this.getX() || s.getY() == this.getY();
                    }
                })
                .collect(Collectors.toSet());
        return resultSet;
    }


    public abstract void refill();


    public abstract boolean isEmpty();


    public abstract boolean isSpawnPoint();
}

