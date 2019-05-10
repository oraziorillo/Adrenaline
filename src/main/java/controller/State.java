package controller;

import model.Pc;
import model.enumerations.PcColourEnum;

abstract class State {

    static Controller controller;

    public static void setController(Controller c){
        controller = c;
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

    public void changeState(int requestedAction) {
    }

    public abstract void nextState();
}
