package common.events;

import common.dto_model.SquareDTO;

public class MapSetEvent extends ModelEvent {

    private SquareDTO[][] map;
    private int numberOfMap;


    public MapSetEvent(SquareDTO[][] map, int numberOfMap){
        this.map = map;
        this.numberOfMap = numberOfMap;
    }


    @Override
    public String toString() {
        return "Map " + numberOfMap + " has been set";
    }


    @Override
    public Object getNewValue() {
        return map;
    }
}
