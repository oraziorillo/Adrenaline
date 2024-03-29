package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;

import static common.Constants.ITEM_COLLECTED;

public class ItemCollectedEvent extends SquareEvent {
    
    /**
     * to deserialize
     */
    private int eventID = ITEM_COLLECTED;
    private PcColourEnum pcColour;
    private String item;


    public ItemCollectedEvent(PcColourEnum pcColour, SquareDTO square, String item){
        super(square);
        this.pcColour = pcColour;
        this.item = item;
    }


    private ItemCollectedEvent(PcColourEnum pcColour, SquareDTO square, String item, boolean isPrivate){
        super(square, isPrivate);
        this.pcColour = pcColour;
        this.item = item;
    }


    @Override
    public String toString() {
        return (isPrivate
                ? pcColour.getName()
                : "You")
                + " collected a " + item + " on " + square;
    }


    @Override
    public SquareEvent censor() {
        return new ItemCollectedEvent(pcColour, square, item, true);
    }
}
