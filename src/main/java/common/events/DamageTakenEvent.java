package common.events;

import common.dto_model.PcDTO;

import java.util.Arrays;

public class DamageTakenEvent extends ModelEvent {

    private PcDTO pc;
    private String shooter;
    private short damage;


    public DamageTakenEvent(PcDTO pc, String shooter, short damage){
        this.pc = pc;
        this.shooter = shooter;
        this.damage = damage;
    }


    @Override
    public String toString() {
        return pc.getName() + " took " + damage + " damages from " + shooter + "\n" +
                Arrays.toString(pc.getPcBoard().getDamageTrack());
    }

    @Override
    public Object getNewValue() {
        return pc.getPcBoard();
    }
}
