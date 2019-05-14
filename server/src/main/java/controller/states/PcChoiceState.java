package controller.states;

import controller.Controller;
import controller.Player;
import model.Pc;
import enums.PcColourEnum;

public class PcChoiceState extends State {

    PcChoiceState(Controller controller) {
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
