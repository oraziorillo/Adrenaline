package model.target_checker;

import model.CardinalDirectionEnum;
import model.Pc;
import model.Tile;

import java.util.Optional;

public class SimpleStraightLineDecorator extends TargetCheckerDecorator{
    private CardinalDirectionEnum direction;
    public SimpleStraightLineDecorator(TargetChecker decorated, CardinalDirectionEnum selectedDirection){
        super(decorated);
        this.direction = selectedDirection;
    }

    public boolean isValid(Pc possibleTarget) {
        boolean valid = false;
        Tile attackerTile, possibleTargetTile;
        Optional<Tile> temp;
        attackerTile = game.getCurrentCharacter().getCurrentTile();
        possibleTargetTile = possibleTarget.getCurrentTile();
        if(attackerTile.equals(possibleTargetTile)){
            valid = true;
        }
        else {
            do {
                temp = attackerTile.OnDirection(direction);
                if (temp.isEmpty()) break;
                valid = temp.equals(possibleTargetTile);
            } while (temp.isPresent() && !valid);
        }
        return base.isValid(possibleTarget) && valid;
    }
}
