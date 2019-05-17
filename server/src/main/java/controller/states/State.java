package controller.states;

import controller.Controller;
import controller.Player;
import model.Pc;
import model.Square;
import model.WeaponCard;
import enums.PcColourEnum;

public abstract class State {

    Controller controller;

    State(Controller controller){
        this.controller = controller;
    }

    void move(Pc pc, Square square){
        pc.getCurrSquare().removePc(pc);
        pc.moveTo(square);
        square.addPc(pc);
    }

    public boolean initializeMap(int n) {
        return false;
    }

    public boolean setNumberOfSkulls(int n) {
        return false;
    }

    public boolean assignPcToPlayer(PcColourEnum colour, Player player){
        return false;
    }

    public boolean spawnPc(Pc pc, int n) {
        return false;
    }

    public boolean runAround(){
        return false;
    }

    public boolean grabStuff(){
        return false;
    }

    public boolean shootPeople(){
        return false;
    }

    public void setTargetables(Pc referencePc){}

    public boolean selectSquare(Pc currPc, Square targetSquare) {
        return false;
    }

    public boolean grabWeapon(Pc currPc, int index){ return false; }

    public boolean selectWeapon(Pc currPc, int index){
        return false;
    }

    public boolean selectFireMode(WeaponCard weapon, int fireModeIndex) {
        return false;
    }

    public boolean selectUpgrade(WeaponCard weapon, int upgradeIndex) {
        return false;
    }

    public boolean reload() {
        return false;
    }



    public abstract State nextState();
}
