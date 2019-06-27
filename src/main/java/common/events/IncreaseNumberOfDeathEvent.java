package common.events;

import common.dto_model.PcBoardDTO;

public class IncreaseNumberOfDeathEvent extends ModelEvent {

    private PcBoardDTO pcBoard;


    public IncreaseNumberOfDeathEvent(PcBoardDTO pcBoard){
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
}
