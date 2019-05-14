package controller.states;

import controller.Controller;
import model.Pc;
import model.PowerUpCard;
import model.Tile;
import model.enumerations.AmmoEnum;

import java.util.Optional;

public class FirstTurnState extends State{

    FirstTurnState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean spawnPc(Pc pc, int n){
        PowerUpCard powerUp = pc.getPowerUpCard(n);
        AmmoEnum colour = powerUp.getColour();
        Optional<Tile> t = controller.getGame().getSpawnTiles().stream()
                .filter(elem -> elem.getTileColour().ordinal() == colour.ordinal())
                .findFirst();
        pc.respawn(t.get());
        pc.moveTo(t.get());
        pc.discardPowerUp(powerUp);
        t.get().addPc(pc);
        return true;
    }

    @Override
    public State nextState() {
        controller.setFirstTurn(false);
        return new StartTurnState(controller);
    }
}
