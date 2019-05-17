package controller.states;

import controller.Controller;
import model.Pc;
import model.Square;

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
    public boolean selectSquare(Pc currPc, Square targetSquare) {
        if (moved || !targetSquare.isTargetable()) {
            return false;
        }
        move(currPc, targetSquare);
        this.moved = true;
        return true;
    }


    @Override
    public void setTargetables(Pc referencePc){
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
        controller.setCurrWeapon(currPc.getWeapons()[index]);
        this.weaponSelected = true;
        return true;
    }

    public boolean reload(){
        haveToReload = true;
        return true;
    }

    @Override
    public State nextState() {
        if (weaponSelected)
            return new FireModeSelectionState(controller);
        if (haveToReload)
            return new ReloadState(controller);
        return this;
    }
}
