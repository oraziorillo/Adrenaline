package model;

import model.Enumerations.AmmoEnum;
import model.Enumerations.PcColourEnum;
import model.Exceptions.NotEnoughAmmosException;

import static model.Constants.LIFEPOINTS;

public class PcBoard {

    private short points;
    private short numOfDeath;
    private short damageTrackIndex;     //è l'indice dell'ultimo elemento inserito
    private short[] marks;
    private short[] ammos;
    private PcColourEnum[] damageTrack;

    public PcBoard(){
        this.points = 0;
        this.numOfDeath = 0;
        this.damageTrackIndex = 0;
        this.marks = new marks[5];
        this.ammos = new ammos[3];
        this.damageTrack = new PcColourEnum[LIFEPOINTS];
    }

    public short getNumOfDeath() {
        return numOfDeath;
    }

    public short getPoints() {
        return points;
    }

    public void increasePoints(int earnedPoints){
        this.points += earnedPoints;
    }

    public void addMarks(PcColourEnum selectedColour, short numOfMarks) {
        marks[selectedColour.ordinal()] = numOfMarks;
    }

    public short getMarks(PcColourEnum selectedColour) {
        return marks[selectedColour.ordinal()];
    }

    public short getDamageTrackIndex() {
        return damageTrackIndex;
    }

    public void addAmmos(AmmoCard card) {
        for (int i = 0; i < card.getAmmos().length; i++) {
            this.ammos[i] += ammos[i];
            if (ammos[i] > 3)
                ammos[i] = 3;
        }
    }

    /**
     * @param ammos
     * @throws NotEnoughAmmosException
     */
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

    public void respawn(){
        numOfDeath++;
        damageTrackIndex = 0;
        damageTrack = new PcColourEnum[LIFEPOINTS];
    }


}
