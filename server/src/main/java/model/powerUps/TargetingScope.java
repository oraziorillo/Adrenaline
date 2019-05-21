package model.powerUps;

import enums.AmmoEnum;
import model.Pc;
import model.squares.Square;

public class TargetingScope extends PowerUpCard {
    public TargetingScope(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Square square) {
        return false;
    }

}
