package server.controller.states;

import server.controller.Controller;
import server.database.DatabaseHandler;
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
        if (powerUp != null) {
            powerUp.setSelectedAsAmmo(!powerUp.isSelectedAsAmmo());
            controller.ackCurrent("\nYou'll lose a " + powerUp.toString() + " instead of paying one " + powerUp.getColour() + " ammo");
        }
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
            controller.ackCurrent("Humankind cannot gain anything without first giving something in return." +
                    "\nTo obtain, something of equal value must be lost." +
                    "\nTo reload a " + currWeapon.toString() + " you have to pay:" + currWeapon.ammoToString());
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
            } else {
                controller.ackCurrent("Not enough ammo to reload that weapon. You should have collected it before!");
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
        controller.ackCurrent("\nBe a good boy/girl until your next turn\n");
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
        DatabaseHandler.getInstance().save(controller);
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
