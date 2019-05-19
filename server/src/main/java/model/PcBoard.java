package model;

import enums.AmmoEnum;
import enums.PcColourEnum;
import exceptions.NotEnoughAmmoException;
import static model.Constants.LIFEPOINTS;

public class PcBoard {

    private short points;
    private short numOfDeaths;
    private short damageTrackIndex;     //is the index of the last inserted element
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
            ammo[i] = 1;       //ogni giocatore appena creato possiede una munizione per ogni colore
        }
    }

    public short getPoints() {
        return points;
    }

    public short getNumOfDeaths() {
        return numOfDeaths;
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

    public void addMarks(PcColourEnum selectedColour, short numOfMarks) {
        marks[selectedColour.ordinal()] = numOfMarks;
    }

    public void addAmmos(AmmoTile card) {
        for (int i = 0; i < card.getAmmos().length; i++) {
            this.ammo[i] += ammo[i];
            if (ammo[i] > 3)
                ammo[i] = 3;
        }
    }

    public boolean hasEnoughAmmo(short[] ammos){
        for(short i : ammos){
            if(this.ammo[i] < ammos[i])
                return false;
        }
        return true;
    }

    public void payAmmo(short[] ammo) throws NotEnoughAmmoException {
        if (!hasEnoughAmmo(ammo)) {
            throw new NotEnoughAmmoException();
        }
        this.ammo[AmmoEnum.BLUE.ordinal()] -= ammo[AmmoEnum.BLUE.ordinal()];
        this.ammo[AmmoEnum.RED.ordinal()] -= ammo[AmmoEnum.RED.ordinal()];
        this.ammo[AmmoEnum.YELLOW.ordinal()] -= ammo[AmmoEnum.YELLOW.ordinal()];
    }

    public void addDamage(PcColourEnum selectedColour, short numOfDamage){
        while (numOfDamage != 0) {
            if (damageTrackIndex == LIFEPOINTS)
                break;
            damageTrack[damageTrackIndex] = selectedColour;
            damageTrackIndex++;
        }
    }

    public void resetDamageTrack(){
        damageTrackIndex = 0;
        damageTrack = new PcColourEnum[LIFEPOINTS];
    }


}
