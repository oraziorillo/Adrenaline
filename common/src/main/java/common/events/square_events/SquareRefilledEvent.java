package common.events.square_events;

import common.dto_model.SquareDTO;
import common.dto_model.WeaponCardDTO;

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
        StringBuilder squareRefilled = new StringBuilder(System.lineSeparator());
        if (isSpawnPoint) {
            StringBuilder weaponsString = new StringBuilder();
            int i = 1;
            for (WeaponCardDTO w : square.getWeapons()) {
                weaponsString.append(System.lineSeparator()).append("[").append(i).append("] ").append(w.getName());
                i++;
            }
            squareRefilled.append(square.toString()).append(" refilled").append(System.lineSeparator()).append("Now there are these weapon cards on it:")
                    .append(weaponsString.toString());
        } else
            squareRefilled.append(square.toString()).append(" refilled").append(System.lineSeparator()).append("Now there is a:")
                    .append(square.getAmmoTile().toString()).append(System.lineSeparator()).append("ammo tile on it");
        return squareRefilled.toString();
    }


    @Override
    public SquareEvent censor() {
        return new SquareRefilledEvent(square, isSpawnPoint, true);
    }
}
