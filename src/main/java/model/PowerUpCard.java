package model;

import model.enumerations.AmmoEnum;

import java.util.HashSet;

public abstract class PowerUpCard {
    private AmmoEnum colour;

    public PowerUpCard(AmmoEnum colour){
        this.colour = colour;
    }

    public AmmoEnum getColour(){ return this.colour; }

    public abstract boolean useEffect(Pc pc, Tile tile);

    //public void useAsAmmo(){
    //  questo metodo sembra inutile: basta inserire le carte powerup da usare come ammo in WeaponCard
    //}

    //public abstract void useToRespawn();    anche questo inutile: il controller gestisce il respawn
}

class Newton extends PowerUpCard{
    public Newton(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc targetPc, Tile destinationTile){
        boolean valid;
        HashSet<Tile> possibleTiles;
        possibleTiles = targetPc.getCurrTile().atDistance(1);
        possibleTiles.addAll(targetPc.getCurrTile().atDistance(2));
        if(possibleTiles.contains(destinationTile)){
            valid = true;
            targetPc.moveTo(destinationTile);
            //qua bisogna modificare le liste di pc dei tiles precedente e attuale
        }
        else{
            valid = false;
        }
        return valid;
    }
}

class TargetingScope extends PowerUpCard{
    public TargetingScope(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Tile tile) {
        return false;
    }

}

class TagbackGrenade extends PowerUpCard{
    public TagbackGrenade(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Tile tile) {
        return false;
    }
}

class Teleporter extends PowerUpCard{
    public Teleporter(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Tile tile) {
        return false;
    }
}
