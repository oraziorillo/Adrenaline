package model.powerUps;

import enums.AmmoEnum;
import model.Pc;
import model.squares.Square;

import java.util.HashSet;

public class Newton extends PowerUpCard {
    public Newton(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc targetPc, Square destinationSquare){
        boolean valid;
        HashSet<Square> possibleSquares;
        possibleSquares = targetPc.getCurrSquare().atDistance(1);
        possibleSquares.addAll(targetPc.getCurrSquare().atDistance(2));
        if(possibleSquares.contains(destinationSquare)){
            valid = true;
            targetPc.moveTo(destinationSquare);
            //qua bisogna modificare le liste di pc dei tiles precedente e attuale
        }
        else{
            valid = false;
        }
        return valid;
    }
}
