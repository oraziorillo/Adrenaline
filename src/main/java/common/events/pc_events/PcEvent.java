package common.events.pc_events;

import common.dto_model.PcDTO;
import common.events.ModelEvent;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;
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
        Pc censoredPc = new Pc(pc.getColour(), null);
        ModelMapper modelMapper = new CustomizedModelMapper().getModelMapper();
        PcDTO censoredPcDTO = modelMapper.map(censoredPc, PcDTO.class);
        censoredPcDTO.setCurrSquare(pc.getCurrSquare());
        censoredPcDTO.setAdrenaline(pc.getAdrenaline());
        censoredPcDTO.setPcBoard(pc.getPcBoard());
        return censoredPcDTO;
    }


    public abstract PcEvent hideSensibleContent();
}
