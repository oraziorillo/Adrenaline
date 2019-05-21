package controller.states;

import controller.Controller;
import controller.player.Player;
import model.Pc;
import enums.PcColourEnum;

public class PcSelectionState extends State {

    PcSelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean assignPcToPlayer(PcColourEnum colour, Player player) {
        Pc pc;
        pc = new Pc(colour, controller.getGame());
        player.setPc(pc);
        controller.getGame().addPc(pc);
        return true;
    }

    @Override
    public State nextState() {
        controller.setFirstTurn(true);
        return new FirstTurnState(controller);
    }


}
