package model;

import java.util.Collection;

public abstract class Tile {
    //TODO: What about using a Map?
    Collection<Character> characters;
    private int x;
    private int y;
    Collection<Tile> visibles;
    public static int distance(Tile t1, Tile t2){
        return Math.abs(t1.x+t1.y-t2.x-t2.y);
    }
}
