package model;

public class GenerationTile extends Tile {
    private Weapon[] weapons;

    public Weapon[] getWeapons() {
        return weapons;
    }

    public Weapon pickWeapon(int index){
        Weapon temp = weapons[index];
        weapons[index] = null;
        return temp;
    }

    public Weapon switchWeapon(int index, Weapon w){
        Weapon temp = weapons[index];
        weapons[index] = w;
        return temp;
    }
}
