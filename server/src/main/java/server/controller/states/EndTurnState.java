package server.controller.states;

import server.controller.Controller;
import server.controller.Player;
import server.database.DatabaseHandler;
import server.model.squares.Square;

import static common.enums.ControllerMethodsEnum.PASS;
import static common.enums.ControllerMethodsEnum.RELOAD;

/**
 * State representing the action to "pass", when the current player has no actions left
 */
class EndTurnState extends State {

    private boolean toReload;

    EndTurnState(Controller controller) {
        super(controller);
        controller.ackCurrent("Now the only things left for you to do are to reload (use the command " +
                RELOAD.getUsage() + ") or just pass (use the command " + PASS.getUsage() + ")");
    }
    
    /**
     * If called, the next state will be a ReloadState
     * @see ReloadState
     * @return true
     */
    @Override
    public boolean reload(Player p) {
        toReload = true;
        controller.ackCurrent(System.lineSeparator() + "Ohw! You really want to reload? You will have to pay all of your ammo!");
        return true;
    }
    
    /**
     * When called the player passes and the the map is refilled according to the manual (Weapons and ammos are restored)
     * @return true
     */
    @Override
    public boolean pass(Player p) {
        if (!controller.isLocked()) {
            controller.getSquaresToRefill().forEach(Square::refill);
            controller.resetSquaresToRefill();
            controller.ackCurrent(System.lineSeparator() + "Be a good boy/girl until your next turn" + System.lineSeparator());
            return true;
        }
        controller.ackCurrent(System.lineSeparator() + "Be patient! A player is choosing whether to use or not a Tagback Grenade");
        return false;
    }


    @Override
    public State forcePass(Player p) {
        controller.getSquaresToRefill().forEach(Square::refill);
        controller.resetSquaresToRefill();
        controller.ackCurrent(System.lineSeparator() + "Be a good boy/girl until your next turn" + System.lineSeparator());
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }

    /**
     * State transition
     * @return ReloadState if the reload was called, StartTurnState else
     */
    @Override
    public State nextState() {
        if(toReload)
            return new ReloadState(controller);
        DatabaseHandler.getInstance().save(controller);
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }


}
