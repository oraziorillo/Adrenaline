package server.controller.states;

import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.ControllerMethodsEnum;
import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.WeaponCard;

import java.rmi.RemoteException;
import java.util.UUID;

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

    public void selectMap(Player p, int n) {
        controller.ackPlayer(p, WRONG_TIME);
    }
    
    public void selectNumberOfSkulls(Player p, int n) {
        controller.ackPlayer(p, WRONG_TIME);
    }
    
    public void selectPcColour(Player p, String pcColour) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public boolean runAround(Player p){
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public boolean grabStuff(Player p){
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public boolean shootPeople(Player p){
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public boolean usePowerUp(Player p) {
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public void selectSquare(Player p, int row, int col) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void response(String response) {}

    public void selectPowerUp(Player p, int index) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void selectWeaponOnBoard(Player p, int index) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void selectWeaponOfMine(Player p, int index) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void switchFireMode(Player p, WeaponCard weapon) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void selectUpgrade(Player p, WeaponCard weapon, int index) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void setAsynchronousEffectOrder(Player p, WeaponCard weapon, boolean beforeBasicEffect) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void selectAmmo(Player p, AmmoEnum fromString) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void selectTarget(Player p, PcColourEnum pcColour) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void selectDirection(Player p, CardinalDirectionEnum direction) {
        controller.ackPlayer(p, WRONG_TIME);
    }

    public void setHasToRespawn(boolean hasToRespawn) {
    }

    public boolean skip(Player p) {
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public boolean isUndoable(Player p) {
        controller.ackPlayer(p, "No regrets. No going back.");
        return false;
    }

    public boolean ok(Player p) {
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public void help(Player p) {
        controller.ackPlayer(p, ControllerMethodsEnum.help());
    }

    public boolean reload(Player p) {
        controller.ackPlayer(p, WRONG_TIME);
        return false;
    }

    public boolean pass(Player p) {
        controller.ackPlayer(p, WRONG_TIME);
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


    public void sendChatMessage(Player sender, String msg) {
        controller.sendChatMessage(sender, msg);
    }


    public State forcePass(Player p) {
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }


    public void removeListener(UUID token) {
        controller.getGame().removeListener(token);
    }


    public PcColourEnum getCurrPc() {
        return controller.getCurrPc().getColour();
    }
}
