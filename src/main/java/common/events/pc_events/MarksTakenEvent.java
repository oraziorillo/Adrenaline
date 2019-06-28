package common.events.pc_events;

import common.dto_model.PcDTO;

import java.util.Arrays;

import static common.Constants.MARKS_TAKEN;

public class MarksTakenEvent extends PcEvent {

    private PcDTO pc;
    private String shooter;
    private int marks;

    public MarksTakenEvent(PcDTO pc, String shooter, short marks){
        this.pc = pc;
        this.shooter = shooter;
        this.marks = marks;
    }


    @Override
    public String toString() {
        return pc.getName() + " took " + marks + " marks from " + shooter + "\n" +
                Arrays.toString(pc.getPcBoard().getDamageTrack());
    }

    @Override
    public PcDTO getNewValue() {
        return pc;
    }
    
    @Override
    public String getPropertyName() {
        return MARKS_TAKEN;
    }
}
