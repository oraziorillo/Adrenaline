package controller.states;

import controller.Controller;
import model.WeaponCard;
import model.Effect;
import model.PowerUpCard;

import java.util.ArrayList;

public class SetupWeaponState extends State {

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
        try {
            PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
            powerUp.setSelectedAsAmmo(!powerUp.isSelectedAsAmmo());
        } catch (NullPointerException e) {
            //TODO stampa a video: Non hai selezionato alcun powerUp
        }

    }


    @Override
    public boolean undo() {
        //dovremmo modificarlo in modo tale da resettare l'intero stato
        if (upgradeIndex > 0)
            controller.getCurrWeapon().removeUpgrade(--upgradeIndex);
        return false;
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
