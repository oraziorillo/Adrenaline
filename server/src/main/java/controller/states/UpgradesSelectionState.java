package controller.states;

import controller.Controller;
import model.WeaponCard;

public class UpgradesSelectionState extends  State {

    UpgradesSelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean selectUpgrade(WeaponCard weaponCard, int index){
        weaponCard.addUpgrade(index);
        return true;
    }

    @Override
    public State nextState() {
        return new TargetSelectionState();
    }
}
