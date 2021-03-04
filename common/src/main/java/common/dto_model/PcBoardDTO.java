package common.dto_model;

import common.enums.PcColourEnum;

import static common.Constants.LIFE_POINTS;

public class PcBoardDTO implements DTO {

    private PcColourEnum colour;
    private short points;
    private short damageTrackIndex;
    private short[] marks;
    private short[] ammo;
    private PcColourEnum[] damageTrack;
    private int[] pcValue;
    private int numOfDeaths;


    public PcColourEnum getColour() {
        return colour;
    }

    public void setColour(PcColourEnum colour) {
        this.colour = colour;
    }

    public short getDamageTrackIndex() {
        return damageTrackIndex;
    }

    public void setDamageTrackIndex(short damageTrackIndex) {
        this.damageTrackIndex = damageTrackIndex;
    }

    public short[] getMarks() {
        return marks;
    }

    public void setMarks(short[] marks) {
        this.marks = marks;
    }

    public short[] getAmmo() {
        return ammo;
    }

    public void setAmmo(short[] ammo) {
        this.ammo = ammo;
    }

    public PcColourEnum[] getDamageTrack() {
        return damageTrack;
    }

    public void setDamageTrack(PcColourEnum[] damageTrack) {
        this.damageTrack = damageTrack;
    }

    public int[] getPcValue() {
        return pcValue;
    }

    public void setPcValue(int[] pcValue) {
        this.pcValue = pcValue;
    }

    public int getNumOfDeaths() {
        return numOfDeaths;
    }

    public void setNumOfDeaths(int numOfDeaths) {
        this.numOfDeaths = numOfDeaths;
    }

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public String toString(){

        StringBuilder s = new StringBuilder();
        s.append(System.lineSeparator()).append("Points: ").append(points);
        s.append(System.lineSeparator()).append("Deaths: ").append(numOfDeaths);

        // damage track
        s.append(System.lineSeparator()).append("Your damage track: [");
        for( int i = 0; i < LIFE_POINTS; i++){

            if (i < damageTrackIndex)
                s.append(damageTrack[i]);
            else
                s.append("/");

            if (i < LIFE_POINTS - 1)
                s.append(", ");
        }

        //marks
        s.append("]").append(System.lineSeparator()).append("Your marks: ");
        for (int i = 0; i < PcColourEnum.values().length; i++){
            s.append("[").append(PcColourEnum.values()[i]).append(", ").append(marks[i]).append("]  ");
        }
        return s.toString();
    }
}
