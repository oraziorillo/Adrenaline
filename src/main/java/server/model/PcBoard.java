package server.model;

import common.enums.PcColourEnum;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;

import static common.Constants.*;

class PcBoard {

    private ModelMapper modelMapper = new CustomizedModelMapper().getModelMapper();

    private ModelEventHandler events = new ModelEventHandler();

    private PcColourEnum colour;
    private short points;
    private short damageTrackIndex;
    private short[] marks;
    private short[] ammo;
    private PcColourEnum[] damageTrack;
    private int [] pcValue;
    private int numOfDeaths;

    PcBoard(PcColourEnum colour){
        this.colour = colour;
        this.points = 0;
        this.numOfDeaths = 0;
        this.damageTrackIndex = 0;
        this.marks = new short[5];
        this.pcValue = PC_VALUES;
        this.damageTrack = new PcColourEnum[LIFE_POINTS];
        this.ammo = new short[AMMO_COLOURS_NUMBER];
    }


    void init(){
        for(int i = 0; i < AMMO_COLOURS_NUMBER; i++){
            ammo[i] = 1;
        }
    }

    public PcColourEnum getColour() {
        return colour;
    }


    public short getPoints() {
        return points;
    }


    public int getNumOfDeaths() {
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


    public int[] getPcValue() {
        return pcValue;
    }


    public short getMarks(PcColourEnum selectedColour) {
        return marks[selectedColour.ordinal()];
    }


    void flipBoard(){
        pcValue = FINAL_FRENZY_PC_VALUES;
        numOfDeaths = 0;
    }


    void increasePoints(int earnedPoints){
        this.points += earnedPoints;
    }


    void increaseNumberOfDeaths(){
        if (numOfDeaths != 5)
            numOfDeaths++;
    }


    void addDamage(PcColourEnum shooterColour, short numOfDamage){
        while (numOfDamage != 0) {
            if (damageTrackIndex == LIFE_POINTS)
                break;
            damageTrack[damageTrackIndex] = shooterColour;
            damageTrackIndex++;
            numOfDamage--;
        }
    }


    void addMarks(PcColourEnum shooterColour, short numOfMarks) {
        if (marks[shooterColour.ordinal()] + numOfMarks > MAX_NUMBER_OF_MARKS_PER_COLOUR)
            marks[shooterColour.ordinal()] = MAX_NUMBER_OF_MARKS_PER_COLOUR;
        else
            marks[shooterColour.ordinal()] += numOfMarks;
    }


    void addAmmo(AmmoTile ammoTile) {
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++) {
            this.ammo[i] += ammoTile.getAmmo()[i];
            if (ammo[i] > MAX_AMMO_PER_COLOUR)
                ammo[i] = MAX_AMMO_PER_COLOUR;
        }
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
     * @param cost some ammos
     * @return ammo, where every succesfully payed ammo has been removed (so it will be a "not payed" array)
     */
    short[] payAmmo(short[] cost) {
        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++){
            if (this.ammo[i] >= cost[i]){
                this.ammo[i] -= cost[i];
                cost[i] = 0;
            } else {
                cost[i] -= this.ammo[i];
                this.ammo[i] = 0;
            }
        }
        return cost;
    }


    void resetDamageTrack(){
        damageTrackIndex = 0;
        damageTrack = new PcColourEnum[LIFE_POINTS];
    }


    void addModelEventHandler(ModelEventHandler events) {
        this.events = events;
    }

}
