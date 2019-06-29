package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;

import static common.Constants.DEATH;

public class DeathEvent extends PcBoardEvent {

    private int eventID = DEATH;


    public DeathEvent(PcBoardDTO pcBoard){
        super(pcBoard);
    }


    private DeathEvent(PcBoardDTO pcBoard, boolean isPrivate){
        super(pcBoard, isPrivate);
    }


    @Override
    public String toString() {
        return isUncensored
                ? "You"
                : pcBoard.getColour().getName()
                    + " died";
    }


    @Override
    public PcBoardEvent switchToPrivate() {
        return new DeathEvent(pcBoard, true);
    }
}
