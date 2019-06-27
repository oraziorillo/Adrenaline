package common.events;

import common.dto_model.PcDTO;
import common.enums.AmmoEnum;

import java.util.List;

public class AmmoChangeEvent extends ModelEvent{

    private PcDTO pc;
    private short[] ammoDifference;
    private List<String> powerUpsDiscarded;
    private boolean isEarned;


    public AmmoChangeEvent(PcDTO pc, short[] ammoDifference, List<String> powerUpsDiscarded, boolean isEarned){
        setPrivateMessage(true);
        this.pc = pc;
        this.ammoDifference = ammoDifference;
        this.powerUpsDiscarded = powerUpsDiscarded;
        this.isEarned = isEarned;
    }


    @Override
    public String toString() {
        if (isEarned)
            return pc.getName() + " earned:\n" + ammoDifferenceToString(ammoDifference);
        else
            return pc.getName() + " paid:\n" + ammoDifferenceToString(ammoDifference)
                    + "and used:\n" + powerUpsDiscarded.toString() + "\nto pay the difference";
    }


    private String ammoDifferenceToString(short[] ammoDifference){
        StringBuilder finalString = new StringBuilder();
        for (AmmoEnum a : AmmoEnum.values())
            finalString.append(isEarned ? "+ " : "- ").append(ammoDifference[a.ordinal()]).append(" ").append(a).append(" ammo\n");
        return finalString.toString();
    }

    @Override
    Object getNewValue() {
        return pc;
    }
}
