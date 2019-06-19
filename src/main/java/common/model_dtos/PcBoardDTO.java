package common.model_dtos;

import common.enums.PcColourEnum;

public class PcBoardDTO {

    private short points;
    private short numOfDeaths;
    private short damageTrackIndex;
    private short[] marks;
    private short[] ammo;
    private PcColourEnum[] damageTrack;
    private int [] pcValue;
    private int pcValueIndex;


    public short getNumOfDeaths() {
        return numOfDeaths;
    }

    public void setNumOfDeaths(short numOfDeaths) {
        this.numOfDeaths = numOfDeaths;
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

    public int getPcValueIndex() {
        return pcValueIndex;
    }

    public void setPcValueIndex(int pcValueIndex) {
        this.pcValueIndex = pcValueIndex;
    }

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }
}
