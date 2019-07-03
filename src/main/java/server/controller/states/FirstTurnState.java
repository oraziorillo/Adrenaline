package server.controller.states;

import common.enums.AmmoEnum;
import server.controller.Controller;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;

/**
 * Spawns the pcs for the first time
 */
public class FirstTurnState extends State{

    private Pc pcToSpawn;
    private int powerUpToDropIndex;

    FirstTurnState(Controller controller) {
        super(controller);
        powerUpToDropIndex = -1;
        this.pcToSpawn = controller.getCurrPc();
        this.pcToSpawn.drawPowerUp(2);
    }

    
    /**
     * Sets the params for later respawn. See the game manual for the dynamics
     * @param powerUpToDropIndex the index of the power up card the pc will use to respawn
     */
    @Override
    public void selectPowerUp (int powerUpToDropIndex) {
        if (powerUpToDropIndex == 0 || powerUpToDropIndex == 1) {
            this.powerUpToDropIndex = powerUpToDropIndex;
            controller.ackCurrent("\nYou'll discard an incredible " + pcToSpawn.getPowerUpCard(powerUpToDropIndex).toString() + ". It's ok?");
        }
    }


    /**
     * Executes the respawn: Puts the pre-given pc on the GenerationSquare of the colour of the card with the given index
     * @return true if pc is respawn fine, false if some parameters was invalid
     */
    @Override
    public boolean ok() {
        if (powerUpToDropIndex > -1) {
            PowerUpCard powerUp = pcToSpawn.getPowerUpCard(powerUpToDropIndex);
            AmmoEnum respawnColour = powerUp.getColour();
            Square s = controller.getGame().getSpawnPoint(respawnColour.toSquareColour());
            pcToSpawn.spawn(s);
            pcToSpawn.discardPowerUp(powerUp);
            return true;
        }
        return false;
    }

    
    /**
     * @return StartTurnState
     */
    @Override
    public State nextState() {
        controller.nextTurn();
        if (controller.getCurrPlayerIndex() != 0)
            controller.ackCurrent("\nChoose a power up to discard, you'll spawn on the spawn point of discarded power up's colour");
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
