package server.controller.states;

import common.enums.ControllerMethodsEnum;
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
        controller.ackCurrent("You can:" + System.lineSeparator() +
                "> run around (use the command " + RUN_AROUND.getUsage() + ")" + System.lineSeparator() +
                "> grab stuff (use the command " + GRAB_STUFF.getUsage() + ")" + System.lineSeparator() +
                "> shoot people (use the command " + SHOOT_PEOPLE.getUsage() + ")" + System.lineSeparator() +
                "> use a power up (use the command " + USE_POWER_UP.getUsage() + ")");
    }


    @Override
    public boolean runAround(Player p){
        controller.ackCurrent(System.lineSeparator() + "Just run? Well, you can move on:");
        nextState = new RunAroundState(controller);
        return true;
    }


    @Override
    public boolean grabStuff(Player p){
        controller.ackCurrent(System.lineSeparator() + "Shopping time! You can grab on:");
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
                    controller.ackCurrent("The adrenaline runs strong in you" +
                            System.lineSeparator() + "(Use the command " + CHOOSE_SQUARE.getUsage() + "to move before shooting or " +
                            CHOOSE_WEAPON_OF_MINE.getUsage() + " to select the weapon to use. Type \"h\" for details on all available commands)" +
                            "You can move on:");
                else
                    controller.ackCurrent("FINALLY! It's showtime!" + System.lineSeparator() +
                            "(Use the command " + CHOOSE_WEAPON_OF_MINE.getUsage() + ". Type \"h\" for details on all available commands)");
                return true;
            }
        }
        controller.ackCurrent(System.lineSeparator() + "You have no usable weapons!");
        return false;
    }


    @Override
    public boolean usePowerUp(Player p) {
        for (PowerUpCard powerUp: p.getPc().getPowerUps()) {
            if (powerUp != null) {
                nextState = new UsePowerUpState(controller);
                controller.ackCurrent(System.lineSeparator() + "Using a power up is sometimes necessary. Choose the power up you want to use" +
                        System.lineSeparator() + "(Use the command " + CHOOSE_POWER_UP.getUsage() + ". Type \"h\" for details on all available commands)");
                return true;
            }
        }
        controller.ackCurrent(System.lineSeparator() + "A power up can make the difference between life and death. You have no power up cards");
        return false;
    }


    public State forcePass(Player p) {
        controller.ackCurrent(System.lineSeparator() + "Too slow man, you''ll skip the turn!");
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
