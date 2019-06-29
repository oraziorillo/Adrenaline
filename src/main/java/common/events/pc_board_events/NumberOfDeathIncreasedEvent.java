package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;

import static common.Constants.NUMBER_OF_DEATH_INCREASED;

public class NumberOfDeathIncreasedEvent extends PcBoardEvent {

    private int eventID = NUMBER_OF_DEATH_INCREASED;


    public NumberOfDeathIncreasedEvent(PcBoardDTO pcBoard){
        super(pcBoard);
    }


    private NumberOfDeathIncreasedEvent(PcBoardDTO pcBoard, boolean isPrivate){
        super(pcBoard, isPrivate);
    }


    @Override
    public String toString() {
        return "";
    }


    @Override
    public PcBoardEvent switchToPrivate() {
        return new NumberOfDeathIncreasedEvent(pcBoard, true);
    }
}
