package controller.states;

import controller.Controller;
import model.WeaponCard;

public class FireModeSelectionState extends State {

    FireModeSelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean selectFireMode(WeaponCard weapon, int index) {
        if(controller.getCurrPc().hasEnoughAmmo(weapon.getCurrentCost())) {
            weapon.selectFireMode(index);
            //TODO chiamare un metodo setTargettables
            return true;
        }
        return false;
    }


    @Override
    public boolean selectUpgrade(WeaponCard weapon, int index) {

    }


    @Override
    public State nextState() {
        return new TargetSelectionState(controller);
    }
}
