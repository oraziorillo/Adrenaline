package server.model;

import common.enums.PcColourEnum;
import common.remote_interfaces.ModelChangeListener;

import java.util.LinkedList;
import java.util.List;

import static common.Constants.*;

class PcBoard {

    private List<ModelChangeListener> listeners;

    private short points;
    private short numOfDeaths;
    private short damageTrackIndex;
    private short[] marks;
    private short[] ammo;
    private PcColourEnum[] damageTrack;
    private int [] pcValue;
    private int pcValueIndex;

    PcBoard(){
        this.points = 0;
        this.numOfDeaths = 0;
        this.damageTrackIndex = 0;
        this.marks = new short[5];
        this.pcValue = PC_VALUES;
        this.pcValueIndex = 0;
        this.damageTrack = new PcColourEnum[LIFE_POINTS];
        this.ammo = new short[AMMO_COLOURS_NUMBER];
        this.listeners = new LinkedList<>();
    }


    void init(){
        for(int i = 0; i < AMMO_COLOURS_NUMBER; i++){
            ammo[i] = 1;
        }
    }


    short getPoints() {
        return points;
    }


    short getNumOfDeaths() {
        return numOfDeaths;
    }


    short[] getAmmo() {
        return ammo;
    }


    PcColourEnum[] getDamageTrack() {
        return damageTrack;
    }


    short getDamageTrackIndex() {
        return damageTrackIndex;
    }


    int getPcValueIndex() {
        return pcValueIndex;
    }


    int[] getPcValue() {
        return pcValue;
    }


    short getMarks(PcColourEnum selectedColour) {
        return marks[selectedColour.ordinal()];
    }


    void flipBoard(){
        pcValue = FINAL_FRENZY_PC_VALUES;
        pcValueIndex = 0;
    }


    void increasePoints(int earnedPoints){
        this.points += earnedPoints;

        //notify listeners
        listeners.parallelStream()/*.forEach(l -> l.onPcBoardChange(*costruire un dto di pcBoard*))*/;
    }


    void increaseNumberOfDeaths(){
        numOfDeaths++;

        //notify listeners
        listeners.parallelStream()/*.forEach(l -> l.onPcBoardChange(*costruire un dto di pcBoard*))*/;
    }


    void increasePcValueIndex(){
        if (pcValueIndex != 5)
            pcValueIndex++;
    }


    void addDamage(PcColourEnum shooterColour, short numOfDamage){
        while (numOfDamage != 0) {
            if (damageTrackIndex == LIFE_POINTS)
                break;
            damageTrack[damageTrackIndex] = shooterColour;
            damageTrackIndex++;
            numOfDamage--;
        }

        //notify listeners
        listeners.parallelStream()/*.forEach(l -> l.onPcBoardChange(*costruire un dto di pcBoard*))*/;
    }


    void addMarks(PcColourEnum shooterColour, short numOfMarks) {
        if (marks[shooterColour.ordinal()] + numOfMarks > MAX_NUMBER_OF_MARKS_PER_COLOUR)
            marks[shooterColour.ordinal()] = MAX_NUMBER_OF_MARKS_PER_COLOUR;
        else
            marks[shooterColour.ordinal()] += numOfMarks;

        //notify listeners
        listeners.parallelStream()/*.forEach(l -> l.onPcBoardChange(*costruire un dto di pcBoard*))*/;
    }


    void addAmmo(AmmoTile ammoTile) {
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++) {
            this.ammo[i] += ammoTile.getAmmo()[i];
            if (ammo[i] > MAX_AMMO_PER_COLOUR)
                ammo[i] = MAX_AMMO_PER_COLOUR;
        }

        //notify listeners
        listeners.parallelStream()/*.forEach(l -> l.onPcBoardChange(*costruire un dto di pcBoard*))*/;
    }


    boolean hasAtLeastOneAmmo() {
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++){
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
    short[] payAmmo(short[] ammo) {
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++){
            if (this.ammo[i] >= ammo[i]){
                this.ammo[i] -= ammo[i];
                ammo[i] = 0;
            } else {
                ammo[i] -= this.ammo[i];
                this.ammo[i] = 0;
            }
        }

        //notify listeners
        listeners.parallelStream()/*.forEach(l -> l.onPcBoardChange(*costruire un dto di pcBoard*))*/;

        return ammo;
    }


    void resetDamageTrack(){
        damageTrackIndex = 0;
        damageTrack = new PcColourEnum[LIFE_POINTS];
    }


    void addModelChangeListener(ModelChangeListener listener) {
        listeners.add(listener);
    }
}
