package controller.states;

import controller.Controller;
import controller.player.Player;
import enums.CardinalDirectionEnum;
import model.Pc;
import model.squares.Square;
import model.WeaponCard;
import enums.PcColourEnum;

public abstract class State {

    Controller controller;

    State(Controller controller){
        this.controller = controller;
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

    public void setTargetableSquares(Pc referencePc){}

    public void selectSquare(Square targetSquare){}

    public void selectTarget(Pc targetPc){}

    public void selectDirection(CardinalDirectionEnum direction){}

    public void setWeaponToGrab(int index){}

    public void setWeaponToDrop(int index){}

    public void switchFireMode(WeaponCard weapon) {}

    public void upgrade(WeaponCard weapon) {}

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) {}

    public boolean skipAction() { return false; }

    public boolean ok() {
        return false;
    }

    public boolean reload() {
        return false;
    }

    public boolean pass() {
        return false;
    }

    public abstract State nextState();
}
