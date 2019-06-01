package controller.states;

import controller.Controller;
import model.Pc;
import model.PowerUpCard;
import model.squares.Square;
import enums.AmmoEnum;

public class FirstTurnState extends State{

    private Pc pcToSpawn;
    private int powerUpToDropIndex;

    FirstTurnState(Controller controller) {
        super(controller);
        powerUpToDropIndex = -1;
        controller.getCurrPc().drawPowerUp();
        controller.getCurrPc().drawPowerUp();
    }

    @Override
    public void selectPowerUp (int powerUpToDropIndex) {
        if (powerUpToDropIndex == 0 || powerUpToDropIndex == 1) {
            this.pcToSpawn = controller.getCurrPc();
            this.powerUpToDropIndex = powerUpToDropIndex;
        }
    }

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
