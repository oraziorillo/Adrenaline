package server.controller.states;

import server.controller.Controller;
import server.model.PowerUpCard;
import server.model.WeaponCard;

import static common.Constants.MAX_WEAPONS_IN_HAND;

/**Turn beginning state*/
public class StartTurnState extends State {

    private State nextState;

    StartTurnState(Controller controller) {
        super(controller);
        controller.ackCurrent("You can:\n> run around\n> grab stuff\n> shoot people\n> use a power up");
    }


    @Override
    public boolean runAround(){
        controller.ackCurrent("\nJust run? Well, you can move on:");
        nextState = new RunAroundState(controller);
        return true;
    }


    @Override
    public boolean grabStuff(){
        controller.ackCurrent("\nShopping time! You can grab on:");
        nextState = new GrabStuffState(controller);
        return true;
    }

    @Override
    public boolean shootPeople(){
        WeaponCard[] weaponCards = controller.getCurrPlayer().getPc().getWeapons();
        for (int i = 0; i < MAX_WEAPONS_IN_HAND; i++) {
            if (weaponCards[i] != null && weaponCards[i].isLoaded()) {
                nextState = new ShootPeopleState(controller, false, false);
                if (controller.getCurrPc().getAdrenaline() == 2)
                    controller.ackCurrent("FINALLY! It's showtime! You can move on:");
                else
                    controller.ackCurrent("FINALLY! It's showtime!");
                return true;
            }
        }
        controller.ackCurrent("\nYou have no usable weapons!");
        return false;
    }


    @Override
    public boolean usePowerUp() {
        for (PowerUpCard powerUp: controller.getCurrPlayer().getPc().getPowerUps()) {
            if (powerUp != null) {
                nextState = new UsePowerUpState(controller);
                return true;
            }
        }
        controller.ackCurrent("\nA power up can make the difference between life and death");
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
