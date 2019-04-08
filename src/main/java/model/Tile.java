package model;

import java.util.Collection;
import java.util.HashSet;

public abstract class Tile {
    private HashSet<Character> characters = new HashSet<>();
    private final int x;
    private final int y;
    private HashSet<Tile> visibles = new HashSet<>();
    private final RoomColourEnum roomColour;

    Tile(int x, int y, RoomColourEnum colour) {
        this.x = x;
        this.y = y;
        this.roomColour = colour;
    }

    public static int distance(Tile t1, Tile t2) {
        return Math.abs(t1.x + t1.y - t2.x - t2.y);
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public Collection<Character> getCharacters() {
        return (Collection<Character>) characters.clone();
    }

    public Collection<Tile> getVisibles() {
        return (Collection<Tile>) visibles.clone();
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


}
