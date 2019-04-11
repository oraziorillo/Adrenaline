package model;

public class GenerationTile extends Tile {

    private WeaponCard[] weapons;

    private /*static*/ Deck<WeaponCard> weaponDeck;

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
