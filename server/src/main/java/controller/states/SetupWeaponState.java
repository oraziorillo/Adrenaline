package controller.states;

import controller.Controller;
import model.WeaponCard;
import model.WeaponEffect;
import model.powerUps.PowerUpCard;

import java.util.ArrayList;

public class SetupWeaponState extends State {

    private boolean waiting;
    private int fireModeIndex, upgradeIndex;

    SetupWeaponState(Controller controller) {
        super(controller);
        this.fireModeIndex = 1;
    }

    @Override
    public void switchFireMode(WeaponCard weapon) {
        ArrayList<WeaponEffect> fireModes = weapon.getFireModes();
        if (fireModes.size() > 1 && controller.getCurrPc().hasEnoughAmmo(fireModes.get(fireModeIndex).getCost())){
            weapon.selectFireMode(fireModeIndex);
            fireModeIndex = (fireModeIndex == fireModes.size() - 1) ? 0 : (fireModeIndex + 1);
        }
    }


    @Override
    public void upgrade(WeaponCard weapon) {
        if (!waiting) {
            ArrayList<WeaponEffect> upgrades = weapon.getUpgrades();
            if (upgradeIndex < upgrades.size() && controller.getCurrPc().hasEnoughAmmo(upgrades.get(upgradeIndex).getCost())) {
                if (!upgrades.get(upgradeIndex).isAsynchronous()) {
                    weapon.addUpgrade(upgradeIndex);
                } else {
                    waiting = true;
                }
                upgradeIndex++;
            }
        }
    }


    @Override
    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect){
        if (waiting) {
            if (beforeBasicEffect) {
                weapon.addFirst(upgradeIndex);
            } else {
                weapon.addUpgrade(upgradeIndex);
            }
            waiting = false;
        }
    }


    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if(!powerUp.isSelectedAsAmmo()) {
            powerUp.setSelectedAsAmmo(true);
        } else {
            controller.getCurrWeapon().increaseCost(powerUp);
            powerUp.setSelectedAdAmmo(false);
        }

    }

    @Override
    public boolean ok() {
        //TODO: gli facciamo pagare ora il costo dell'arma??
        return true;
    }


    @Override
    public State nextState() {
        return new TargetSelectionState(controller);
    }
}
