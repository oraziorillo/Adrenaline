package model.powerUps;

import enums.AmmoEnum;
import model.Pc;
import model.WeaponEffect;
import model.squares.Square;

public class PowerUpCard {

    private boolean selectedAsAmmo;
    private boolean shooting;
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

    public boolean isShooting() {
        return shooting;
    }

    public AmmoEnum getColour(){ return this.colour; }

    public WeaponEffect getEffect() {
        return effect;
    }

    public boolean useEffect(Pc pc, Square square) { return false; }

    public void setSelectedAsAmmo(boolean selectedAsAmmo) {
        this.selectedAsAmmo = selectedAsAmmo;
    }


    //public void useAsAmmo(){
    //  questo metodo sembra inutile: basta inserire le carte powerup da usare come ammo in WeaponCard
    //}

    //public abstract void useToRespawn();    anche questo inutile: il controller gestisce il resetDamageTrack
}

