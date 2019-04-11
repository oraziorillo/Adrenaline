package model;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

public abstract class Tile {
    private final int x;
    private final int y;
    private final RoomColourEnum roomColour;
    private HashSet<Pc> pcs = new HashSet<>();
    private HashSet<Tile> visibles = new HashSet<>();

    public Tile(int x, int y, RoomColourEnum colour) {
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

    public HashSet<Tile> distanceOf(int distance){
        if(distance < 0){
            throw new IllegalArgumentException("Distance has to be positive");
        }
        HashSet<Tile> temp = new HashSet<>();
        if(distance == 0){
            temp.add(this);
        }
        else {
            for(Tile t1 : this.getVisibles()){
                temp.addAll(t1.distanceOf(distance-1));
            }
        }
        return temp;
    }

    public RoomColourEnum getRoomColour() {
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

    public Set<Tile> getVisibles() {
        return (HashSet<Tile>) visibles.clone();
    }

    public void addCharacter(Pc pc) {
        pcs.add(pc);
    }

    public void removeCharacter(Pc c) {
        pcs.remove(c);
    }

    public void addVisible(Tile t) {
        visibles.add(t);
    }

    public boolean equals(Tile t){
        return ((this.getX() == t.getX()) && (this.getY() == t.getY()));
    }


}


class GenerationTile extends Tile {
    //TODO da rivedere se abbiamo un'istanza di game dappertutto

    private WeaponCard[] weapons;
    private Deck<WeaponCard> weaponDeck;

    GenerationTile(int x, int y, RoomColourEnum colour, Deck<WeaponCard> deck) {
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

    AmmoTile(int x, int y, RoomColourEnum colour, Deck<AmmoCard> deck) {
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

    public void drawCardFromDeck() {
        if (card == null) {
            //TODO: pesca una nuova carta dal deck
        }
    }
}
