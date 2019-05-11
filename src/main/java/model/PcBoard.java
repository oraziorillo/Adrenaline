package model;

import model.enumerations.AmmoEnum;
import model.enumerations.PcColourEnum;
import exceptions.NotEnoughAmmosException;
import static model.Constants.LIFEPOINTS;

public class PcBoard {

    private short points;
    private short numOfDeaths;
    private short damageTrackIndex;     //is the index of the last inserted element
    private short[] marks;
    private short[] ammos;
    private PcColourEnum[] damageTrack;

    public PcBoard(){
        this.points = 0;
        this.numOfDeaths = 0;
        this.damageTrackIndex = 0;
        this.marks = new short[5];
        this.ammos = new short[3];
        this.damageTrack = new PcColourEnum[LIFEPOINTS];
        for(int i = 0; i < 3; i++){
            ammos[i] = 1;       //ogni giocatore appena creato possiede una munizione per ogni colore
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

    public void addAmmos(AmmoCard card) {
        for (int i = 0; i < card.getAmmos().length; i++) {
            this.ammos[i] += ammos[i];
            if (ammos[i] > 3)
                ammos[i] = 3;
        }
    }

    public void payAmmos(short[] ammos) throws NotEnoughAmmosException {
        if (this.ammos[AmmoEnum.BLUE.ordinal()] < ammos[AmmoEnum.BLUE.ordinal()] ||
                this.ammos[AmmoEnum.RED.ordinal()] < ammos[AmmoEnum.RED.ordinal()] ||
                this.ammos[AmmoEnum.YELLOW.ordinal()] < ammos[AmmoEnum.YELLOW.ordinal()]) {
            throw new NotEnoughAmmosException();
        }
        this.ammos[AmmoEnum.BLUE.ordinal()] -= ammos[AmmoEnum.BLUE.ordinal()];
        this.ammos[AmmoEnum.RED.ordinal()] -= ammos[AmmoEnum.RED.ordinal()];
        this.ammos[AmmoEnum.YELLOW.ordinal()] -= ammos[AmmoEnum.YELLOW.ordinal()];
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
