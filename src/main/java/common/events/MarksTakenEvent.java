package common.events;

import common.dto_model.PcDTO;

import java.util.Arrays;

public class MarksTakenEvent extends ModelEvent {

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
    public Object getNewValue() {
        return pc.getPcBoard();
    }
}
