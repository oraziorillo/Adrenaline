package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;
import common.enums.AmmoEnum;

import java.util.List;

import static common.Constants.AMMO_CHANGED;

public class AmmoChangedEvent extends PcBoardEvent {

    private int eventID = AMMO_CHANGED;
    private short[] ammoDifference;
    private List<String> powerUpsDiscarded;
    private boolean isEarned;


    public AmmoChangedEvent(PcBoardDTO pcBoard, short[] ammoDifference, List<String> powerUpsDiscarded, boolean isEarned){
        super(pcBoard);
        this.ammoDifference = ammoDifference;
        this.powerUpsDiscarded = powerUpsDiscarded;
        this.isEarned = isEarned;
    }


    private AmmoChangedEvent(PcBoardDTO pcBoard, short[] ammoDifference, List<String> powerUpsDiscarded, boolean isEarned, boolean isPrivate){
        super(pcBoard, isPrivate);
        this.ammoDifference = ammoDifference;
        this.powerUpsDiscarded = powerUpsDiscarded;
        this.isEarned = isEarned;
    }


    private String ammoDifferenceToString(short[] ammoDifference){
        StringBuilder stringBuilder = new StringBuilder();
        for (AmmoEnum a : AmmoEnum.values())
            stringBuilder.append(isEarned ? "+ " : "- ").append(ammoDifference[a.ordinal()]).append(" ").append(a).append(" ammo\n");
        return stringBuilder.toString();
    }


    @Override
    public String toString() {
        StringBuilder dynamicMessageBuilder = new StringBuilder();
        dynamicMessageBuilder.append(
                isUncensored
                ? "You"
                : pcBoard.getColour().getName());
        dynamicMessageBuilder.append(
                isEarned
                ? " earned:\n" + ammoDifferenceToString(ammoDifference)
                : " paid:\n" + ammoDifferenceToString(ammoDifference)
                        + "and used:\n" + powerUpsDiscarded.toString() + "\nto pay the difference");
        return dynamicMessageBuilder.toString();
    }


    @Override
    public PcBoardEvent switchToPrivate() {
        return new AmmoChangedEvent(pcBoard, ammoDifference, powerUpsDiscarded, isEarned, true);
    }
}
