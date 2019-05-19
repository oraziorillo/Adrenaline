package controller.states;

import controller.Controller;
import model.WeaponCard;

public class UpdradesSelectionState extends State {

    UpdradesSelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean selectUpgrade(WeaponCard weapon, int index) {
        if (controller.getCurrPc().hasEnoughAmmo(weapon.getCurrentCost())) {
            weapon.addUpgrade(index);
            //TODO chiamare un metodo setTargettables
            return true;
        }
        return false;
    }

    @Override
    public State nextState() {
        return new TargetSelectionState(controller);
    }
}
