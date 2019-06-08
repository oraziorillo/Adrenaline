package server.controller.states;

import server.controller.Controller;
import server.model.PowerUpCard;
import server.model.WeaponCard;
import server.model.squares.Square;

/**
 * Reloads the weapons
 */
public class ReloadState extends State {


    private WeaponCard weaponToReload;


    ReloadState(Controller controller) {
        super(controller);
    }


    /**
     * Selects a powerup to use as an ammo
     * @param index the powerup card index
     * @see PowerUpCard
     */
    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null)
            powerUp.setSelectedAsAmmo(!powerUp.isSelectedAsAmmo());
    }


    /**
     * selects a weapon to reload
     * @param index the WeaponCard index
     */
    @Override
    public void selectWeaponOfMine(int index) {
        WeaponCard currWeapon = controller.getCurrPc().weaponAtIndex(index);
        if (currWeapon != null && !currWeapon.isLoaded()) {
            this.weaponToReload = currWeapon;
        }
    }


    /**
     * Reloads the pre-selected weapon using methods from Pc and WeaponCard
     * @see server.model.Pc
     * @see WeaponCard
     * @return true iif a weapon was pre-selected
     */
    @Override
    public boolean ok() {
        if (weaponToReload != null){
            short [] weaponCost = weaponToReload.getAmmo();
            if (controller.getCurrPc().hasEnoughAmmo(weaponCost)) {
                controller.getCurrPc().payAmmo(weaponCost);
                weaponToReload.setLoaded(true);
                weaponToReload = null;
            }
        }
        return false;
    }


    /**
     * Ends the player turn
     * @return true
     */
    @Override
    public boolean pass() {
        controller.getSquaresToRefill().forEach(Square::refill);
        controller.resetSquaresToRefill();
        return true;
    }


    /**
     * Transition
     * @return ShootPeopleState if final frenzy is on, StartTurnState else
     */
    @Override
    public State nextState() {
        if (controller.isFinalFrenzy())
            return new ShootPeopleState(controller, true, true);
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
