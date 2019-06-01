package controller.states;

import controller.Controller;
import model.Pc;
import model.PowerUpCard;
import model.squares.Square;
import enums.AmmoEnum;

/**
 * Spawns the pcs for the first time
 */
public class FirstTurnState extends State{

    private Pc pcToSpawn;
    private int powerUpToDropIndex;

    FirstTurnState(Controller controller) {
        super(controller);
        powerUpToDropIndex = -1;
        controller.getCurrPc().drawPowerUp();
        controller.getCurrPc().drawPowerUp();
    }
    
    /**
     * Sets the params for later respawn. See the game manual for the dynamics
     * @param pc the pc to respawn
     * @param powerUpToDropIndex the index of the powerup card the pc will use to respawn
     */
    @Override
    public void selectPowerUp (int powerUpToDropIndex) {
        if (powerUpToDropIndex == 0 || powerUpToDropIndex == 1) {
            this.pcToSpawn = controller.getCurrPc();
            this.powerUpToDropIndex = powerUpToDropIndex;
        }
    }
    
    /**
     * Executes the respawn: Puts the pre-given pc on the GenerationSquare of the colour of the card with the given index
     * @return true if pc is respawned fine, false if some parameters was invalid
     */
    @Override
    public boolean ok() {
        if (pcToSpawn != null && powerUpToDropIndex > -1) {
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
        if (controller.getCurrPlayerIndex() == controller.getPlayers().size() - 1) {
            controller.nextTurn();
            return new StartTurnState(controller);
        }
        controller.nextTurn();
        return new FirstTurnState(controller);
    }
}
