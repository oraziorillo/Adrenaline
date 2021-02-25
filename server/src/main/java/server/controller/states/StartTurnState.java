package server.controller.states;

import server.controller.Controller;
import server.controller.Player;
import server.model.PowerUpCard;
import server.model.WeaponCard;

import static common.Constants.MAX_WEAPONS_IN_HAND;
import static common.enums.ControllerMethodsEnum.*;

/**Turn beginning state*/
public class StartTurnState extends State {

    private State nextState;

    StartTurnState(Controller controller) {
        super(controller);
        controller.ackCurrent("You can:\n" +
                "> run around (use the command " + RUN_AROUND.getUsage() + ")\n" +
                "> grab stuff (use the command " + GRAB_STUFF.getUsage() + ")\n" +
                "> shoot people (use the command " + SHOOT_PEOPLE.getUsage() + ")\n" +
                "> use a power up (use the command " + USE_POWER_UP.getUsage() + ")");
    }


    @Override
    public boolean runAround(Player p){
        controller.ackCurrent("\nJust run? Well, you can move on:");
        nextState = new RunAroundState(controller);
        return true;
    }


    @Override
    public boolean grabStuff(Player p){
        controller.ackCurrent("\nShopping time! You can grab on:");
        nextState = new GrabStuffState(controller);
        return true;
    }

    @Override
    public boolean shootPeople(Player p){
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
    public boolean usePowerUp(Player p) {
        for (PowerUpCard powerUp: p.getPc().getPowerUps()) {
            if (powerUp != null) {
                nextState = new UsePowerUpState(controller);
                controller.ackCurrent("\nUsing a powerUp sometimes can be necessary");
                return true;
            }
        }
        controller.ackCurrent("\nA power up can make the difference between life and death");
        return false;
    }


    public State forcePass(Player p) {
        controller.ackCurrent("\nToo slow man, you''ll skip the turn!");
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
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
