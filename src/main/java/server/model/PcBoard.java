package server.model;

import common.dto_model.PcBoardDTO;
import common.enums.PcColourEnum;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static common.Constants.*;

class PcBoard {

    private ModelMapper modelMapper = new CustomizedModelMapper().getModelMapper();

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private short points;
    private short damageTrackIndex;
    private short[] marks;
    private short[] ammo;
    private PcColourEnum[] damageTrack;
    private int [] pcValue;
    private int numOfDeaths;

    PcBoard(){
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


    short getPoints() {
        return points;
    }


    int getNumOfDeaths() {
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


    int[] getPcValue() {
        return pcValue;
    }


    short getMarks(PcColourEnum selectedColour) {
        return marks[selectedColour.ordinal()];
    }


    void flipBoard(){
        pcValue = FINAL_FRENZY_PC_VALUES;
        numOfDeaths = 0;
    }


    void increasePoints(int earnedPoints){

        PcBoardDTO old = modelMapper.map(this, PcBoardDTO.class);

        this.points += earnedPoints;


        //notify listeners
        changes.firePropertyChange(INCREASE_POINTS, old, modelMapper.map(this, PcBoardDTO.class));
    }


    void increaseNumberOfDeaths(){

        PcBoardDTO old = modelMapper.map(this, PcBoardDTO.class);

        if (numOfDeaths != 5)
            numOfDeaths++;

        //notify listeners
        changes.firePropertyChange(INCREASE_NUMBER_OF_DEATHS, old, modelMapper.map(this, PcBoardDTO.class));
    }


    void addDamage(PcColourEnum shooterColour, short numOfDamage){

        PcBoardDTO old = modelMapper.map(this, PcBoardDTO.class);

        while (numOfDamage != 0) {
            if (damageTrackIndex == LIFE_POINTS)
                break;
            damageTrack[damageTrackIndex] = shooterColour;
            damageTrackIndex++;
            numOfDamage--;
        }

        changes.firePropertyChange(ADD_DAMAGE, old, modelMapper.map(this, PcBoardDTO.class));
    }


    void addMarks(PcColourEnum shooterColour, short numOfMarks) {

        PcBoardDTO old = modelMapper.map(this, PcBoardDTO.class);

        if (marks[shooterColour.ordinal()] + numOfMarks > MAX_NUMBER_OF_MARKS_PER_COLOUR)
            marks[shooterColour.ordinal()] = MAX_NUMBER_OF_MARKS_PER_COLOUR;
        else
            marks[shooterColour.ordinal()] += numOfMarks;

        //notify listeners
        changes.firePropertyChange(ADD_MARKS, old, modelMapper.map(this, PcBoardDTO.class));
    }


    void addAmmo(AmmoTile ammoTile) {

        PcBoardDTO old = modelMapper.map(this, PcBoardDTO.class);

        for (int i = 0; i < AMMO_COLOURS_NUMBER; i++) {
            this.ammo[i] += ammoTile.getAmmo()[i];
            if (ammo[i] > MAX_AMMO_PER_COLOUR)
                ammo[i] = MAX_AMMO_PER_COLOUR;
        }

        //notify listeners
        changes.firePropertyChange(ADD_AMMO, old, modelMapper.map(this, PcBoardDTO.class));
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

        PcBoardDTO old = modelMapper.map(this, PcBoardDTO.class);

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
        changes.firePropertyChange(PAY_AMMO, old, modelMapper.map(this, PcBoardDTO.class));

        return ammo;
    }


    void resetDamageTrack(){
        damageTrackIndex = 0;
        damageTrack = new PcColourEnum[LIFE_POINTS];
    }


    void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }
}
