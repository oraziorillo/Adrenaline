package controller.states;

import controller.Controller;
import model.Pc;
import model.WeaponCard;
import model.squares.Square;
import java.util.HashSet;

public class ShootPeopleState extends State {

    private boolean moved;
    private boolean weaponSelected;
    private boolean haveToReload;
    private Square targetSquare;

    ShootPeopleState(Controller controller) {
        super(controller);
        this.moved = false;
        this.weaponSelected = false;
        this.haveToReload = false;
        setTargetableToValidSquares(controller.getCurrPc());
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (!moved && targetSquare.isTargetable()) {
            this.targetSquare = targetSquare;
        }
    }


    @Override
    public void selectWeaponOfMine(int index) {
        WeaponCard currWeapon = controller.getCurrPc().weaponAtIndex(index);
        if (currWeapon.isLoaded()) {
            controller.setCurrWeapon(currWeapon);
            this.weaponSelected = true;
        }
    }


    @Override
    public boolean reload(){
        this.haveToReload = true;
        return true;
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
        HashSet<Square> targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
        controller.getGame().setTargetableSquares(targetableSquares);
    }


    @Override
    public boolean ok() {
        if (targetSquare != null) {
            controller.getCurrPc().moveTo(targetSquare);
            controller.getGame().resetTargetableSquares();
            moved = true;
            return false;
        }
        return weaponSelected || haveToReload;
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
