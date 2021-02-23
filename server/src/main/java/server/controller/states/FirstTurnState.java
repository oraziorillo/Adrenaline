package server.controller.states;

import common.enums.AmmoEnum;
import common.enums.ControllerMethodsEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;

import java.util.Random;

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
        controller.ackCurrent("\nChoose a power up to discard, you'll spawn on the spawn point of discarded power up's colour");
        controller.ackCurrent("(Use the command " + ControllerMethodsEnum.CHOOSE_POWER_UP.getUsage() + ". Type \"h\" for details on all available commands)");
    }

    
    /**
     * Sets the params for later respawn. See the game manual for the dynamics
     * @param powerUpToDropIndex the index of the power up card the pc will use to respawn
     */
    @Override
    public void selectPowerUp (Player p, int powerUpToDropIndex) {
        if (powerUpToDropIndex == 0 || powerUpToDropIndex == 1) {
            this.powerUpToDropIndex = powerUpToDropIndex;
            controller.ackCurrent("\nYou'll discard an incredible " + pcToSpawn.getPowerUpCard(powerUpToDropIndex).toString() + ". Is it ok? (\"ok\" to confirm your choice)\"");
        }
    }


    /**
     * Executes the respawn: Puts the pre-given pc on the GenerationSquare of the colour of the card with the given index
     * @return true if pc is respawn fine, false if some parameters was invalid
     */
    @Override
    public boolean ok(Player p) {
        if (powerUpToDropIndex > -1) {
            spawn();
            return true;
        }
        return false;
    }


    @Override
    public State forcePass(Player p) {
        Random random = new Random();
        powerUpToDropIndex = random.nextInt(2);
        spawn();
        controller.ackCurrent("\nToo slow man, you''ll skip the turn!");
        return nextState();
    }


    private void spawn() {
        PowerUpCard powerUp = pcToSpawn.getPowerUpCard(powerUpToDropIndex);
        AmmoEnum respawnColour = powerUp.getColour();
        Square s = controller.getGame().getSpawnPoint(respawnColour.toSquareColour());
        pcToSpawn.spawn(s);
        pcToSpawn.discardPowerUp(powerUp);
    }


    /**
     * @return StartTurnState
     */
    @Override
    public State nextState() {
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
