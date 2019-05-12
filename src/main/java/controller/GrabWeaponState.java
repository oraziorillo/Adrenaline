package controller;

import exceptions.FullyArmedException;
import model.Pc;

public class GrabWeaponState extends State {

    private int weaponToGrabIndex;
    private int weaponToDropIndex;

    GrabWeaponState(Controller controller) {
        super(controller);
        this.weaponToGrabIndex = -1;
        this.weaponToDropIndex = -1;
    }


    @Override
    public boolean grabWeapon(Pc currPc, int index){
        if (weaponToDropIndex < 0) {
            //if no weapon was selected to be dropped, try to collect
            //else if currPc is fully armed set the value of weaponToGrabIndex to the right value
            try {
                currPc.collectWeapon(index);
                controller.addSquareToRefill(currPc.getCurrTile());
                return true;
            } catch (FullyArmedException e) {
                this.weaponToGrabIndex = index;
                return false;
            } catch (NullPointerException e) {
                return false;
            }
        } else {
            //if a weapon was already selected to be dropped, try to switch weapons
            try {
                currPc.switchWeapons(weaponToGrabIndex, weaponToDropIndex);
                controller.addSquareToRefill(currPc.getCurrTile());
                return true;
            } catch (NullPointerException e) {
                return false;
            }
        }
    }


    @Override
    public boolean selectWeapon(Pc currPc, int index){
        if(currPc.isFullyArmed()) {
            //if currPc is fully armed, he's able to drop a weapon
            if (weaponToGrabIndex >= 0) {
                //if a weapon was already selected to be grabbed, try to switch weapons
                try {
                    currPc.switchWeapons(weaponToGrabIndex, weaponToDropIndex);
                    controller.addSquareToRefill(currPc.getCurrTile());
                    return true;
                } catch (NullPointerException e) {
                    return false;
                }
            } else
                //if no weapon was selected to be grabbed, set the value of weaponToDropIndex to the right value
                this.weaponToDropIndex = index;
        }
        return false;
    }


    @Override
    public void nextState() {
        weaponToDropIndex = -1;
        weaponToGrabIndex = -1;
        controller.decreaseRemainingActions();
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            controller.setCurrState(controller.endTurn);
        } else
            controller.setCurrState(controller.startTurnState);
    }
}
