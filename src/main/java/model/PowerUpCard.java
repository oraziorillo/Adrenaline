package model;

import model.enumerations.AmmoEnum;

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

class Newton extends PowerUpCard{
    public Newton(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc targetPc, Square destinationSquare){
        boolean valid;
        HashSet<Square> possibleSquares;
        possibleSquares = targetPc.getCurrSquare().atDistance(1);
        possibleSquares.addAll(targetPc.getCurrSquare().atDistance(2));
        if(possibleSquares.contains( destinationSquare )){
            valid = true;
            targetPc.moveTo( destinationSquare );
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
    public boolean useEffect(Pc pc, Square square) {
        return false;
    }

}

class TagbackGrenade extends PowerUpCard{
    public TagbackGrenade(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Square square) {
        return false;
    }
}

class Teleporter extends PowerUpCard{
    public Teleporter(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Square square) {
        return false;
    }
}
