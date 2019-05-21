package controller.states;

import controller.Controller;
import model.Pc;
import model.squares.Square;
import model.WeaponCard;

public class ShootPeopleState extends State {

    private boolean moved;
    private boolean weaponSelected;
    private boolean haveToReload;

    ShootPeopleState(Controller controller) {
        super(controller);
        this.moved = false;
        this.weaponSelected = false;
        this.haveToReload = false;
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (!moved && targetSquare.isTargetable()) {
            controller.getCurrPc().moveTo(targetSquare);
            moved = true;
        }
    }


    @Override
    public void setTargetableSquares(Pc referencePc){
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
        controller.getGame().setTargetables(maxDistance, referencePc.getCurrSquare());
    }


    @Override
    public boolean selectWeapon(Pc currPc, int index) {
        WeaponCard currWeapon = currPc.getWeapons()[index];
        if (currWeapon.isLoaded()) {
            controller.setCurrWeapon(currWeapon);
            this.weaponSelected = true;
            return true;
        }
        return false;
    }

    public boolean reload(){
        this.haveToReload = true;
        return true;
    }

    @Override
    public State nextState() {
        if (weaponSelected)
            return new SetupWeaponState(controller);
        if (haveToReload)
            return new ReloadState(controller);
        return this;
    }
}
