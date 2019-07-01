package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;
import common.events.ModelEvent;

public abstract class PcBoardEvent implements ModelEvent {

    PcBoardDTO pcBoard;
    boolean censored;


    PcBoardEvent(PcBoardDTO pcBoard) {
        this.pcBoard = pcBoard;
        this.censored = false;
    }


    PcBoardEvent(PcBoardDTO pcBoard, boolean censored){
        this.pcBoard = pcBoard;
        this.censored = censored;
    }


    @Override
    public PcBoardDTO getDTO(){
        return pcBoard;
    }


    public abstract PcBoardEvent switchToPrivate();
}
