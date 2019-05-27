package controller.states;

import controller.Controller;
import model.Pc;
import model.powerUps.PowerUpCard;
import model.squares.Square;
import enums.AmmoEnum;

public class FirstTurnState extends State{

    private Pc pcToSpawn;
    private int powerUpToDropIndex;

    FirstTurnState(Controller controller) {
        super(controller);
        powerUpToDropIndex = -1;
    }

    @Override
    public void spawnPc(Pc pc, int powerUpToDropIndex){
        this.pcToSpawn = pc;
        this.powerUpToDropIndex = powerUpToDropIndex;
    }

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

    @Override
    public State nextState() {
        controller.setFirstTurn(false);
        return new StartTurnState(controller);
    }
}
