package model;

import java.util.Collection;
import java.util.HashSet;

public abstract class Tile {
    private HashSet<Character> characters=new HashSet<>();
    private int x;
    private int y;
    private HashSet<Tile> visibles=new HashSet<>();

    public static int distance(Tile t1, Tile t2){
        return Math.abs(t1.x+t1.y-t2.x-t2.y);
    }
    public void addCharacter(Character c){
        characters.add(c);
    }

    public void removeCharacter(Character c){
        characters.remove(c);
    }

    public Collection<Character> getCharacters(){
        return (Collection<Character>) characters.clone();
    }

    public Collection<Tile> getVisibles() {
        return (Collection<Tile>) visibles.clone();
    }

    public void addVisible(Tile t){
        visibles.add(t);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    Tile(int x,int y){
        this.x=x;
        this.y=y;
    }

    //TODO: VALE LA PENA AGGIUNGERE DUE METODI isGenerationTile E isAmmoTile ??

}
