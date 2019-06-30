package common.events.pc_events;

import common.dto_model.PcDTO;
import common.events.ModelEvent;
import server.model.Pc;

public abstract class PcEvent implements ModelEvent {

    PcDTO pc;
    boolean isUncensored;


    PcEvent(PcDTO pc) {
        this.pc = pc;
    }


    PcEvent(PcDTO pc, boolean isUncensored) {
        this.pc = pc;
        this.isUncensored = isUncensored;
    }


    @Override
    public PcDTO getDTO() {
        return pc;
    }


    PcDTO getCensoredDTO() {
        PcDTO censoredPcDTO = new Pc(pc.getColour(), null).convertToDTO();
        censoredPcDTO.setSquareRow(pc.getSquareRow());
        censoredPcDTO.setSquareCol(pc.getSquareCol());
        censoredPcDTO.setAdrenaline(pc.getAdrenaline());
        censoredPcDTO.setPcBoard(pc.getPcBoard());
        return censoredPcDTO;
    }


    public abstract PcEvent hideSensibleContent();
}
