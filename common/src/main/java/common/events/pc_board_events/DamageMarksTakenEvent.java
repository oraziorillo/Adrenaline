package common.events.pc_board_events;

import common.dto_model.PcBoardDTO;

import java.util.Arrays;

import static common.Constants.DAMAGE_MARKS_TAKEN;

public class DamageMarksTakenEvent extends PcBoardEvent{

    private int eventID = DAMAGE_MARKS_TAKEN;
    private String shooter;
    private short damage;
    private short marks;


    public DamageMarksTakenEvent(PcBoardDTO pcBoard, String shooter, short damage, short marks){
        super(pcBoard);
        this.shooter = shooter;
        this.damage = damage;
        this.marks = marks;
    }


    private DamageMarksTakenEvent(PcBoardDTO pcBoard, String shooter, short damage, short marks, boolean isPrivate){
        super(pcBoard, isPrivate);
        this.shooter = shooter;
        this.damage = damage;
        this.marks = marks;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(
                (censored
                    ? pcBoard.getColour().getName()
                    : "You"))
                .append(" took ");
        if (damage > 0 && marks > 0)
            builder.append(damage).append(" damages and ").append(marks).append(" marks from ");
        else if (damage > 0 && marks == 0)
            builder.append(damage).append(" damages from ");
        else
            builder.append(marks).append(" marks from ");
        builder.append(shooter)
                .append("\n\n")
                .append(Arrays.toString(pcBoard.getDamageTrack()));

        return builder.toString();
    }


    @Override
    public PcBoardEvent censor() {
        return new DamageMarksTakenEvent(pcBoard, shooter, damage, marks,true);
    }
}
