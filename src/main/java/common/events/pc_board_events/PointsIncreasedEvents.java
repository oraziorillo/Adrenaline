package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;

import static common.Constants.POINTS_INCREASED;

public class PointsIncreasedEvents extends PcBoardEvent {

    private int eventID = POINTS_INCREASED;
    private int earnedPoints;


    public PointsIncreasedEvents(PcBoardDTO pcBoard, int earnedPoints){
        super(pcBoard);
        this.earnedPoints = earnedPoints;
    }


    private PointsIncreasedEvents(PcBoardDTO pcBoard, int earnedPoints, boolean isPrivate){
        super(pcBoard, isPrivate);
        this.earnedPoints = earnedPoints;
    }

    @Override
    public String toString() {
        return (censored
                ? pcBoard.getColour().getName()
                : "You")
                    + " gained " + earnedPoints + " points";
    }


    @Override
    public PcBoardEvent switchToPrivate() {
        return new PointsIncreasedEvents(pcBoard, earnedPoints, true);
    }
}
