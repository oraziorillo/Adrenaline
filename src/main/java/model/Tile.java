package model;

import java.util.Collection;
import java.util.HashSet;

public abstract class Tile {
    private HashSet<Character> characters = new HashSet<>();
    private final int x;
    private final int y;
    private HashSet<Tile> visibles = new HashSet<>();
    private final RoomColourEnum roomColour;

    public Tile(int x, int y, RoomColourEnum colour) {
        this.x = x;
        this.y = y;
        this.roomColour = colour;
        this.characters = new HashSet<>();
        this.visibles = new HashSet<>();
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public HashSet<Character> getCharacters() {
        return (HashSet<Character>) characters.clone();
    }

    public HashSet<Tile> getVisibles() {
        return (HashSet<Tile>) visibles.clone();
    }

    public void addVisible(Tile t) {
        visibles.add(t);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public RoomColourEnum getRoomColour() {
        return roomColour;
    }

    public boolean equals(Tile t1){
        boolean equal = false;
        if(this.x == t1.x && this.y == t1.y){
            equal == true;
        }
        return equal;
    }


}
