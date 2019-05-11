package controller;

import model.Pc;
import model.Tile;
import model.enumerations.PcColourEnum;

abstract class State {

    private static final int ACTIONS_PER_TURN = 2;
    private static final int ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER = 1;

    Controller controller;
    private int remainingActions;


    State(Controller controller){
        this.controller = controller;
        remainingActions = ACTIONS_PER_TURN;
    }

    int getRemainingActions(){
        return remainingActions;
    }

    void decreaseRemainingActions(){
        this.remainingActions--;
    }

    void resetRemainingActions(){
        if(!controller.isFinalFrenzy() || controller.beforeFirstPlayer(controller.getCurrPlayerIndex()))
            this.remainingActions = ACTIONS_PER_TURN;
        else
            this.remainingActions = ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER;
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

    public boolean executeOnTile(Pc currPc, Tile targetSquare) {
        return false;
    }

    public boolean executeOnWeapon(Pc currPc, int n){ return false; }

    public abstract void nextState();
}
