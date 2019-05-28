package model;

import enums.AmmoEnum;
import model.WeaponEffect;

public class PowerUpCard {

    private boolean selectedAsAmmo;
    private AmmoEnum colour;
    private WeaponEffect effect;

    public PowerUpCard(AmmoEnum colour){
        //inizializzazione fatta con jSon
        this.colour = colour;
        this.selectedAsAmmo = false;
        //TODO inizializzare l'effect
    }

    public boolean isSelectedAsAmmo() {
        return selectedAsAmmo;
    }

    public AmmoEnum getColour(){ return this.colour; }

    public WeaponEffect getEffect() {
        return effect;
    }

    public void setSelectedAsAmmo(boolean selectedAsAmmo) {
        this.selectedAsAmmo = selectedAsAmmo;
    }


    //public void useAsAmmo(){
    //  questo metodo sembra inutile: basta inserire le carte powerup da usare come ammo in WeaponCard
    //}

    //public abstract void useToRespawn();    anche questo inutile: il controller gestisce il resetDamageTrack
}

