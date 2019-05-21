package model.powerUps;

import enums.AmmoEnum;
import model.Pc;
import model.squares.Square;

public class Teleporter extends PowerUpCard {
    public Teleporter(AmmoEnum colour){
        super(colour);
    }

    @Override
    public boolean useEffect(Pc pc, Square square) {
        return false;
    }
}
