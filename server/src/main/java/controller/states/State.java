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

    void setTargetableToValidSquares(Pc referencePc) {}

    public void selectMap(int n) {}

    public void selectNumberOfSkulls(int n) {}

    public void selectPcForPlayer(PcColourEnum colour, Player player) {}

    public void spawnPc(Pc pc, int n) {}

    public boolean runAround(){
        return false;
    }

    public boolean grabStuff(){
        return false;
    }

    public boolean shootPeople(){
        return false;
    }

    public void selectSquare(Square targetSquare) {}

    public void selectWeaponOnBoard(int index) {}

    public void selectWeaponOfMine(int index) {}

    public void switchFireMode(WeaponCard weapon) {}

    public void upgrade(WeaponCard weapon) {}

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) {}

    public void selectTarget(Pc targetPc) {}

    public void selectDirection(CardinalDirectionEnum direction) {}

    public boolean skipAction() {
        return false;
    }

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
