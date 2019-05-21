package controller.states;

import controller.Controller;
import model.Pc;
import model.powerUps.PowerUpCard;
import model.squares.Square;
import enums.AmmoEnum;

public class FirstTurnState extends State{

    FirstTurnState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean spawnPc(Pc pc, int n){
        PowerUpCard powerUp = pc.getPowerUpCard(n);
        AmmoEnum colour = powerUp.getColour();
        Square s = controller.getGame().getSpawnPoint(colour.toSquareColour());
        pc.respawn(s);
        pc.moveTo(s);
        pc.discardPowerUp(powerUp);
        s.addPc(pc);
        return true;
    }

    @Override
    public State nextState() {
        controller.setFirstTurn(false);
        return new StartTurnState(controller);
    }
}
