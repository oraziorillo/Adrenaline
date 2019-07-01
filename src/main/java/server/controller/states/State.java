package server.controller.states;

import common.enums.PcColourEnum;
import server.controller.Controller;
import common.enums.CardinalDirectionEnum;
import server.model.Pc;
import server.model.WeaponCard;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This class represents a state of a game state diagram.
 * All it's methods are useless by default
 */
public abstract class State {

    Controller controller;

    State(Controller controller){
        this.controller = controller;
    }

    void setTargetableToValidSquares(Pc referencePc) throws RemoteException {}

    public void selectMap(int n) {}
    
    public void selectNumberOfSkulls(int n) {}
    
    public void selectPcColour(String pcColour) {}

    public void spawnPc(Pc pc, int powerUpToDropIndex) {}

    public boolean runAround(){
        return false;
    }

    public boolean grabStuff(){
        return false;
    }

    public boolean shootPeople(){
        return false;
    }

    public boolean usePowerUp() { return false; }

    public void selectSquare(int row, int col) {}

    public void selectPowerUp(int index) { print(); }

    public void selectWeaponOnBoard(int index) { print(); }

    public void selectWeaponOfMine(int index) { print(); }

    public void switchFireMode(WeaponCard weapon) { print(); }

    public void selectUpgrade(WeaponCard weapon, int index) { print(); }

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) { print(); }

    public void selectTarget(PcColourEnum pcColour) { print(); }

    public void selectDirection(CardinalDirectionEnum direction) { print(); }

    public void setHasToRespawn(boolean hasToRespawn) {}

    public void hasBeenAttacked(int playerIndex) {}

    public boolean skipAction() {
        return false;
    }

    public boolean undo() { return false; }

    public boolean ok() {
        return false;
    }

    public boolean reload() {
        return false;
    }

    public boolean pass() {
        return false;
    }

    public boolean isInactive() {
        return false;
    }
    
    /**
     * Calculate the next state and returns it
     * @return The next expected state
     */
    public abstract State nextState();

    void print(){
        try {
            controller.getCurrPlayer().getView().ack("Using this command now is useless");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
