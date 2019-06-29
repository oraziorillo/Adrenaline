package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;

import static common.Constants.ITEM_COLLECTED;

public class ItemCollectedEvent extends SquareEvent {

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
        return isUncensored
                ? "You"
                : pcColour
                + " collected a " + item + " on " + square;
    }


    @Override
    public PcColourEnum getPublisherColour() {
        return pcColour;
    }


    @Override
    public SquareEvent switchToPrivate() {
        return new ItemCollectedEvent(pcColour, square, item, true);
    }
}
