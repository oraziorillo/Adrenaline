package controller.states;

import controller.Controller;
import model.WeaponCard;
import model.Effect;
import model.PowerUpCard;

import java.util.ArrayList;

public class SetupWeaponState extends State {

    private boolean undo;
    private boolean waiting;
    private int fireModeIndex, upgradeIndex;

    SetupWeaponState(Controller controller) {
        super(controller);
        this.fireModeIndex = 1;
    }

    private short [] sumArray(short [] firstArray, short [] secondArray){
        for (int i = 0; i < 3; i++)
            firstArray[i] += secondArray[i];
        return firstArray;
    }

    @Override
    public void switchFireMode(WeaponCard weapon) {
        ArrayList<Effect> fireModes = weapon.getFireModes();
        if (fireModes.size() > 1) {
            short [] newCost = sumArray(controller.getCurrWeapon().getCurrentCost(), fireModes.get(fireModeIndex).getCost());
            if (controller.getCurrPc().hasEnoughAmmo(newCost)) {
                weapon.selectFireMode(fireModeIndex);
                fireModeIndex = (fireModeIndex == fireModes.size() - 1) ? 0 : (fireModeIndex + 1);
            }
        }
    }


    @Override
    public void upgrade(WeaponCard weapon) {
        if (!waiting) {
            ArrayList<Effect> upgrades = weapon.getUpgrades();
            if (upgradeIndex < upgrades.size()) {
                short [] newCost = sumArray(controller.getCurrWeapon().getCurrentCost(), upgrades.get(upgradeIndex).getCost());
                if (controller.getCurrPc().hasEnoughAmmo(newCost)) {
                    if (!upgrades.get(upgradeIndex).isAsynchronous()) {
                        weapon.addUpgrade(upgradeIndex);
                    } else {
                        waiting = true;
                    }
                    upgradeIndex++;
                }
            }
        }
    }

    @Override
    public void removeUpgrade(WeaponCard weapon){
        if (!waiting && upgradeIndex != 0) {
            weapon.removeUpgrade(--upgradeIndex);
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


    //con questa implementazione l'utente non può deselezionare il powerUpAsAmmo a meno che non usi undo
    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null && powerUp.isSelectedAsAmmo())
            powerUp.setSelectedAsAmmo(true);
    }

    @Override
    public boolean undo() {
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.isSelectedAsAmmo())
                p.setSelectedAsAmmo(false);
        }
        controller.getCurrWeapon().clear();
        undo = true;
        return true;
    }

    @Override
    public boolean ok() {
        return true;
    }


    @Override
    public State nextState() {
        if (undo)
            return new SetupWeaponState(controller);
        return new TargetSelectionState(controller);
    }
}
