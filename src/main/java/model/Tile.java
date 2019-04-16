package model;

import model.Enumerations.CardinalDirectionEnum;
import model.Enumerations.TileColourEnum;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

public abstract class Tile {
    private Game currGame;
    private final int x;
    private final int y;
    private final TileColourEnum tileColour;
    private HashSet<Pc> pcs;
    private HashSet<Tile> visibles;

    public Tile(int x, int y, TileColourEnum colour, Game currGame) {
        this.currGame = currGame;
        this.x = x;
        this.y = y;
        this.tileColour = colour;
        this.pcs = new HashSet<>();
        this.visibles = new HashSet<>();
    }

    public Game getCurrGame(){
        return currGame;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @param dist distance of returned tiles
     * @return HashSet of Tiles at distance dist
     */
    public HashSet<Tile> atDistance(int dist){
        if(dist < 0){
            throw new IllegalArgumentException("Distance has to be positive");
        }
        Optional<Tile> tempTile;
        HashSet<Tile> temp = new HashSet<>();
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

    public TileColourEnum getTileColour() {
        return tileColour;
    }

    public Optional<Tile> onDirection(CardinalDirectionEnum direction){
        Optional<Tile> temp = null;
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

    public HashSet<Pc> getPcs() {
        return (HashSet<Pc>) pcs.clone();
    }

    public HashSet<Tile> getVisibles() {
        return (HashSet<Tile>) visibles.clone();
    }

    public void addPc(Pc pc) {
        pcs.add(pc);
    }

    public void removePc(Pc c) {
        pcs.remove(c);
    }

    public void addVisible(TileColourEnum t) {
        visibles.add(t);
    }

    /* davvero utile???? non dovrebbe essere un '=='?? Noi restituiamo sempre i riferimenti ai tile, mai cloni

    public boolean equals(Tile t){
        return ((this.getX() == t.getX()) && (this.getY() == t.getY()));
    }

     */

}


class SpawnTile extends Tile {

    private WeaponCard[] weapons;

    SpawnTile(int x, int y, TileColourEnum colour, Game game) {
        super(x, y, colour, game);
        weapons = new WeaponCard[3];
        for (int i = 0; i < 3; i++)
            weapons[i] = getCurrGame().weaponsDeck.draw();
    }

    public WeaponCard[] getWeapons() {
        return weapons;
    }

    public WeaponCard pickWeapon(int index) {
        WeaponCard temp = weapons[index];
        weapons[index] = null;
        return temp;
    }

    public WeaponCard switchWeapon(int index, WeaponCard w) {
        WeaponCard temp = weapons[index];
        weapons[index] = w;
        return temp;
    }
}


class AmmoTile extends Tile {
    private AmmoCard ammoCard;

    AmmoTile(int x, int y, TileColourEnum colour, Game game) {
        super(x, y, colour, game);
        ammoCard = getCurrGame().ammosDeck.draw();
    }

    public AmmoCard pickAmmo() {
        AmmoCard oldCard = ammoCard;
        ammoCard = getCurrGame().ammosDeck.draw();
        return oldCard;
    }

}


enum TileColourEnum {
    RED,
    YELLOW,
    GREEN,
    WHITE,
    VIOLET,
    BLUE;
}
