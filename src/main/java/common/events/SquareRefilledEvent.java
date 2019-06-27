package common.events;

import common.dto_model.SquareDTO;

import java.util.Arrays;

import static common.Constants.SQUARE_REFILLED;

public class SquareRefilledEvent extends ModelEvent {

    private SquareDTO square;
    private boolean isSpawnPoint;


    public SquareRefilledEvent(SquareDTO square, boolean isSpawnPoint){
        this.square = square;
        this.isSpawnPoint = isSpawnPoint;
    }


    @Override
    public String toString() {
        return isSpawnPoint
                ? square.toString() + " refilled\nNow there are these weapon cards on it:\n" + Arrays.toString(square.getWeapons())
                : square.toString() + " refilled\nNow there is a:\n" + square.getAmmoTile().toString() + "\nammo tile on it";
    }


    @Override
    public Object getNewValue() {
        return square;
    }


    @Override
    public String getPropertyName() {
        return SQUARE_REFILLED;
    }
}
