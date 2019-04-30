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

    public abstract void useAsAmmo();

    public abstract void useToRespawn();
}

public class Newton extends PowerUpCard{
    public Newton(AmmoEnum colour){
        super(colour);
    }

    public boolean useEffect(Pc targetPc, Tile destinationTile){
        boolean valid = false;
        HashSet<Tile> possibleTiles;
        possibleTiles = targetPc.getCurrTile().atDistance(1);
        possibleTiles.addAll(targetPc.getCurrTile().atDistance(2));
        if(possibleTiles.contains(destinationTile)){
            valid = true;

        }

    }
}
