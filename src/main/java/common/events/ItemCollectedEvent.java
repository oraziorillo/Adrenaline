package common.events;

import common.dto_model.SquareDTO;

import static common.Constants.ITEM_COLLECTED;

public class ItemCollectedEvent extends ModelEvent {

    private String pcName;
    private SquareDTO square;
    private String item;


    public ItemCollectedEvent(String pcName, SquareDTO square, String item){
        this.pcName = pcName;
        this.square = square;
        this.item = item;
    }


    @Override
    public String toString() {
        return pcName + " collected a " + item + " on " + square;
    }

    @Override
    public Object getNewValue() {
        return square;
    }

    @Override
    public String getPropertyName() {
        return ITEM_COLLECTED;
    }
}
