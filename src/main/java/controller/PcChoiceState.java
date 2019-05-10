package controller;

import model.Pc;
import model.enumerations.PcColourEnum;

public class PcChoiceState extends State {

    @Override
    public boolean assignPcToPlayer(PcColourEnum colour, Player player) {
        Pc pc;
        pc = new Pc(colour, controller.getGame());
        player.setPc(pc);
        controller.getGame().addPc(pc);
        return true;
    }

    @Override
    public void nextState() {
        controller.setCurrState(controller.firstTurnState);
        controller.setFirstTurn(true);
    }


}
