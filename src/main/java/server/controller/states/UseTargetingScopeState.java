package server.controller.states;

import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;

import java.rmi.RemoteException;
import java.util.List;

public class UseTargetingScopeState extends State {

    private boolean undo = false;
    private PowerUpCard currPowerUp;
    private PowerUpCard powerUpSelectedAsAmmo;
    private AmmoEnum ammoToUse;
    private Action currAction;
    private List<Pc> targetablePcs;


    UseTargetingScopeState(Controller controller, List<Pc> targetables) {
        super(controller);
        this.targetablePcs = targetables;
    }


    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp;
        Action powerUpAction;
        try {
            powerUp = controller.getCurrPc().getPowerUpCard(index);
            powerUpAction = powerUp.getAction();
            if (powerUp.getAction().isAdditionalDamage()) {
                currPowerUp = powerUp;
                currAction = powerUpAction;
            } else {
                powerUpSelectedAsAmmo = powerUp;
                try {
                    controller.getCurrPlayer().getView().ack("You have selected this powerUp as an ammo!");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception ex){
            try {
                controller.getCurrPlayer().getView().ack("Be careful! " + ex );
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void selectAmmo(AmmoEnum ammoColour){
        short[] cost = new short[3];
        cost[ammoColour.ordinal()]++;
        if (controller.getCurrPlayer().getPc().hasEnoughAmmo(cost)){
            ammoToUse = ammoColour;
        }
    }


    @Override
    public void selectTarget(PcColourEnum targetPcColour) {
        Pc targetPc = controller.getPlayers().stream().map(Player::getPc).filter(pc -> pc.getColour() == targetPcColour).findFirst().orElse(null);
        if ( targetPc != null && targetablePcs.contains(targetPc)) {
            if (currAction != null) {
                currAction.selectPc(targetPc);
            }
        }
    }


    @Override
    public boolean undo() {
        currAction.resetAction();
        undo = true;
        return true;
    }


    @Override
    public boolean skip() {
        currAction.resetAction();
        undo = true;
        return true;
    }


    @Override
    public boolean ok() {
        if (currAction.isComplete()) {
            if (ammoToUse != null || powerUpSelectedAsAmmo != null){
                currPowerUp.useAction(controller.getCurrPc());
                payAmmo();
                controller.getCurrPc().discardPowerUp(currPowerUp);
                return settingNextState();
            }
        }
        return false;
    }


    private boolean settingNextState() {
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.getAction().isAdditionalDamage() && controller.getCurrPc().hasAtLeastOneAvailableAmmo()){
                currPowerUp = null;
                powerUpSelectedAsAmmo = null;
                ammoToUse = null;
                currAction = null;
                return false;
            }
        }
        return true;
    }


    private void payAmmo(){
        if (ammoToUse != null) {
            short[] cost = new short[3];
            cost[ammoToUse.ordinal()]++;
            controller.getCurrPlayer().getPc().payAmmo(cost);
        } else {
            controller.getCurrPc().discardPowerUp(powerUpSelectedAsAmmo);
        }
    }


    @Override
    public State nextState() {
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
