package model;

public class GenerationTile extends Tile {
    private Weapon[] weapons;

    public Weapon[] getWeapons() {
        return weapons;
    }

    public Weapon takeWeapon(int i){
        //TODO: think about how to replace the card
        return weapons[i];
    }
}
