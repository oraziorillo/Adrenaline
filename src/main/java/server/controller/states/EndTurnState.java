package server.controller.states;

import server.controller.Controller;
import server.model.squares.Square;

/**
 * State representing the action to "pass", when the current player has no actions left
 */
class EndTurnState extends State {

    private boolean toReload;

    EndTurnState(Controller controller) {
        super(controller);
    }
    
    /**
     * If called, the next state will be a ReloadState
     * @see ReloadState
     * @return true
     */
    @Override
    public boolean reload() {
        toReload = true;
        return true;
    }
    
    /**
     * When called the player passes and the the map is refilled according to the manual (Weapons and ammos are restored)
     * @return true
     */
    @Override
    public boolean pass() {
        controller.getSquaresToRefill().forEach(Square::refill);
        controller.resetSquaresToRefill();
        return true;
    }
    
    /**
     * State transition
     * @return ReloadState if the reload was called, StartTurnState else
     */
    @Override
    public State nextState() {
        if(toReload)
            return new ReloadState(controller);
        controller.nextTurn();
        return new InactiveState(controller, true);
    }
}
