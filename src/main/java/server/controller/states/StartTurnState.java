package server.controller.states;

import server.controller.Controller;
import server.model.PowerUpCard;
import server.model.WeaponCard;

import java.rmi.RemoteException;

import static common.Constants.MAX_WEAPONS_IN_HAND;

/**Turn beginning state*/
public class StartTurnState extends State {

    private State nextState;

    StartTurnState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean runAround(){
        nextState = new RunAroundState(controller);
        return true;
    }

    @Override
    public boolean grabStuff(){
        nextState = new GrabStuffState(controller);
        return true;
    }

    @Override
    public boolean shootPeople(){
        WeaponCard[] weaponCards = controller.getCurrPlayer().getPc().getWeapons();
        for (int i = 0; i < MAX_WEAPONS_IN_HAND; i++) {
            if (weaponCards[i] != null && weaponCards[i].isLoaded()) {
                nextState = new ShootPeopleState(controller, false, false);
                return true;
            }
        }
        try {
            controller.getCurrPlayer().getView().ack("You can't use this action now!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        nextState = new StartTurnState(controller);
        return false;
    }


    @Override
    public boolean usePowerUp() {
        for (PowerUpCard powerUp: controller.getCurrPlayer().getPc().getPowerUps()) {
            if (powerUp != null) {
                nextState = new UsePowerUpState(controller, null);
                return true;
            }
        }
        try {
            controller.getCurrPlayer().getView().ack("You can't use this action now!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        nextState = new StartTurnState(controller);
        return false;
    }
    
    /**
     * Transition
     * @return the state with the same name of the last method called on this state
     */
    @Override
    public State nextState() {
        return nextState;
    }
}
