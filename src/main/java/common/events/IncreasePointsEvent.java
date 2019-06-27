package common.events;

import common.dto_model.PcDTO;

public class IncreasePointsEvent extends ModelEvent {

    private PcDTO pc;
    private int earnedPoints;


    public IncreasePointsEvent(PcDTO pc, int earnedPoints){
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
}
