package model;

import model.weapon_effect.WeaponEffect;

import java.util.Collection;
import java.util.HashSet;

public abstract class Weapon {
    private short[] ammos;
    private HashSet<WeaponEffect> effects;
    private HashSet<WeaponEffect> upgrades;

    Weapon(short blueAmmos, short redAmmos, short yellowAmmos, HashSet<WeaponEffect> effects, HashSet<WeaponEffect> upgrades){
        ammos = new short[3];
        if(blueAmmos <= 3 && blueAmmos >= 0)
            ammos[AmmoEnum.BLUE.ordinal()] = blueAmmos;
        if(redAmmos <= 3 && redAmmos >= 0)
            ammos[AmmoEnum.RED.ordinal()] = redAmmos;
        if(yellowAmmos <= 3 && yellowAmmos >= 0)
            ammos[AmmoEnum.YELLOW.ordinal()] = yellowAmmos;
        this.effects=effects;
        this.upgrades=upgrades;
    }

    public short[] getAmmos() {
        return ammos;
    }

    public Collection<WeaponEffect> getEffects() {
        return (HashSet<WeaponEffect>)effects.clone();
    }

    public Collection<WeaponEffect> getUpgrades() {
        return (HashSet<WeaponEffect>)upgrades.clone();
    }
}
