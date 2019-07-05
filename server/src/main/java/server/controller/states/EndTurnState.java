package server.controller.states;

import server.controller.Controller;
import server.database.DatabaseHandler;
import server.model.squares.Square;

/**
 * State representing the action to "pass", when the current player has no actions left
 */
class EndTurnState extends State {

    private boolean toReload;

    EndTurnState(Controller controller) {
        super(controller);
        //cli.controller.startTimer();
        controller.ackCurrent("Now the only things left for you to do are to reload or just pass");
    }
    
    /**
     * If called, the next state will be a ReloadState
     * @see ReloadState
     * @return true
     */
    @Override
    public boolean reload() {
        toReload = true;
        controller.ackCurrent("\nOhw! You really want to reload? You will have to pay all of your ammo!");
        return true;
    }
    
    /**
     * When called the player passes and the the map is refilled according to the manual (Weapons and ammos are restored)
     * @return true
     */
    @Override
    public boolean pass() {
        if (!controller.isLocked()) {
            controller.getSquaresToRefill().forEach(Square::refill);
            controller.resetSquaresToRefill();
            controller.ackCurrent("\nBe a good boy/girl until your next turn\n");
            return true;
        }
        controller.ackCurrent("\nBe patient! A player is choosing whether to use or not a Tagback Grenade");
        return false;
    }


    @Override
    public State forcePass() {
        controller.getSquaresToRefill().forEach(Square::refill);
        controller.resetSquaresToRefill();
        controller.ackCurrent("\nBe a good boy/girl until your next turn\n");
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
