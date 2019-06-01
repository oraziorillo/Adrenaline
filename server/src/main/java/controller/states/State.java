package controller.states;

import controller.Controller;
import controller.player.Player;
import enums.CardinalDirectionEnum;
import model.Pc;
import model.squares.Square;
import model.WeaponCard;
import enums.PcColourEnum;

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

    
    public void selectPcForPlayer(PcColourEnum colour, Player player) {}

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

    public void selectSquare(Square targetSquare) {}

    public void selectPowerUp(int index) {}

    public void selectWeaponOnBoard(int index) {}

    public void selectWeaponOfMine(int index) {}

    public void switchFireMode(WeaponCard weapon) {}

    public void upgrade(WeaponCard weapon) {}

    public void removeUpgrade(WeaponCard weapon) {}

    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect) {}

    public void selectTarget(Pc targetPc) {}

    public void selectDirection(CardinalDirectionEnum direction) {}

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
    
    /**
     * Calculate the next state and returns it
     * @return The next expected state
     */
    public abstract State nextState();
    
    /**
     *
     * @param controller the controller of the game creating the state diagram
     * @return The starting state of the diagram (usually, a setup state)
     */
    public static State getFirstState(Controller controller){
        return new SetupMapState( controller );
    }
}
