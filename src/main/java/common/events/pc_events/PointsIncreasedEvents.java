package common.events.pc_events;

import common.dto_model.PcDTO;

import static common.Constants.POINTS_INCREASED;

public class PointsIncreasedEvents extends PcEvent {

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
    public PcDTO getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return POINTS_INCREASED;
    }
}
