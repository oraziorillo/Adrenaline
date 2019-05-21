package model.powerUps;

import enums.AmmoEnum;
import model.Pc;
import model.squares.Square;

import java.util.HashSet;

public abstract class PowerUpCard {
    private AmmoEnum colour;

    public PowerUpCard(AmmoEnum colour){
        this.colour = colour;
    }

    public AmmoEnum getColour(){ return this.colour; }

    public abstract boolean useEffect(Pc pc, Square square);

    //public void useAsAmmo(){
    //  questo metodo sembra inutile: basta inserire le carte powerup da usare come ammo in WeaponCard
    //}

    //public abstract void useToRespawn();    anche questo inutile: il controller gestisce il resetDamageTrack
}

