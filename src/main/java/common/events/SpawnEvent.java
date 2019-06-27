package common.events;

import common.dto_model.SquareDTO;

public class SpawnEvent extends ModelEvent {

    private String pcName;
    private SquareDTO newPos;


    public SpawnEvent(String pcName, SquareDTO newPos){
        this.pcName = pcName;
        this.newPos = newPos;
    }


    @Override
    public String toString() {
        return pcName + " spawned on " + newPos.toString();
    }


    @Override
    public Object getNewValue() {
        return newPos;
    }
}