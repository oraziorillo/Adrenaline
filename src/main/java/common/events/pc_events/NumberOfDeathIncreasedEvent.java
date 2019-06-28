package common.events.pc_events;

import common.dto_model.PcBoardDTO;
import common.dto_model.PcDTO;

import static common.Constants.NUMBER_OF_DEATH_INCREASED;

public class NumberOfDeathIncreasedEvent extends PcEvent {

    private PcDTO pc;


    public NumberOfDeathIncreasedEvent(PcDTO pcBoard){
        this.pc = pcBoard;
    }


    @Override
    public String toString() {
        return "";
    }


    @Override
    public PcDTO getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return NUMBER_OF_DEATH_INCREASED;
    }
}
