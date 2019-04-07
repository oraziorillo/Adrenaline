package model;

public abstract class Weapon {
    private short[] ammos;
    private WeaponEffect primaryEffect;

    Weapon(short blueAmmos, short redAmmos, short yellowAmmos, WeaponEffect e){
        ammos = new short[3];
        if(blueAmmos <= 3 && blueAmmos >= 0)
            ammos[AmmoEnum.BLUE.ordinal()] = blueAmmos;
        if(redAmmos <= 3 && redAmmos >= 0)
            ammos[AmmoEnum.RED.ordinal()] = redAmmos;
        if(yellowAmmos <= 3 && yellowAmmos >= 0)
            ammos[AmmoEnum.YELLOW.ordinal()] = yellowAmmos;
        primaryEffect = e;
    }

    public short[] getAmmos() {
        return ammos;
    }

    //TODO: add abstract method
}
