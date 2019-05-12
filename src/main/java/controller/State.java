package controller;

import model.Pc;
import model.Tile;
import model.enumerations.PcColourEnum;

abstract class State {

    Controller controller;

    State(Controller controller){
        this.controller = controller;
    }

    void move(Pc pc, Tile tile){
        pc.getCurrTile().removePc(pc);
        pc.moveTo(tile);
        tile.addPc(pc);
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

    public boolean execute(Pc currPc, Tile targetSquare) {
        return false;
    }

    public abstract void nextState();
}
