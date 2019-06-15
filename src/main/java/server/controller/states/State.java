package server.controller.states;

import server.controller.Controller;
import common.enums.CardinalDirectionEnum;
import server.model.Pc;
import server.model.WeaponCard;

/**
 * This class represents a state of a game state diagram.
 * All it's methods are useless by default
 */
public abstract class State {

    Controller controller;

    State(Controller controller){
        this.controller = controller;
    }

    void setTargetableToValidSquares(Pc referencePc) {}

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

    public void selectPowerUp(int index) {}

    public void selectWeaponOnBoard(int index) {}

    public void selectWeaponOfMine(int index) {}

    public void switchFireMode(WeaponCard weapon) {}

    public void upgrade(WeaponCard weapon) {}

    public void removeUpgrade(WeaponCard weapon) {}

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) {}

    public void selectTarget(Pc targetPc) {}

    public void selectDirection(CardinalDirectionEnum direction) {}

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

}
