package controller.states;

import controller.Controller;
import model.PowerUpCard;
import model.WeaponCard;

public class ReloadState extends State {

    private WeaponCard weaponToReload;

    ReloadState(Controller controller) {
        super(controller);
    }

    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null)
            powerUp.setSelectedAsAmmo(!powerUp.isSelectedAsAmmo());
    }

    @Override
    public void selectWeaponOfMine(int index) {
        WeaponCard currWeapon = controller.getCurrPc().weaponAtIndex(index);
        if (currWeapon != null && !currWeapon.isLoaded()) {
            this.weaponToReload = currWeapon;
        }
    }

    @Override
    public boolean reload() {
        if (weaponToReload != null){
            short [] weaponCost = weaponToReload.getAmmo();
            if (controller.getCurrPc().hasEnoughAmmo(weaponCost)) {
                controller.getCurrPc().payAmmo(weaponCost);
                weaponToReload.setLoaded(true);
            }
        }
        return false;
    }

    @Override
    public boolean pass() {
        return true;
    }


    @Override
    public State nextState() {
        if (controller.isFinalFrenzy())
            return new
        return null;
    }
}
