package server.controller.states;

import server.controller.Controller;
import server.controller.Player;
import server.model.Effect;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;

public class InactiveState extends State {

    static final int PC_SELECTION_STATE = 0;
    static final int FIRST_TURN_STATE = 1;
    static final int START_TURN_STATE = 2;

    private int nextState;
    private boolean hasToRespawn;
    private boolean attacked;
    private Pc attackingPc;
    private Player attackedPlayer;


    InactiveState(Controller controller, int nextState) {
        super(controller);
        this.nextState = nextState;
    }

    @Override
    public boolean isInactive(){
        return true;
    }


    public void setHasToRespawn(boolean hasToRespawn) {
        this.hasToRespawn = hasToRespawn;
    }


    @Override
    public void hasBeenAttacked(int playerIndex){
        attacked = true;
        attackingPc = controller.getCurrPc();
        attackedPlayer = controller.getPlayers().get(playerIndex);
        //TODO mandare messaggio al giocatore per chiedergli se vuole usare il powerUp che applica il marchio
    }

    @Override
    public void selectPowerUp(int index) {
        if (attacked){
            PowerUpCard powerUpCard = attackedPlayer.getPc().getPowerUpCard(index);
            if (powerUpCard.getEffect().isAsynchronous()){
                Effect effect = powerUpCard.getEffect();
                Action action = effect.getActions().get(0);
                action.selectPc(attackingPc);
                action.apply(attackedPlayer.getPc());
                attackedPlayer.getPc().discardPowerUp(powerUpCard);
                attacked = false;
            }
        }
    }

    @Override
    public State nextState() {
        if (hasToRespawn)
            return new RespawnState(controller);
        switch (nextState){
            case PC_SELECTION_STATE:
                return new PcSelectionState(controller);
            case FIRST_TURN_STATE:
                return new FirstTurnState(controller);
            case START_TURN_STATE:
                return new StartTurnState(controller);
            default:
                return this;
        }
    }
}
