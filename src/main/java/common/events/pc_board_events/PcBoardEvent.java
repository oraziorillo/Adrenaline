package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;
import common.events.ModelEvent;

public abstract class PcBoardEvent implements ModelEvent {

    PcBoardDTO pcBoard;
    boolean isUncensored;


    PcBoardEvent(PcBoardDTO pcBoard) {
        this.pcBoard = pcBoard;
        this.isUncensored = false;
    }


    PcBoardEvent(PcBoardDTO pcBoard, boolean isUncensored){
        this.pcBoard = pcBoard;
        this.isUncensored = isUncensored;
    }


    @Override
    public PcBoardDTO getDTO(){
        return pcBoard;
    }


    public abstract PcBoardEvent switchToPrivate();
}
