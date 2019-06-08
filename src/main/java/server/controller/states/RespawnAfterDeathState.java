package server.controller.states;

import server.controller.Controller;
import server.enums.AmmoEnum;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;

public class RespawnAfterDeathState extends State {

    private Pc pcToRespawn;
    private int powerUpToRespawnIndex;

    public RespawnAfterDeathState(Controller controller) {
        super(controller);
        this.powerUpToRespawnIndex = -1;
        pcToRespawn = controller.getKilledPlayers().get(0).getPc();
        pcToRespawn.drawPowerUp();
}

    @Override
    public void selectPowerUp(int index) {
        if( pcToRespawn.getPowerUps().get(index) != null)
            this.powerUpToRespawnIndex = index;
    }


    @Override
    public boolean ok() {
        if (powerUpToRespawnIndex > -1) {
            PowerUpCard powerUp = pcToRespawn.getPowerUpCard(powerUpToRespawnIndex);
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
        //TODO conflitto tra nextTurn e nextState
        if (controller.getKilledPlayers().size() == 1 && controller.isBefore(controller.getCurrPlayer(), controller.getKilledPlayers().get(0)))
            controller.getKilledPlayers().remove(0);
        controller.nextTurn();
        return
    }
}
