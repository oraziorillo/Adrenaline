package model;

import model.weapon_effect.WeaponEffect;

import java.util.Collection;
import java.util.HashSet;

public abstract class Weapon {
    private short[] ammos;
    private WeaponEffect currentEffect;
    private HashSet<WeaponEffect> effects;
    private HashSet<WeaponEffect> upgrades;

    Weapon(short blueAmmos, short redAmmos, short yellowAmmos, HashSet<WeaponEffect> effects, HashSet<WeaponEffect> upgrades) {
        ammos = new short[3];
        if (blueAmmos <= 3 && blueAmmos >= 0)
            ammos[AmmoEnum.BLUE.ordinal()] = blueAmmos;
        if (redAmmos <= 3 && redAmmos >= 0)
            ammos[AmmoEnum.RED.ordinal()] = redAmmos;
        if (yellowAmmos <= 3 && yellowAmmos >= 0)
            ammos[AmmoEnum.YELLOW.ordinal()] = yellowAmmos;
        this.effects = effects;
        this.upgrades = upgrades;
    }

    public short[] getAmmos() {
        return ammos;
    }

    public Collection<WeaponEffect> getEffects() {
        return (HashSet<WeaponEffect>) effects.clone();
    }

    public Collection<WeaponEffect> getUpgrades() {
        return (HashSet<WeaponEffect>) upgrades.clone();
    }

    public void selectEffect(WeaponEffect effect) throws IllegalArgumentException{
        if(!effects.contains(effect)){
            throw new IllegalArgumentException("This weapon can't do that");
        }
        currentEffect=effect;
    }

    public void attachUpgrade(WeaponEffect upgrade) throws IllegalArgumentException{
        if(!upgrades.contains(upgrade)){
            throw new IllegalArgumentException("This weapon can't be upgraded that way");
        }
        currentEffect=WeaponEffect.upgrade(currentEffect,upgrade);
    }
}
