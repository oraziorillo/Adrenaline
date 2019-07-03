package server.controller.states;

import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.WeaponCard;

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

    public void response(String response) {}

    public void selectPowerUp(int index) {

    }

    public void selectWeaponOnBoard(int index) {

    }

    public void selectWeaponOfMine(int index) {

    }

    public void switchFireMode(WeaponCard weapon) {

    }

    public void selectUpgrade(WeaponCard weapon, int index) {

    }

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) {

    }

    public void selectAmmo(AmmoEnum fromString) {

    }

    public void selectTarget(PcColourEnum pcColour) {

    }

    public void selectDirection(CardinalDirectionEnum direction) {
    }

    public void setHasToRespawn(boolean hasToRespawn) {
    }

    public boolean skip() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean undo() {
        //controller.ackCurrent("No regrets. No going back.");
        return false;
    }

    public boolean ok() {
        controller.ackCurrent(WRONG_TIME);
        return false;
    }

    public boolean reload() {
        return false;
    }

    public boolean pass() {
        return false;
    }


    public void checkTagbackGrenadeConditions(Player damagedPlayer){}


    public boolean isInactive() {
        return false;
    }
    
    /**
     * Calculate the next state and returns it
     * @return The next expected state
     */
    public abstract State nextState();

    public void sendChatMessage(String msg) {
        controller.sendChatMessage(msg);
    }
}
