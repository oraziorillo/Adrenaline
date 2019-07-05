package server.model.squares;

import com.google.gson.annotations.Expose;
import common.dto_model.SquareDTO;
import common.enums.CardinalDirectionEnum;
import common.enums.SquareColourEnum;
import common.events.square_events.TargetableSetEvent;
import common.exceptions.EmptySquareException;
import common.exceptions.NotEnoughAmmoException;
import server.model.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Square {

    ModelEventHandler events;

    @Expose boolean isSpawnPoint;
    @Expose private int row;
    @Expose private int col;
    @Expose private SquareColourEnum colour;
    @Expose private HashSet<Pc> pcs;
    private boolean targetable;
    private boolean marked;
    private HashSet<Square> visibles;


    public Square(){
        this.pcs = new HashSet<>();
        this.visibles = new HashSet<>();
    }


    public Square(int row, int col, SquareColourEnum colour) {
        this.row = row;
        this.col = col;
        this.colour = colour;
        this.pcs = new HashSet<>();
        this.visibles = new HashSet<>();
    }


    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }


    public boolean isTargetable(){
        return targetable;
    }


    public boolean isMarked() {
        return marked;
    }


    public void setMarked(boolean marked) {
        this.marked = marked;
    }


    public SquareColourEnum getColour() {
        return colour;
    }


    public Set<Pc> getPcs() {
        return pcs;
    }


    public Set<Square> getVisibles() {
        return visibles;
    }


    public void setTargetable(boolean targetable){
        this.targetable = targetable;

        //notify listeners
        events.fireEvent(new TargetableSetEvent(convertToDTO()));
    }


    public void setWeaponToGrabIndex(int weaponToGrabIndex){}


    public void setWeaponToDropIndex(int weaponToDropIndex){}


    public void resetWeaponIndexes(){}


    /**
     * Used to grab a weapon or ammo from this square
     * @param currPc the pc who wants to collect the object
     * @throws EmptySquareException if there are no objects on this square
     * @throws NotEnoughAmmoException if the currPc has not enough ammo to pick a weapon
     */
    public abstract void collect(Pc currPc) throws EmptySquareException, NotEnoughAmmoException;

    /**
     * Returns a Set containing all the Squares at a given distance
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
     * Given a cardinal direction, returns the first square in that direction if no wall is encountered
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
                            return s.getRow() == this.row - 1 && s.getCol() == this.col;
                        case EAST:
                            return s.getCol() == this.col + 1 && s.getRow() == this.row;
                        case SOUTH:
                            return s.getRow() == this.row + 1 && s.getCol() == this.col;
                        case WEST:
                            return s.getCol() == this.col - 1 && s.getRow() == this.row;
                        default:
                            return false;
                    }
                })
                .findFirst();
        return temp.orElse(null);
    }


    /**
     * adds a pc to this square
     * @param pc the pc to put on this tile
     */
    public void addPc(Pc pc) {
        pcs.add(pc);
    }


    /**
     * removes a pc from this square
     * @param c the pc to removeLine
     */
    public void removePc(Pc c) {
        pcs.remove(c);
    }


    /**
     *after this method the given square will be "visible" from this square
     * @param s the tile to make visible
     */
    public void addVisible(Square s) {
        visibles.add(s);
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
                if (!s.isMarked()) {
                    s.setMarked(true);
                    queue.offer(s);
                    resultSet.add(s);
                }
            });
        }
        resultSet.forEach(s -> s.setMarked(false));
        return resultSet;
    }
    
    /**
     * Returns a set with every square on a cardinal direction
     * @param direction the desired direction
     * @return A set containing every set on the given direction
     */
    public Set<Square> allSquaresOnDirection(CardinalDirectionEnum direction){
        Set<Square> resultSet = allSquares();
        resultSet = resultSet.stream()
                .filter(s -> {
                    if (direction == null)
                        return s.getRow() == this.getRow() || s.getCol() == this.getCol();
                    switch (direction){
                        case NORTH:
                            return s.getRow() <= this.getRow() && s.getCol() == this.getCol();
                        case EAST:
                            return s.getRow() == this.getRow() && s.getCol() >= this.getCol();
                        case SOUTH:
                            return s.getRow() >= this.getRow() && s.getCol() == this.getCol();
                        case WEST:
                            return s.getRow() == this.getRow() && s.getCol() <= this.getCol();
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toSet());
        return resultSet;
    }



    public void addModelEventHandler(ModelEventHandler events) {
        this.events = events;
    }


    public abstract void init(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck);


    public abstract boolean isEmpty();


    public abstract boolean isSpawnPoint();


    public abstract void refill();

    @Override
    public String toString(){
        return "(" + this.row + "," + this.col + ")";
    }


    public String description(){
        return (isSpawnPoint ? "\nSpawnPoint " : "\nAmmoSquare ") + "(" + row + "," + col + ")";
    }

    public abstract String itemToString();


    public abstract SquareDTO convertToDTO();
}

