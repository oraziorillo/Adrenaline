package model;

import enums.PcColourEnum;
import static model.Constants.AMMO_COLOURS_NUMBER;
import static model.Constants.LIFEPOINTS;

public class PcBoard {

    private short points;
    private short numOfDeaths;
    private short damageTrackIndex;
    private short[] marks;
    private short[] ammo;
    private PcColourEnum[] damageTrack;

    public PcBoard(){
        this.points = 0;
        this.numOfDeaths = 0;
        this.damageTrackIndex = 0;
        this.marks = new short[5];
        this.ammo = new short[3];
        this.damageTrack = new PcColourEnum[LIFEPOINTS];
        for(int i = 0; i < 3; i++){
            ammo[i] = 1;
        }
    }


    public short getPoints() {
        return points;
    }


    public short getNumOfDeaths() {
        return numOfDeaths;
    }


    public short[] getAmmo() {
        return ammo;
    }


    public PcColourEnum[] getDamageTrack() {
        return damageTrack;
    }


    public short getDamageTrackIndex() {
        return damageTrackIndex;
    }


    public short getMarks(PcColourEnum selectedColour) {
        return marks[selectedColour.ordinal()];
    }

    public void increasePoints(int earnedPoints){
        this.points += earnedPoints;
    }

    public void increaseNumberOfDeaths(){
        numOfDeaths++;
    }


    public void addDamage(PcColourEnum shooterColour, short numOfDamage){
        while (numOfDamage != 0) {
            if (damageTrackIndex == LIFEPOINTS)
                break;
            damageTrack[damageTrackIndex] = shooterColour;
            damageTrackIndex++;
            numOfDamage--;
        }
    }


    public void addMarks(PcColourEnum shooterColour, short numOfMarks) {
        if (marks[shooterColour.ordinal()] + numOfMarks > 3)
            marks[shooterColour.ordinal()] = 3;
        else
            marks[shooterColour.ordinal()] += numOfMarks;
    }


    public void addAmmo(AmmoTile ammoTile) {
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++) {
            this.ammo[i] += ammoTile.getAmmo()[i];
            if (ammo[i] > 3)
                ammo[i] = 3;
        }
    }


    public boolean hasAtLeastOneAmmo() {
        for (int i = 0; i < 3; i++){
            if(ammo[i] > 0)
                return true;
        }
        return false;
    }

    /**
     * reverse of addAmmo
     * @param ammo some ammos
     * @return ammo, where every succesfully payed ammo has been removed (so it will be a "not payed" array)
     */
    public short[] payAmmo(short[] ammo) {
        for (int i = 0; i < 3; i++){
            if (this.ammo[i] >= ammo[i]){
                this.ammo[i] -= ammo[i];
                ammo[i] = 0;
            } else {
                ammo[i] -= this.ammo[i];
                this.ammo[i] = 0;
            }
        }
        return ammo;
    }


    public void resetDamageTrack(){
        damageTrackIndex = 0;
        damageTrack = new PcColourEnum[LIFEPOINTS];
    }

}
