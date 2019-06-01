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
    }
    
    /**
     * Sets the params for later respawn. See the game manual for the dynamics
     * @param pc the pc to respawn
     * @param powerUpToDropIndex the index of the powerup card the pc will use to respawn
     */
    @Override
    public void spawnPc(Pc pc, int powerUpToDropIndex){
        this.pcToSpawn = pc;
        this.powerUpToDropIndex = powerUpToDropIndex;
    }
    
    /**
     * Executes the respawn: Puts the pre-given pc on the GenerationSquare of the colour of the card with the given index
     * @return true if pc is respawned fine, false if some parameters was invalid
     */
    @Override
    public boolean ok() {
        if (pcToSpawn != null && powerUpToDropIndex > 0) {
            PowerUpCard powerUp = pcToSpawn.getPowerUpCard(powerUpToDropIndex);
            AmmoEnum colour = powerUp.getColour();
            Square s = controller.getGame().getSpawnPoint(colour.toSquareColour());
            pcToSpawn.moveTo(s);
            pcToSpawn.discardPowerUp(powerUp);
            pcToSpawn = null;
            powerUpToDropIndex = -1;
            return true;
        }
        return false;
    }
    
    /**
     * @return StartTurnState
     */
    @Override
    public State nextState() {
        controller.setFirstTurn(false);
        return new StartTurnState(controller);
    }
}
