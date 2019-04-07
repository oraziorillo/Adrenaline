package model;

public class DoubleFiremodeWeapon {
    private WeaponEffect alternativeFiremode;

    DoubleFiremodeWeapon (short blueAmmos, short redAmmos, short yellowAmmos,
                          WeaponEffect primaryEffect, WeaponEffect secondaryEffect){
        super(blueAmmos, redAmmos, yellowAmmos, primaryEffect);
        alternativeFiremode = secondaryEffect;
    }
}
