package common.events.square_events;

import common.dto_model.SquareDTO;

import java.util.Arrays;

import static common.Constants.SQUARE_REFILLED;

public class SquareRefilledEvent extends SquareEvent {

    private int eventID = SQUARE_REFILLED;
    private boolean isSpawnPoint;


    public SquareRefilledEvent(SquareDTO square, boolean isSpawnPoint){
        super(square);
        this.isSpawnPoint = isSpawnPoint;
    }


    private SquareRefilledEvent(SquareDTO square, boolean isSpawnPoint, boolean isPrivate){
        super(square, isPrivate);
        this.isSpawnPoint = isSpawnPoint;
    }


    @Override
    public String toString() {
        return isSpawnPoint
                ? square.toString() + " refilled\nNow there are these weapon cards on it:\n" + Arrays.toString(square.getWeapons())
                : square.toString() + " refilled\nNow there is a:\n" + square.getAmmoTile().toString() + "\nammo tile on it";
    }


    @Override
    public SquareEvent switchToPrivate() {
        return new SquareRefilledEvent(square, isSpawnPoint, true);
    }
}
