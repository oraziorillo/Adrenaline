package controller.states;

import controller.Controller;
import model.WeaponCard;

public class FireModeSelectionState extends State {

    FireModeSelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean selectFireMode(WeaponCard weapon, int index) {
        //TODO controllare costi
        weapon.selectFireMode(index);
        //TODO chiamare un metodo setTargettables
        return true;
    }

    @Override
    public State nextState() {
        return new TargetSelectionState(controller);
    }
}
