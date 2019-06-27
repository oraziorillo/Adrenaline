package common.events;

import common.dto_model.PcBoardDTO;

import static common.Constants.NUMBER_OF_DEATH_INCREASED;

public class NumberOfDeathIncreasedEvent extends ModelEvent {

    private PcBoardDTO pcBoard;


    public NumberOfDeathIncreasedEvent(PcBoardDTO pcBoard){
        this.pcBoard = pcBoard;
    }


    @Override
    public String toString() {
        return "";
    }


    @Override
    public Object getNewValue() {
        return pcBoard;
    }


    @Override
    public String getPropertyName() {
        return NUMBER_OF_DEATH_INCREASED;
    }
}
