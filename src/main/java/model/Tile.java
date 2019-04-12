package model;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

public abstract class Tile {
    private final int x;
    private final int y;
    private final TileColourEnum roomColour;
    private HashSet<Pc> pcs = new HashSet<>();
    private HashSet<Tile> visibles = new HashSet<>();

    public Tile(int x, int y, TileColourEnum colour) {
        this.x = x;
        this.y = y;
        this.roomColour = colour;
        this.pcs = new HashSet<>();
        this.visibles = new HashSet<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @param dist distance of Tiles returned
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
                tempTile = this.OnDirection(direction);
                if(tempTile.isPresent()) {
                    temp.addAll(tempTile.get().atDistance(dist - 1));
                }
            }
        }
        return temp;
    }


    public TileColourEnum getRoomColour() {
        return roomColour;
    }

    public Optional<Tile> OnDirection(CardinalDirectionEnum direction){
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

    public Set<TileColourEnum> getVisibles() {
        return (HashSet<TileColourEnum>) visibles.clone();
    }

    public void addCharacter(Pc pc) {
        pcs.add(pc);
    }

    public void removeCharacter(Pc c) {
        pcs.remove(c);
    }

    public void addVisible(TileColourEnum t) {
        visibles.add(t);
    }

    public boolean equals(Tile t){
        return ((this.getX() == t.getX()) && (this.getY() == t.getY()));
    }

}


class SpawnTile extends Tile {
    //TODO da rivedere se abbiamo un'istanza di game dappertutto

    private WeaponCard[] weapons;
    private Deck<WeaponCard> weaponDeck;

    SpawnTile(int x, int y, TileColourEnum colour, Deck<WeaponCard> deck) {
        super(x, y, colour);
        weapons = new WeaponCard[3];
        weaponDeck = deck;
        for (int i = 0; i < 3; i++)
            weapons[i] = weaponDeck.draw();
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
    //TODO da rivedere se abbiamo un'istanza di game dappertutto
    private AmmoCard card;
    private Deck<AmmoCard> ammoCardDeck;

    AmmoTile(int x, int y, TileColourEnum colour, Deck<AmmoCard> deck) {
        super(x, y, colour);
        ammoCardDeck = deck;
        card = ammoCardDeck.draw();
    }

    public AmmoCard drawCard() {
        //TODO: qui dovrà essere inserito un observer che notifica che la carta è stata pescata e deve essere sostituita
        AmmoCard oldCard = card;
        card = null;
        return oldCard;
    }
}


 class enum TileColourEnum {
    RED,
    YELLOW,
    GREEN,
    WHITE,
    VIOLET,
    BLUE;
}
