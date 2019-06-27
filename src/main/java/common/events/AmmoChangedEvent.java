package common.events;

import common.dto_model.PcDTO;
import common.enums.AmmoEnum;

import java.util.List;

import static common.Constants.AMMO_CHANGED;

public class AmmoChangedEvent extends ModelEvent{

    private PcDTO pc;
    private short[] ammoDifference;
    private List<String> powerUpsDiscarded;
    private boolean isEarned;


    public AmmoChangedEvent(PcDTO pc, short[] ammoDifference, List<String> powerUpsDiscarded, boolean isEarned){
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
        StringBuilder stringBuilder = new StringBuilder();
        for (AmmoEnum a : AmmoEnum.values())
            stringBuilder.append(isEarned ? "+ " : "- ").append(ammoDifference[a.ordinal()]).append(" ").append(a).append(" ammo\n");
        return stringBuilder.toString();
    }

    @Override
    public Object getNewValue() {
        return pc.getPcBoard();
    }


    @Override
    public String getPropertyName() {
        return AMMO_CHANGED;
    }
}
