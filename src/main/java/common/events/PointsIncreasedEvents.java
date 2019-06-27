package common.events;

import common.dto_model.PcDTO;

import static common.Constants.POINTS_INCREASED;

public class PointsIncreasedEvents extends ModelEvent {

    private PcDTO pc;
    private int earnedPoints;


    public PointsIncreasedEvents(PcDTO pc, int earnedPoints){
        this.pc = pc;
        this.earnedPoints = earnedPoints;
    }


    @Override
    public String toString() {
        return pc.getName() + " gained " + earnedPoints + " points";
    }


    @Override
    public Object getNewValue() {
        return pc.getPcBoard();
    }


    @Override
    public String getPropertyName() {
        return POINTS_INCREASED;
    }
}
