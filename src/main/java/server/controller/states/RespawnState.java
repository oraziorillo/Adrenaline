package server.controller.states;

import server.controller.Controller;
import server.controller.Player;
import server.enums.AmmoEnum;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;

public class RespawnState extends State {

    private Player deadPlayer;
    private int powerUpIndex;

    RespawnState(Controller controller) {
        super(controller);
        this.powerUpIndex = -1;
        controller.getGame().registerDeath(deadPlayer.getPc());
        deadPlayer = controller.getDeadPlayers().poll();
        deadPlayer.getPc().drawPowerUp();
}

    @Override
    public void selectPowerUp(int index) {
        if( deadPlayer.getPc().getPowerUps().get(index) != null)
            this.powerUpIndex = index;
    }


    @Override
    public boolean ok() {
        if (powerUpIndex > -1) {
            Pc pcToRespawn = deadPlayer.getPc();
            PowerUpCard powerUp = pcToRespawn.getPowerUpCard(powerUpIndex);
            AmmoEnum respawnColour = powerUp.getColour();
            Square s = controller.getGame().getSpawnPoint(respawnColour.toSquareColour());
            Square oldSquare = pcToRespawn.getCurrSquare();
            pcToRespawn.spawn(s);
            pcToRespawn.discardPowerUp(powerUp);
            oldSquare.getPcs().remove(pcToRespawn);
            return true;
        }
        return false;
    }


    @Override
    public State nextState() {
        if (controller.getDeadPlayers().size() == 0 && controller.isNextOnDuty(deadPlayer)) {
            controller.increaseCurrPlayerIndex();
            return new StartTurnState(controller);
        }
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }
}
