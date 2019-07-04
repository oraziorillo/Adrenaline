package server.controller.states;

import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.WeaponCard;
import server.model.squares.Square;

import java.rmi.RemoteException;

import static common.Constants.WRONG_TIME;

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

    public void selectMap(int n) {
        controller.ackCurrent(WRONG_TIME);
    }
    
    public void selectNumberOfSkulls(int n) {
        controller.ackCurrent(WRONG_TIME);
    }
    
    public void selectPcColour(String pcColour) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void spawnPc(Pc pc, int powerUpToDropIndex) {
        controller.ackCurrent(WRONG_TIME);
    }

    public boolean runAround(){
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean grabStuff(){
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean shootPeople(){
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean usePowerUp() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public void selectSquare(int row, int col) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectPowerUp(int index) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectWeaponOnBoard(int index) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectWeaponOfMine(int index) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void switchFireMode(WeaponCard weapon) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectUpgrade(WeaponCard weapon, int index) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectAmmo(AmmoEnum fromString) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectTarget(PcColourEnum pcColour) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void selectDirection(CardinalDirectionEnum direction) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void setHasToRespawn(boolean hasToRespawn) {
        controller.ackCurrent(WRONG_TIME);
    }

    public void hasBeenAttacked(Player attackedPlayer) {
        controller.ackCurrent(WRONG_TIME);
    }

    public boolean skip() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean undo() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean ok() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean reload() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean pass() {
        controller.ackCurrent("No regrets. No going back.");
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


    public State forcePass() {
        controller.getSquaresToRefill().forEach(Square::refill);
        controller.resetSquaresToRefill();
        controller.resetRemainingActions();
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
