package common.events.pc_events;

import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.WeaponCardDTO;
import common.events.ModelEvent;
import server.model.Pc;

import java.util.ArrayList;

import static common.Constants.MAX_POWER_UPS_IN_HAND;
import static common.Constants.MAX_WEAPONS_IN_HAND;

public abstract class PcEvent implements ModelEvent {

    PcDTO pc;
    boolean censored;


    PcEvent(PcDTO pc) {
        this.pc = pc;
    }


    PcEvent(PcDTO pc, boolean censored) {
        this.pc = pc;
        this.censored = censored;
    }


    @Override
    public PcDTO getDTO() {
        return pc;
    }


    PcDTO getCensoredDTO() {
        PcDTO censoredPcDTO = new Pc(pc.getColour(), null).convertToDTO();

        WeaponCardDTO[] censoredWeapons = new WeaponCardDTO[MAX_WEAPONS_IN_HAND];
        WeaponCardDTO tmp;
        for (int i = 0; i < MAX_WEAPONS_IN_HAND; i++) {
            tmp = pc.getWeapons()[i];
            if (tmp != null && tmp.isLoaded())
                censoredWeapons[i] = WeaponCardDTO.getCardBack();
            else if (tmp != null)
                censoredWeapons[i] = tmp;
        }
        censoredPcDTO.setWeapons(censoredWeapons);

        ArrayList<PowerUpCardDTO> censoredPowerUps = new ArrayList<>();
        for (int i = 0; i < MAX_POWER_UPS_IN_HAND; i++) {
            if (i < pc.getPowerUps().size())
                censoredPowerUps.add(PowerUpCardDTO.getCardBack());
        }
        censoredPcDTO.setPowerUps(censoredPowerUps);

        censoredPcDTO.setSquareRow(pc.getSquareRow());
        censoredPcDTO.setSquareCol(pc.getSquareCol());
        censoredPcDTO.setAdrenaline(pc.getAdrenaline());
        censoredPcDTO.setPcBoard(pc.getPcBoard());
        return censoredPcDTO;
    }
    
    public boolean isCensored() {
        return censored;
    }
    
    public abstract PcEvent hideSensibleContent();
}
