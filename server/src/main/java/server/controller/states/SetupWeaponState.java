package server.controller.states;

import common.events.requests.Request;
import server.controller.Controller;
import server.model.Effect;
import server.model.PowerUpCard;
import server.model.WeaponCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Pre-shot state: the user decides how it's weapons will shot
 */
public class SetupWeaponState extends State {

    private boolean undo;
    private boolean waiting;
    private boolean moved;
    private int fireModeIndex;
    private int asynchronousUpgradeIndex;

    SetupWeaponState(Controller controller, boolean hasMoved) {
        super(controller);
        this.moved = hasMoved;
        this.fireModeIndex = 1;
        //cli.controller.startTimer();
    }

    private short [] sumArray(short [] firstArray, short [] secondArray){
        for (int i = 0; i < 3; i++)
            firstArray[i] += secondArray[i];
        return firstArray;
    }
    
    /**
     * Toggles weapon's selected firemode to the next one (or the first after the last)
     * @param weapon a WeaponCard
     */
    @Override
    public void switchFireMode(WeaponCard weapon) {
        List<Effect> fireModes = weapon.getFireModes();
        if (fireModes.size() > 1) {
            short [] newCost = sumArray(controller.getCurrWeapon().getCurrentCost(), fireModes.get(fireModeIndex).getCost());
            if (controller.getCurrPc().hasEnoughAmmo(newCost)) {
                weapon.selectFireMode(fireModeIndex);
                fireModeIndex = (fireModeIndex == fireModes.size() - 1) ? 0 : (fireModeIndex + 1);
            }
        }
    }
   
   /**
    * Attaches the next selectable selectUpgrade to weapon, if it's not waiting for an AsynchronousUpgrade selection
    * @param weapon a WeaponCard
    * @see Effect
    */
    @Override
    public void selectUpgrade(WeaponCard weapon, int upgradeIndex) {
        if (!waiting) {
            List<Effect> upgrades = weapon.getUpgrades();
            if (upgradeIndex < upgrades.size()) {
                short [] newCost = sumArray(controller.getCurrWeapon().getCurrentCost(), upgrades.get(upgradeIndex).getCost());
                if (controller.getCurrPc().hasEnoughAmmo(newCost)) {
                    if (!upgrades.get(upgradeIndex).isAsynchronous()) {
                        weapon.addUpgrade(upgradeIndex);
                        controller.ackCurrent("\nNow you weapon is huge");
                    } else {
                        List<String> possibilities = new ArrayList<>();
                        possibilities.add("before");
                        possibilities.add("after");
                        controller.sendNonBlockingRequest(new Request("\nYou can use it *before* or *after* the basic effect. So tell me, what do you prefer?", possibilities));
                        waiting = true;
                        asynchronousUpgradeIndex = upgradeIndex;
                    }
                }
            }
        }
    }

   
   /**
    * If waiting for an asynchronous effect, sets it t be performed before every other action of the weapon or after them
    * @param weapon a WeaponCard
    * @param beforeBasicEffect set true to apply the effect before enithing else
    * @see Effect
    */
    @Override
    public void setAsynchronousEffectOrder(WeaponCard weapon, boolean beforeBasicEffect){
        if (waiting) {
            if (beforeBasicEffect) {
                weapon.pushFirstUpgrade(asynchronousUpgradeIndex);
                controller.ackCurrent("\nBefore, huh? I'd have use it after...");
            } else {
                weapon.addUpgrade(asynchronousUpgradeIndex);
                controller.ackCurrent("\nAfter, huh? I'd have use it before...");
            }
            waiting = false;
        }
    }
   
   /**
    * Selects the powerup on the specified position in the current player's hand to be used as an ammo, see the rules
    * @param index the index of the card to be used as ammo
    */
    //con questa implementazione l'utente non può deselezionare il powerUpAsAmmo a meno che non usi undo
    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null && !powerUp.isSelectedAsAmmo()) {
            powerUp.setSelectedAsAmmo(true);
            controller.ackCurrent("You'll lose a " + powerUp.toString() + " instead of paying one " + powerUp.getColour() + " ammo");
        }
    }
   
   /**
    * Deselects every powerup selected to be used ad ammo, clears the current weapon, and prepares to restart this state
    * @return true
    */
    @Override
    public boolean undo() {
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.isSelectedAsAmmo())
                p.setSelectedAsAmmo(false);
        }
        controller.getCurrWeapon().reset();
        undo = true;
        return true;
    }
   
   /**
    * Just returns true
    * @return true
    */
    @Override
    public boolean ok() {
        return true;
    }


    @Override
    public State forcePass() {
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.isSelectedAsAmmo())
                p.setSelectedAsAmmo(false);
        }
        controller.getCurrWeapon().reset();
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }


   /**
    * Transition
    * @return SetUpWeaponState if undo has been called, TargetSelectionSquare else
    */
    @Override
    public State nextState() {
        if (undo)
            return new SetupWeaponState(controller, moved);
        return new TargetSelectionState(controller, moved);
    }
}
