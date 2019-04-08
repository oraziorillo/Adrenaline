package model;

public class GenerationTile extends Tile {

    private Weapon[] weapons;

    private /*static*/ Deck<Weapon> weaponDeck;

    GenerationTile(int x, int y, RoomColourEnum colour, Deck<Weapon> deck) {
        super(x, y, colour);
        weapons = new Weapon[3];
        weaponDeck = deck;
        for (int i = 0; i < 3; i++)
            weapons[i] = weaponDeck.draw();
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public Weapon pickWeapon(int index) {
        Weapon temp = weapons[index];
        weapons[index] = null;
        return temp;
    }

    public Weapon switchWeapon(int index, Weapon w) {
        Weapon temp = weapons[index];
        weapons[index] = w;
        return temp;
    }
}
