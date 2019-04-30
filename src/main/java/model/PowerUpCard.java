package model;

import model.Enumerations.AmmoEnum;

import java.util.HashSet;

public abstract class PowerUpCard {
    private AmmoEnum colour;

    public PowerUpCard(AmmoEnum colour){
        this.colour = colour;
    }

    public AmmoEnum getColour(){ return this.colour; }

    public abstract boolean useEffect();

    //public void useAsAmmo(){
    //  questo metodo sembra inutile: basta inserire le carte powerup da usare come ammo in WeaponCard
    //}

    //public abstract void useToRespawn();    anche questo inutile: il controller gestisce il respawn
}

public class Newton extends PowerUpCard{
    public Newton(AmmoEnum colour){
        super(colour);
    }

    public boolean useEffect(Pc targetPc, Tile destinationTile){
        boolean valid;
        HashSet<Tile> possibleTiles;
        possibleTiles = targetPc.getCurrTile().atDistance(1);
        possibleTiles.addAll(targetPc.getCurrTile().atDistance(2));
        if(possibleTiles.contains(destinationTile)){
            valid = true;
            targetPc.setCurrTile(destinationTile);
            //qua bisogna modificare le liste di pc dei tiles precedente e attuale
        }
        else{
            valid = false;
        }
        return valid;
    }
}
