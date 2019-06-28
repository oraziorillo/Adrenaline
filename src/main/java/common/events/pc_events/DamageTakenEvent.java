package common.events.pc_events;

import common.dto_model.PcDTO;

import java.util.Arrays;

import static common.Constants.DAMAGE_TAKEN;

public class DamageTakenEvent extends PcEvent {

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
    public PcDTO getNewValue() {
        return pc;
    }


    @Override
    public String getPropertyName() {
        return DAMAGE_TAKEN;
    }
}
