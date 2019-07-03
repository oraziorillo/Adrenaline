package server.controller.states;

import server.controller.Controller;
import server.model.Pc;
import server.model.WeaponCard;
import server.model.squares.Square;
import java.util.Set;

/**
 * Firts state in shooting someone
 */
public class ShootPeopleState extends State {

    private boolean undo;
    private boolean moved;
    private boolean weaponSelected;
    private boolean wantsToReload;
    private boolean reloadDone;
    private Square targetSquare;
    private Set<Square> targetableSquares;

    ShootPeopleState(Controller controller, boolean hasMoved, boolean hasReloaded) {
        super(controller);
        this.moved = hasMoved;
        this.reloadDone = hasReloaded;
        setTargetableToValidSquares(controller.getCurrPc());
    }


    @Override
    public void selectSquare(int row, int column) {
        Square s = controller.getGame().getSquare(row, column);
        if (!moved && s != null && s.isTargetable()) {
            this.targetSquare = s;
        } else if (moved) {
            controller.ackCurrent("You can't move twice, it's time to shoot now! Just choose a weapon.");
        } else
            controller.ackCurrent("You can't move there!");
    }


    @Override
    public void selectWeaponOfMine(int index) {
        WeaponCard currWeapon = controller.getCurrPc().weaponAtIndex(index);
        if (currWeapon != null && currWeapon.isLoaded()) {
            controller.setCurrWeapon(currWeapon);
            this.weaponSelected = true;
            controller.ackCurrent("Ohoh! That hurts!");
        }
    }


    @Override
    public boolean reload(){
        if (controller.isFinalFrenzy() && !reloadDone) {
            this.wantsToReload = true;
            return true;
        } else if (reloadDone)
            controller.ackCurrent("You just reloaded!");
        else
            controller.ackCurrent("Not in final frenzy, yet. First shoot, then reload!");
        return false;
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc){
        int maxDistance;
        if (!controller.isFinalFrenzy()) {
            if (referencePc.getAdrenaline() < 2)
                return;
            else
                maxDistance = 1;
        } else if (controller.beforeFirstPlayer(controller.getCurrPlayerIndex())){
            maxDistance = 1;
        } else
            maxDistance = 2;
        targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
        targetableSquares.remove(referencePc.getCurrSquare());
        controller.getGame().setTargetableSquares(targetableSquares, true);
    }


    @Override
    public boolean undo() {
        if (!moved && !reloadDone){
            if (targetableSquares != null) {
                controller.getGame().setTargetableSquares(targetableSquares, false);
            }
            undo = true;
            return true;
        }
        return false;
    }


    @Override
    public boolean ok() {
        if (targetSquare != null && !moved) {
            controller.getCurrPc().moveTo(targetSquare);
            controller.getGame().setTargetableSquares(targetableSquares, false);
            moved = true;
            return false;
        }
        return weaponSelected || wantsToReload;
    }


    @Override
    public State nextState() {
        if (undo)
            return new StartTurnState(controller);
        if (weaponSelected)
            return new SetupWeaponState(controller);
        if (wantsToReload)
            return new ReloadState(controller);
        return this;
    }
}
